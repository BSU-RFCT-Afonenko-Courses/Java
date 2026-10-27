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

    // Этот случай проверяет UTF-8, раздельные stdout и stderr, аргументы,
    // рабочий каталог и сохранение ненулевого кода завершения.
    @Test void keepsStreamsArgumentsDirectoryAndNonzeroExit() throws Exception {
        var result = run("protocol", "текст\n", Duration.ofSeconds(5), 65536);
        assertEquals("a b|Я🙂|текст\nTIMEOUT\n", result.stdout());
        assertEquals("diagnostic\n", result.stderr());
        assertEquals(7, result.exitCode());
        assertEquals("saved", java.nio.file.Files.readString(directory.resolve("marker.txt")));
    }

    // В режиме state каждый запуск в новой JVM печатает 1 в stdout.
    // Два вызова Program.main в одной JVM напечатали бы последовательно 1 и 2.
    @Test void startsFreshJvmForEveryCase() throws Exception {
        assertEquals("1", run("state", "", Duration.ofSeconds(5), 65536).stdout());
        assertEquals("1", run("state", "", Duration.ofSeconds(5), 65536).stdout());
    }

    // Синхронная запись в stdin до чтения stdout блокируется, когда системные каналы заполнены.
    @Test void drainsBothStreamsWhileWritingLargeInput() throws Exception {
        var result = run("pipes", "x".repeat(200000), Duration.ofSeconds(5), 500000);
        assertEquals("o".repeat(100000) + "200000", result.stdout());
        assertEquals("e".repeat(100000), result.stderr());
    }

    @Test void timesOutInsteadOfWaitingForever() {
        var failure = assertThrows(AssertionError.class,
            () -> run("hang", "", Duration.ofMillis(300), 65536));
        assertInstanceOf(JavaProcess.StudentTimeout.class, failure);
        assertTrue(failure.getMessage().contains("времени"), failure::getMessage);
    }

    // Потомок запущенной программы удерживает системный канал открытым.
    // Освобождение ресурсов ограничено по времени и не ожидает завершения потомка.
    @Test void cleanupStaysBoundedWhenDescendantKeepsInputPipeOpen() throws Exception {
        long started = System.nanoTime();
        try {
            assertThrows(AssertionError.class, () -> run("descendant", "x".repeat(2000000),
                Duration.ofMillis(300), 65536));
            long elapsed = Duration.ofNanos(System.nanoTime() - started).toMillis();
            assertTrue(elapsed < 2500, "Запуск и освобождение ресурсов заняли " + elapsed + " мс");
        } finally {
            Path pid = directory.resolve("child.pid");
            if (java.nio.file.Files.exists(pid)) {
                ProcessHandle.of(Long.parseLong(java.nio.file.Files.readString(pid)))
                    .ifPresent(ProcessHandle::destroyForcibly);
            }
        }
    }

    // Лимит вывода применяется к суммарному числу байтов stdout и stderr.
    @Test void boundsCombinedOutput() {
        var failure = assertThrows(AssertionError.class,
            () -> run("pipes", "", Duration.ofSeconds(5), 150000));
        assertInstanceOf(JavaProcess.StudentOutputLimit.class, failure);
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

    @Test void checkedLaunchAllowsExplicitNonzeroExitWithoutException() throws Exception {
        var result = JavaProcess.runCheckingUncaught(Program.class, "текст\n", "checked-protocol", "a b", "Я🙂");
        assertEquals(7, result.exitCode());
        assertEquals("a b|Я🙂|текст\nTIMEOUT\n", result.stdout());
        assertEquals("diagnostic\n", result.stderr());
    }

    @Test void checkedLaunchRejectsCorrectPrintFollowedByUncaughtFailure() {
        assertInstanceOf(JavaProcess.UncaughtStudentException.class, assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(Program.class, "", "print-then-throw")));
    }

    @Test void checkedLaunchPreservesJava25InstanceMainSelection() throws Exception {
        assertEquals("instance:value\n", JavaProcess.runCheckingUncaught(InstanceProgram.class, "", "value").stdout());
    }

    @Test void checkedLaunchAcceptsCaughtExceptionsAndArbitraryStderr() throws Exception {
        var result = JavaProcess.runCheckingUncaught(Program.class, "", "caught");
        assertEquals("ok\n", result.stdout());
        assertEquals("Exception in thread diagnostic\n", result.stderr());
    }

    @Test void checkedLaunchRejectsUncaughtFailureEvenWithZeroProcessStatus() {
        assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(Program.class, "", "thread-throw"));
    }

    @Test void checkedLaunchWorksWhenSubmissionHasNoTestSupportOnItsClasspath() throws Exception {
        Path source = directory.resolve("SeparateMain.java");
        java.nio.file.Files.writeString(source,
            "public class SeparateMain { void main() { System.out.print(\"separate\"); System.exit(7); } }");
        Path classes = java.nio.file.Files.createDirectory(directory.resolve("classes"));
        assertEquals(0, javax.tools.ToolProvider.getSystemJavaCompiler().run(null, null, null,
            "--release", "25", "-d", classes.toString(), source.toString()));
        try (var loader = new java.net.URLClassLoader(new java.net.URL[]{classes.toUri().toURL()},
                ClassLoader.getPlatformClassLoader())) {
            var result = JavaProcess.runCheckingUncaught(loader.loadClass("SeparateMain"), "");
            assertEquals("separate", result.stdout());
            assertEquals("", result.stderr());
            assertEquals(7, result.exitCode());
        }
    }

    @Test void checkedLaunchRejectsFailureFromInterruptedMain() {
        assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(Program.class, "", "interrupted-throw"));
    }

    @Test void checkedLaunchRejectsFailureFromInterruptedWorker() {
        assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(Program.class, "", "interrupted-worker-throw"));
    }

    @Test void checkedLaunchAllowsInterruptWithoutUncaughtException() throws Exception {
        assertEquals("ok\n", JavaProcess.runCheckingUncaught(Program.class, "", "interrupt-only").stdout());
    }

    @Test void checkedLaunchRejectsFailureAfterClosingStderr() {
        assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(Program.class, "", "closed-stderr-throw"));
    }

    @Test void checkedLaunchRejectsFailureDuringClassInitialization() {
        assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(InitializationFailure.class, ""));
    }

    @Test void checkedLaunchRejectsFailureDuringInstanceConstruction() {
        assertThrows(AssertionError.class,
            () -> JavaProcess.runCheckingUncaught(ConstructorFailure.class, ""));
    }

    @Test void checkedLaunchPreservesAllJava25MainFormsAndInheritedMethods() throws Exception {
        for (Class<?> type : List.of(StaticNoArg.class, InstanceNoArg.class,
                InheritedMain.class, InterfaceMain.class, PackageMain.class)) {
            assertEquals("ok\n", JavaProcess.runCheckingUncaught(type, "").stdout(), type.getName());
        }
        assertEquals("args:value\n", JavaProcess.runCheckingUncaught(OverloadedMain.class, "", "value").stdout());
    }

    public static class ConstructorFailure {
        ConstructorFailure() {
            System.out.println("false");
            System.err.close();
            throw new ArithmeticException("constructor");
        }
        void main() {}
    }
    public static class StaticNoArg { protected static void main() { System.out.println("ok"); } }
    public static class InstanceNoArg { void main() { System.out.println("ok"); } }
    public static class ParentMain { protected void main(String[] args) { System.out.println("ok"); } }
    public static class InheritedMain extends ParentMain {}
    public interface MainInterface { default void main() { System.out.println("ok"); } }
    public static class InterfaceMain implements MainInterface {}
    static class PackageMain { static void main(String... args) { System.out.println("ok"); } }
    public static class OverloadedMain {
        static void main() { System.out.println("wrong"); }
        void main(String[] args) { System.out.println("args:" + args[0]); }
    }

    public static class InitializationFailure {
        static {
            System.out.println("false");
            if (System.nanoTime() != 0) throw new ArithmeticException("initialization");
        }
        public static void main(String[] args) {}
    }

    public static class InstanceProgram {
        void main(String[] args) { System.out.println("instance:" + args[0]); }
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
                case "checked-protocol" -> {
                    String input = new String(System.in.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                    System.out.print(args[1] + "|" + args[2] + "|" + input + "TIMEOUT\n");
                    System.err.print("diagnostic\n");
                    System.exit(7);
                }
                case "print-then-throw" -> {
                    System.out.println("false");
                    throw new ArithmeticException("zero");
                }
                case "closed-stderr-throw" -> {
                    System.out.println("false");
                    System.err.close();
                    throw new ArithmeticException("closed stderr");
                }
                case "thread-throw" -> {
                    Thread worker = new Thread(() -> { throw new ArithmeticException("worker"); });
                    worker.start();
                    worker.join();
                }
                case "interrupted-throw" -> {
                    System.out.println("false");
                    Thread.currentThread().interrupt();
                    throw new ArithmeticException("interrupted main");
                }
                case "interrupted-worker-throw" -> {
                    Thread worker = new Thread(() -> {
                        Thread.currentThread().interrupt();
                        throw new ArithmeticException("interrupted worker");
                    });
                    worker.start();
                    worker.join();
                }
                case "interrupt-only" -> {
                    Thread.currentThread().interrupt();
                    System.out.println("ok");
                }
                case "caught" -> {
                    try { throw new ArithmeticException("handled"); }
                    catch (ArithmeticException expected) { System.out.println("ok"); }
                    System.err.println("Exception in thread diagnostic");
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
