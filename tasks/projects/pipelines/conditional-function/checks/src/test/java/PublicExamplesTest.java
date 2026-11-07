import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
class PublicExamplesTest {
 @Test
 void normal(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","normal","input","Создание функции; apply для пустой строки и дважды для abc","expected","0, 3, 3; condition=3, true=1, false=2; тот же аргумент"));
  var observed=PublicChecks.observeNormal();
  reporter.publishEntry(Map.of("public-case","normal","actual",observed.actual()));
  PublicChecks.checkNormal(observed);
 }
 @Test
 void boundary(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","boundary","input","apply(null), условие true, невыбранная ветвь выбрасывает исключение","expected","null; condition=1, true=1, false=0; при создании вызовов нет"));
  var observed=PublicChecks.observeBoundary();
  reporter.publishEntry(Map.of("public-case","boundary","actual",observed.actual()));
  PublicChecks.checkBoundary(observed);
 }
}
