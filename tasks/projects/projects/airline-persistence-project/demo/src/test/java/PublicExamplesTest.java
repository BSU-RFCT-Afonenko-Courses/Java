import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
class PublicExamplesTest {
 @Test
 void normal(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","normal","input","Три опубликованных самолёта; сортировка, поиск [1500, 2400], сохранение/загрузка; места c: 80 → 100","expected","Мест 232 → 252, грузоподъёмность 38; порядок по объявленному направлению, поиск с включёнными границами; состояние сохраняется между процессами"));
  var observed=PublicChecks.observeNormal();
  reporter.publishEntry(Map.of("public-case","normal","actual",observed.actual()));
  PublicChecks.checkNormal(observed);
 }
 @Test
 void boundary(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","boundary","input","Поиск [1500, 1500], [2400, 2400], [4000, 4100]","expected","На каждой включённой границе найден настоящий самолёт парка; последний диапазон пуст"));
  var observed=PublicChecks.observeBoundary();
  reporter.publishEntry(Map.of("public-case","boundary","actual",observed.actual()));
  PublicChecks.checkBoundary(observed);
 }
}
