import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PublicExamplesTest {
    @Test
    @DisplayName("Размеры, имя и площадь круга: r=2")
    void normal() {
        PublicChecks.checkCircle(2);
    }

    @Test
    @DisplayName("Максимальный допустимый радиус: r=1e150")
    void boundary() {
        PublicChecks.checkCircle(1e150);
    }
}
