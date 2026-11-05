import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;

class PublicExamplesTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Сумма многочленов без изменения входов")
    void normal(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeNormal();
        reporter.publishEntry(Map.of("public-case", "normal", "expected",
            "Коэффициенты {0=1.0, 1=2.0, 2=0.0}; входы сохранены, результат независим; нулевой коэффициент можно не хранить.",
            "actual", observed.actual()));
        PublicChecks.checkNormal(observed);
    }

    @Test
    @org.junit.jupiter.api.DisplayName("Пустые карты многочленов")
    void boundary(TestReporter reporter) throws Throwable {
        var observed = PublicChecks.observeBoundary();
        reporter.publishEntry(Map.of("public-case", "boundary", "expected",
            "Пустой результат; входы сохранены, результат независим.", "actual", observed.actual()));
        PublicChecks.checkBoundary(observed);
    }
}
