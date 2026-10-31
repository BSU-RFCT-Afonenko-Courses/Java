import java.lang.Throwable;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.TestReporter;import java.util.Map;
class PublicExamplesTest {
 @Test void normal(TestReporter reporter)throws Throwable {var observed=PublicChecks.observeNormal();reporter.publishEntry(Map.of("public-case","normal","actual",observed.actual()));PublicChecks.checkNormal(observed);}
 @Test void boundary(TestReporter reporter)throws Throwable {var observed=PublicChecks.observeBoundary();reporter.publishEntry(Map.of("public-case","boundary","actual",observed.actual()));PublicChecks.checkBoundary(observed);}
}
