package coursesupport;

import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class JavaProcessTest {
    @TempDir Path directory;

    private JavaProcess.Result run(String mode, String input, Duration timeout, int limit) throws Exception {
        Path classes = Path.of(Program.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        return JavaProcess.run(classes, Program.class.getName(), input, List.of(mode, "a b", "Я🙂"),
            directory, timeout, limit);
    }

    // Потеря UTF-8, склейка stderr, требование exit=0 или изменение cwd ломают этот протокол.
    @Test void keepsStreamsArgumentsDirectoryAndNonzeroExit() throws Exception {
        var result = run("protocol", "текст\n", Duration.ofSeconds(5), 65536);
        assertEquals("a b|Я🙂|текст\nTIMEOUT\n", result.stdout());
        assertEquals("diagnostic\n", result.stderr());
        assertEquals(7, result.exitCode());
        assertEquals("saved", java.nio.file.Files.readString(directory.resolve("marker.txt")));
    }

    // Повторный main в общей JVM вернул бы 2 или завершил процесс JUnit.
    @Test void startsFreshJvmForEveryCase() throws Exception {
        assertEquals("1", run("state", "", Duration.ofSeconds(5), 65536).stdout());
        assertEquals("1", run("state", "", Duration.ofSeconds(5), 65536).stdout());
    }

    // Синхронная запись stdin перед чтением stdout зависает на заполненных pipe.
    @Test void drainsBothStreamsWhileWritingLargeInput() throws Exception {
        var result = run("pipes", "x".repeat(200000), Duration.ofSeconds(5), 500000);
        assertEquals("o".repeat(100000) + "200000", result.stdout());
        assertEquals("e".repeat(100000), result.stderr());
    }

    @Test void timesOutInsteadOfWaitingForever() {
        var failure = assertThrows(AssertionError.class,
            () -> run("hang", "", Duration.ofMillis(300), 65536));
        assertTrue(failure.getMessage().contains("времени"), failure::getMessage);
    }

    // Grandchild удерживает pipe: cleanup не вправе ждать его завершения.
    @Test void cleanupStaysBoundedWhenDescendantKeepsInputPipeOpen() throws Exception {
        long started = System.nanoTime();
        try {
            assertThrows(AssertionError.class, () -> run("descendant", "x".repeat(2000000),
                Duration.ofMillis(300), 65536));
            long elapsed = Duration.ofNanos(System.nanoTime() - started).toMillis();
            assertTrue(elapsed < 2500, "cleanup занял " + elapsed + " мс");
        } finally {
            Path pid = directory.resolve("child.pid");
            if (java.nio.file.Files.exists(pid)) {
                ProcessHandle.of(Long.parseLong(java.nio.file.Files.readString(pid)))
                    .ifPresent(ProcessHandle::destroyForcibly);
            }
        }
    }

    // Лимит общий для двух потоков, а не отдельные 65536 на каждый.
    @Test void boundsCombinedOutput() {
        var failure = assertThrows(AssertionError.class,
            () -> run("pipes", "", Duration.ofSeconds(5), 150000));
        assertTrue(failure.getMessage().contains("вывода"), failure::getMessage);
    }

    @Test void rejectsInvalidLimitsBeforeLaunch() {
        assertThrows(IllegalArgumentException.class,
            () -> run("state", "", Duration.ZERO, 1));
        assertThrows(IllegalArgumentException.class,
            () -> run("state", "", Duration.ofSeconds(1), 0));
    }

    @Test void defaultEntryPointLauncherUsesCodeLocationWithoutInitializingInHost() throws Exception {
        assertEquals("1", JavaProcess.run(Program.class, "", "state").stdout());
        assertNull(System.getProperty("support.program.initialized"));
    }

    public static class Program {
        static int calls;
        static { System.setProperty("support.program.initialized", "yes"); }

        public static void main(String[] args) throws Exception {
            switch (args[0]) {
                case "protocol" -> {
                    String input = new String(System.in.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                    System.out.print(args[1] + "|" + args[2] + "|" + input + "TIMEOUT\n");
                    System.err.print("diagnostic\n");
                    java.nio.file.Files.writeString(Path.of("marker.txt"), "saved");
                    System.exit(7);
                }
                case "state" -> System.out.print(++calls);
                case "pipes" -> {
                    System.out.print("o".repeat(100000));
                    System.out.flush();
                    System.err.print("e".repeat(100000));
                    System.err.flush();
                    System.out.print(System.in.readAllBytes().length);
                }
                case "hang" -> Thread.sleep(60000);
                case "descendant" -> {
                    String classes = Path.of(Program.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
                    Process child = new ProcessBuilder(Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                        "-cp", classes, Program.class.getName(), "hold").inheritIO().start();
                    java.nio.file.Files.writeString(Path.of("child.pid"), Long.toString(child.pid()));
                    Thread.sleep(10000);
                }
                case "hold" -> Thread.sleep(4000);
                default -> throw new IllegalArgumentException(args[0]);
            }
        }
    }
}
