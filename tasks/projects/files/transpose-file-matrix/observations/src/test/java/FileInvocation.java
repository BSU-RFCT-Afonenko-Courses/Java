import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.io.File;
import java.nio.file.Path;
import coursesupport.JavaProcess;

final class FileInvocation {
    private static JavaProcess.Result last;

    static void run(String... args) throws Exception {
        last = null;
        Path classes = Path.of(
            Path.of(ProcessPort.class.getProtectionDomain().getCodeSource().getLocation().toURI())
            + File.pathSeparator
            + Path.of(Study.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
        last = JavaProcess.runCheckingUncaught(classes, "ProcessPort", "", args);
    }

    interface Action { void run() throws Exception; }

    static void refused(Action action) throws Exception {
        last = null;
        try {
            action.run();
        } catch (JavaProcess.UncaughtStudentException refused) {
            return;
        }
        // Ошибки запуска JVM/чтения служебного статуса остаются IOException:
        // их нельзя принять как успешный отказ студенческой программы.
        if (last == null || last.stdout().isBlank() && last.stderr().isBlank()) {
            throw new AssertionError("Отказ без причины");
        }
    }
}
