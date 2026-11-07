import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;

class PublicExamplesTest {

    @Test
    @org.junit.jupiter.api.DisplayName(PublicChecks.NORMAL_NAME)
    void normal(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeNormal();
        reporter.publishEntry(Map.of(
            "public-case", "normal", "expected", PublicChecks.NORMAL_EXPECTED, "actual", observed.actual()));
        PublicChecks.checkNormal(observed);
    }

    @Test
    @org.junit.jupiter.api.DisplayName(PublicChecks.BOUNDARY_NAME)
    void boundary(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeBoundary();
        reporter.publishEntry(Map.of("public-case", "boundary", "expected", PublicChecks.BOUNDARY_EXPECTED,
            "actual", observed.actual()));
        PublicChecks.checkBoundary(observed);
    }
}
