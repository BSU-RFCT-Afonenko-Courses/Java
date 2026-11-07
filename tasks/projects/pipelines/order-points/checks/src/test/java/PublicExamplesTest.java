import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
class PublicExamplesTest {
 @Test
 void normal(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","normal","input","(1:10), (2:0)","expected","Печать (2:0) (1:10); вход сохранён"));
  var observed=PublicChecks.observeNormal();
  reporter.publishEntry(Map.of("public-case","normal","actual",observed.actual()));
  PublicChecks.checkNormal(observed);
 }
 @Test
 void boundary(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","boundary","input","(3:4), (0:5), (3:4)","expected","Тот же взаимный порядок; вход сохранён"));
  var observed=PublicChecks.observeBoundary();
  reporter.publishEntry(Map.of("public-case","boundary","actual",observed.actual()));
  PublicChecks.checkBoundary(observed);
 }
}
