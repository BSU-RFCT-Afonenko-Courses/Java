package coursesupport;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/** Запускает программу в отдельной JVM и получает её вывод и код завершения. */
public final class JavaProcess {
    private JavaProcess() {}

    public record Result(String stdout, String stderr, int exitCode) {}

    public static Result run(Class<?> entryPoint, String input, String... arguments) throws Exception {
        Path classes = Path.of(entryPoint.getProtectionDomain().getCodeSource().getLocation().toURI());
        return run(classes, entryPoint.getName(), input, List.of(arguments), null,
            Duration.ofSeconds(10), 65536);
    }

    public static Result run(Path classes, String mainClass, String input, List<String> arguments,
            Path directory, Duration timeout, int outputLimit) throws IOException, InterruptedException {
        if (timeout.isNegative() || timeout.isZero() || outputLimit <= 0) {
            throw new IllegalArgumentException("Время и объём вывода должны быть положительными");
        }
        long remaining = timeout.toNanos();
        var command = new ArrayList<>(List.of(
            Path.of(System.getProperty("java.home"), "bin", "java").toString(),
            "-Xmx128m", "-XX:TieredStopAtLevel=1", "-XX:ActiveProcessorCount=1",
            "-XX:-UsePerfData", "-XX:+DisableAttachMechanism", "--illegal-native-access=deny",
            "-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8",
            "-cp", classes.toAbsolutePath().toString(), mainClass));
        command.addAll(arguments);
        Process process = new ProcessBuilder(command)
            .directory(directory == null ? null : directory.toFile()).start();
        long started = System.nanoTime();
        var failure = new AtomicReference<Throwable>();
        var budget = new OutputBudget(outputLimit);
        ExecutorService workers = Executors.newVirtualThreadPerTaskExecutor();
        try {
            Future<String> stdout = workers.submit(() -> capture(process.getInputStream(), budget, failure));
            Future<String> stderr = workers.submit(() -> capture(process.getErrorStream(), budget, failure));
            workers.submit(() -> {
                try (var stdin = process.getOutputStream()) {
                    stdin.write(input.getBytes(StandardCharsets.UTF_8));
                } catch (IOException ignored) {
                    // Завершение программы может закрыть ввод до передачи всех данных.
                }
            });
            while (true) {
                throwFailure(failure.get());
                remaining = timeout.toNanos() - (System.nanoTime() - started);
                if (remaining <= 0) throw timedOut();
                if (process.waitFor(Math.min(remaining, TimeUnit.MILLISECONDS.toNanos(10)),
                        TimeUnit.NANOSECONDS)) break;
            }
            String out = captured(stdout, timeout.toNanos() - (System.nanoTime() - started));
            String err = captured(stderr, timeout.toNanos() - (System.nanoTime() - started));
            throwFailure(failure.get());
            return new Result(out, err, process.exitValue());
        } finally {
            // Process.destroyForcibly может ждать освобождения блокировки при закрытии каналов.
            // Здесь для завершения созданного процесса используется ProcessHandle.
            if (process.isAlive()) process.toHandle().destroyForcibly();
            process.waitFor(1, TimeUnit.SECONDS);
            // Поток ввода-вывода закрывается в задаче, которая с ним работает.
            // Потомок процесса может удерживать унаследованный канал открытым.
            workers.shutdownNow();
            workers.awaitTermination(1, TimeUnit.SECONDS);
        }
    }

    private static String capture(InputStream stream, OutputBudget budget,
            AtomicReference<Throwable> failure) throws IOException {
        try (stream) {
            var bytes = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            for (int read; (read = stream.read(buffer)) != -1;) {
                budget.add(read);
                bytes.write(buffer, 0, read);
            }
            return bytes.toString(StandardCharsets.UTF_8);
        } catch (IOException | AssertionError problem) {
            failure.compareAndSet(null, problem);
            throw problem;
        }
    }

    private static String captured(Future<String> result, long remaining)
            throws IOException, InterruptedException {
        try {
            return result.get(Math.max(1, remaining), TimeUnit.NANOSECONDS);
        } catch (TimeoutException problem) {
            throw timedOut();
        } catch (ExecutionException problem) {
            throwFailure(problem.getCause());
            throw new IOException("Ошибка захвата вывода", problem.getCause());
        }
    }

    private static void throwFailure(Throwable problem) throws IOException {
        if (problem instanceof AssertionError assertion) throw assertion;
        if (problem instanceof IOException io) throw io;
        if (problem != null) throw new IOException("Ошибка процесса", problem);
    }

    private static AssertionError timedOut() {
        return new AssertionError("Программа не завершилась в пределах времени проверки");
    }

    private static final class OutputBudget {
        private final int limit;
        private long used;
        OutputBudget(int limit) { this.limit = limit; }
        synchronized void add(int count) {
            used += count;
            if (used > limit) throw new AssertionError("Превышено ограничение вывода " + limit + " байт");
        }
    }
}
