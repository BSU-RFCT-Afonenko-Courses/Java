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

    private static final java.util.concurrent.atomic.AtomicLong launches =
        new java.util.concurrent.atomic.AtomicLong();

    public record Result(String stdout, String stderr, int exitCode) {}

    /** Наблюдённое необработанное исключение в запущенной программе. */
    public static final class UncaughtStudentException extends AssertionError {
        private UncaughtStudentException(String stderr) {
            super("Программа завершила поток необработанным исключением:\n" + stderr);
        }
    }

    /** Истечение времени именно вложенного процесса или захвата его вывода. */
    public static final class StudentTimeout extends AssertionError {
        private StudentTimeout() { super("Программа не завершилась в пределах времени проверки"); }
    }

    /** Превышение совокупного бюджета stdout и stderr вложенного процесса. */
    public static final class StudentOutputLimit extends AssertionError {
        private StudentOutputLimit(int limit) { super("Превышено ограничение вывода " + limit + " байт"); }
    }


    public static Result run(Class<?> entryPoint, String input, String... arguments) throws Exception {
        Path classes = Path.of(entryPoint.getProtectionDomain().getCodeSource().getLocation().toURI());
        return run(classes, entryPoint.getName(), input, List.of(arguments), null,
            Duration.ofSeconds(10), 65536);
    }

    /** Проверка необработанного исключения без ограничения кода завершения. */
    public static Result runCheckingUncaught(Class<?> entryPoint, String input, String... arguments)
            throws Exception {
        // Для выбора main используется механизм запускателя установленного JDK 25.
        try {
            Class.forName("jdk.internal.misc.MethodFinder").getMethod("findMainMethod", Class.class);
        } catch (ReflectiveOperationException | LinkageError problem) {
            throw new IOException("В установленном JDK отсутствует выбор main для проверки", problem);
        }
        Path classes = Path.of(entryPoint.getProtectionDomain().getCodeSource().getLocation().toURI());
        // CREATE_NEW сохраняет чужие файлы; счётчик не требует источника энтропии
        // в песочнице, где createTempFile запускает медленный резервный механизм SecureRandom.
        Path scratch = java.nio.file.Files.createDirectory(Path.of(System.getProperty("java.io.tmpdir"),
            "course-uncaught-" + ProcessHandle.current().pid() + "-" + launches.incrementAndGet()
                + "-" + Long.toUnsignedString(System.nanoTime())));
        Path launcher = scratch.resolve("launcher.jar");
        Path status = scratch.resolve("uncaught.status");
        try {
            java.nio.file.Files.createFile(status);
            String resource = CheckedLauncher.class.getName().replace('.', '/') + ".class";
            try (var bytes = JavaProcess.class.getResourceAsStream("/" + resource);
                    var jar = new java.util.jar.JarOutputStream(java.nio.file.Files.newOutputStream(launcher))) {
                if (bytes == null) throw new IOException("Не найден класс запуска проверки исключений");
                jar.putNextEntry(new java.util.jar.JarEntry(resource));
                bytes.transferTo(jar);
                jar.closeEntry();
            }
            var launchArguments = new ArrayList<>(List.of(status.toString(), entryPoint.getName()));
            launchArguments.addAll(List.of(arguments));
            Result result = run(classes, CheckedLauncher.class.getName(), input, launchArguments, null,
                Duration.ofSeconds(10), 65536,
                List.of("--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED"), launcher);
            byte[] observed = java.nio.file.Files.readAllBytes(status);
            if (observed.length == 0 || observed[0] != 'R') {
                throw new IOException("Не удалось подготовить проверку исключений в JDK 25:\n" + result.stderr());
            }
            if (observed.length > 1) {
                throw new UncaughtStudentException(result.stderr());
            }
            return result;
        } finally {
            java.nio.file.Files.deleteIfExists(launcher);
            java.nio.file.Files.deleteIfExists(status);
            java.nio.file.Files.deleteIfExists(scratch);
        }
    }

    /** Отдельная JVM: ошибки загрузки, создания экземпляра и main видимы проверке. */
    public static final class CheckedLauncher {
        public static void main(String[] arguments) throws Exception {
            // Служебный файл статуса открыт через RandomAccessFile до запуска программы.
            // Закрытие или замена stderr оставляет его открытым.
            var status = new java.io.RandomAccessFile(arguments[0], "rw");
            java.lang.reflect.Method selector = Class.forName("jdk.internal.misc.MethodFinder")
                .getMethod("findMainMethod", Class.class);
            selector.invoke(null, CheckedLauncher.class);
            status.write('R');
            Thread.UncaughtExceptionHandler monitor = (thread, failure) -> {
                try {
                    synchronized (status) {
                        status.seek(1);
                        status.write('F');
                    }
                } catch (IOException problem) {
                    Runtime.getRuntime().halt(125);
                }
                failure.printStackTrace(System.err);
            };
            Thread.setDefaultUncaughtExceptionHandler(monitor);
            try {
                Class<?> entry = Class.forName(arguments[1], false, ClassLoader.getSystemClassLoader());
                var main = (java.lang.reflect.Method) selector.invoke(null, entry);
                if (main == null) throw new NoSuchMethodException("Не найден main в " + entry.getName());
                Object instance = null;
                Class.forName(entry.getName(), true, entry.getClassLoader());
                if (!java.lang.reflect.Modifier.isStatic(main.getModifiers())) {
                    var constructor = entry.getDeclaredConstructor();
                    if (java.lang.reflect.Modifier.isPrivate(constructor.getModifiers())) {
                        throw new IllegalAccessException("Конструктор без параметров недоступен");
                    }
                    constructor.setAccessible(true);
                    instance = constructor.newInstance();
                }
                main.setAccessible(true);
                if (main.getParameterCount() == 0) main.invoke(instance);
                else main.invoke(instance, (Object) java.util.Arrays.copyOfRange(arguments, 2, arguments.length));
            } catch (Throwable failure) {
                if (failure instanceof java.lang.reflect.InvocationTargetException wrapped) {
                    failure = wrapped.getCause();
                }
                monitor.uncaughtException(Thread.currentThread(), failure);
            }
        }
    }

    public static Result run(Path classes, String mainClass, String input, List<String> arguments,
            Path directory, Duration timeout, int outputLimit) throws IOException, InterruptedException {
        return run(classes, mainClass, input, arguments, directory, timeout, outputLimit, List.of(), null);
    }

    private static Result run(Path classes, String mainClass, String input, List<String> arguments,
            Path directory, Duration timeout, int outputLimit, List<String> vmArguments, Path launcher)
            throws IOException, InterruptedException {
        if (timeout.isNegative() || timeout.isZero() || outputLimit <= 0) {
            throw new IllegalArgumentException("Время и объём вывода должны быть положительными");
        }
        long remaining = timeout.toNanos();
        var command = new ArrayList<>(List.of(
            Path.of(System.getProperty("java.home"), "bin", "java").toString(),
            "-Xmx128m", "-XX:TieredStopAtLevel=1", "-XX:ActiveProcessorCount=1",
            "-XX:-UsePerfData", "-XX:+DisableAttachMechanism", "--illegal-native-access=deny",
            "-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8"));
        command.addAll(vmArguments);
        String classPath = classes.toAbsolutePath().toString();
        if (launcher != null) classPath = launcher.toAbsolutePath() + java.io.File.pathSeparator + classPath;
        command.addAll(List.of("-cp", classPath, mainClass));
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

    private static StudentTimeout timedOut() {
        return new StudentTimeout();
    }

    private static final class OutputBudget {
        private final int limit;
        private long used;
        OutputBudget(int limit) { this.limit = limit; }
        synchronized void add(int count) {
            used += count;
            if (used > limit) throw new StudentOutputLimit(limit);
        }
    }
}
