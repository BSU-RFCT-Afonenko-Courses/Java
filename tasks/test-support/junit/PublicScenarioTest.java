import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;

/** Общий JUnit-адаптер явно публичных JDK-сценариев для платформы. */
public class PublicScenarioTest {
    @Test void normal(TestReporter reporter) throws Exception {
        var result = PublicChecks.observeNormal();
        reporter.publishEntry(Map.of("public-case", "normal", "actual", actual(result)));
        PublicChecks.checkNormal(result);
    }
    @Test void boundary(TestReporter reporter) throws Exception {
        var result = PublicChecks.observeBoundary();
        reporter.publishEntry(Map.of("public-case", "boundary", "actual", actual(result)));
        PublicChecks.checkBoundary(result);
    }
    private static String actual(coursesupport.JavaProcess.Result result) {
        String text = result.stdout();
        if (text.isEmpty()) return "Пустой стандартный вывод.";
        if (!text.isBlank()) return text;
        return "Пробельный стандартный вывод: \"" + text.replace("\\", "\\\\")
            .replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t")
            .replace("\f", "\\f").replace("\u000b", "\\u000b") + "\".";
    }
}
