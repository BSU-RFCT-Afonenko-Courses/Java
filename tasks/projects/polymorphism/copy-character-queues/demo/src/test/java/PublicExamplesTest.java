import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class PublicExamplesTest {
    @Test
    @DisplayName(PublicChecks.NORMAL_NAME)
    void normal(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeNormal();
        reporter.publishEntry(Map.of("public-case", "normal", "expected",
                                     PublicChecks.NORMAL_EXPECTED, "actual", observed.actual()));
        assertTrue(observed.passed(),
                   "Вход/действие: " + PublicChecks.NORMAL_INPUT + "\nОжидается: "
                   + PublicChecks.NORMAL_EXPECTED + "\nПолучено: " + observed.actual());
    }

    @Test
    @DisplayName(PublicChecks.BOUNDARY_NAME)
    void boundary(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeBoundary();
        reporter.publishEntry(Map.of("public-case", "boundary", "expected",
                                     PublicChecks.BOUNDARY_EXPECTED, "actual", observed.actual()));
        assertTrue(observed.passed(),
                   "Вход/действие: " + PublicChecks.BOUNDARY_INPUT + "\nОжидается: "
                   + PublicChecks.BOUNDARY_EXPECTED + "\nПолучено: " + observed.actual());
    }
}
