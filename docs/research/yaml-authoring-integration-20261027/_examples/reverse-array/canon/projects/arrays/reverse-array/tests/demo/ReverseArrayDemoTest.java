import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обычный JUnit: только прежние normal и boundary, без PL reporter и main runner. */
class ReverseArrayDemoTest {
    @Test
    @DisplayName("Обратный порядок с повторением")
    void normal() throws Exception {
        PublicChecks.checkNormal(PublicChecks.observeNormal());
    }

    @Test
    @DisplayName("Пустой массив")
    void boundary() throws Exception {
        PublicChecks.checkBoundary(PublicChecks.observeBoundary());
    }
}
