import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
class PublicExamplesTest {
 @Test
 void normal(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","normal","input","one two two three three three","expected","Частоты one:1, two:2, three:3; префиксные двоичные коды, минимальная суммарная длина, биты в порядке входа"));
  var observed=PublicChecks.observeNormal();
  reporter.publishEntry(Map.of("public-case","normal","actual",observed.actual()));
  PublicChecks.checkNormal(observed);
 }
 @Test
 void boundary(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","boundary","input","Пустой текст; one one one; дважды red blue green","expected","Пустые таблица и биты; для единственного слова код 0; воспроизводимые коды равных частот"));
  var observed=PublicChecks.observeBoundary();
  reporter.publishEntry(Map.of("public-case","boundary","actual",observed.actual()));
  PublicChecks.checkBoundary(observed);
 }
}
