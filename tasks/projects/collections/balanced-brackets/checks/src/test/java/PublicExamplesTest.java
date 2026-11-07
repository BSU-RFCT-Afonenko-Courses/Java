import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;

class PublicExamplesTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Правильная вложенность скобок")
    void normal(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeNormal();
        reporter.publishEntry(
            Map.of("public-case", "normal", "expected", "true для ([]{}).", "actual", observed.actual()));
        PublicChecks.checkNormal(observed);
    }

    @Test
    @org.junit.jupiter.api.DisplayName("Пустая строка и лишняя закрывающая скобка")
    void boundary(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeBoundary();
        reporter.publishEntry(Map.of("public-case", "boundary", "expected",
            "true для пустой строки; false для ).", "actual", observed.actual()));
        PublicChecks.checkBoundary(observed);
    }
}
