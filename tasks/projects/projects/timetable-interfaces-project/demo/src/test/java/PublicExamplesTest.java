import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
class PublicExamplesTest {
 @Test
 void normal(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","normal","input","Четыре опубликованных занятия 19/20 октября; отмена l1, аудитория l3=C","expected","t1: l1/l3 → l3, g1: l1/l2 → l2, количества 2 → 1; остальные поля l3 сохранены"));
  var observed=PublicChecks.observeNormal();
  reporter.publishEntry(Map.of("public-case","normal","actual",observed.actual()));
  PublicChecks.checkNormal(observed);
 }
 @Test
 void boundary(TestReporter reporter) throws Throwable {
  reporter.publishEntry(Map.of("public-case","boundary","input","Независимые расписания: удаление t1, выходной 20.10, предел школы, практическое занятие без автомобиля","expected","Объявленная политика удаления; l3/l4 исключены в выходной; превышение предела и отсутствие автомобиля выявлены"));
  var observed=PublicChecks.observeBoundary();
  reporter.publishEntry(Map.of("public-case","boundary","actual",observed.actual()));
  PublicChecks.checkBoundary(observed);
 }
}
