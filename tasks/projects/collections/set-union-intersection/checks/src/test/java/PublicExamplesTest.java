import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;

class PublicExamplesTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Объединение и пересечение без изменения входов")
    void normal(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeNormal();
        reporter.publishEntry(Map.of("public-case", "normal", "expected",
            "union={1,2,3}; intersection={2}; входы сохранены; порядок свободен.", "actual",
            observed.actual()));
        PublicChecks.checkNormal(observed);
    }

    @Test
    @org.junit.jupiter.api.DisplayName("Пустое и непустое множества")
    void boundary(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeBoundary();
        reporter.publishEntry(Map.of("public-case", "boundary", "expected",
            "union={4}; intersection={}; входы сохранены; изменение второго входа не меняет результат; порядок свободен.",
            "actual", observed.actual()));
        PublicChecks.checkBoundary(observed);
    }
}
