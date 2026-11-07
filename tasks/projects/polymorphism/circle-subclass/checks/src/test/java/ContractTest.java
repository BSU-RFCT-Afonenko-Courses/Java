import java.lang.Double;
import java.lang.String;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ContractTest {
    @DisplayName("Размеры, имя и площадь круга для допустимых радиусов")
    @ParameterizedTest(name = "{0}")
    @MethodSource("positiveRadii")
    void positiveDomain(String caseName, double radius) {
        PublicChecks.checkCircle(radius);
    }

    static Stream<Arguments> positiveRadii() {
        return Stream.of(
            Arguments.of("Минимальный положительный double: r=Double.MIN_VALUE", Double.MIN_VALUE),
            Arguments.of("Малый положительный радиус: r=1e-100", 1e-100),
            Arguments.of("Дробный радиус: r=0.25", 0.25),
            Arguments.of("Целый радиус: r=3", 3)
        );
    }
}
