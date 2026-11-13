import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Регрессия публичной самопроверки; этот файл не входит в оценочный набор contract. */
class MoveRowZerosPublicChecksTest {
    private static final int[][] EXPECTED = {{-3, 3, 0}, {2, -1, 0}, {1, 3, -2}};

    @Test
    @DisplayName("Допустимы десятичная и экспоненциальная запись")
    void acceptsDecimalAndScientificNotation() {
        assertDoesNotThrow(
                () -> PublicChecks.rows(EXPECTED, "-3.0 3e0 0.000\n2.0 -1.0 0e0\n1e0 3.0 -2e0\n"));
    }

    @Test
    @DisplayName("Публичная самопроверка сохраняет допуск оценочного JUnit")
    void acceptsSameAbsoluteToleranceAsContract() {
        assertDoesNotThrow(() -> PublicChecks.rows(EXPECTED, "-3 3 0.000000005\n2 -1 0\n1 3 -2\n"));
    }

    @Test
    @DisplayName("Неверное значение отклоняется")
    void rejectsWrongElement() {
        assertThrows(
                AssertionError.class,
                () -> PublicChecks.rows(EXPECTED, "-3 3 0.1\n2 -1 0\n1 3 -2\n"));
    }

    @Test
    @DisplayName("NaN и бесконечные значения отклоняются")
    void rejectsNaNAndInfinity() {
        for (String invalid : new String[] {"NaN", "Infinity", "1e309"}) {
            assertThrows(
                    AssertionError.class,
                    () -> PublicChecks.rows(EXPECTED, "-3 3 " + invalid + "\n2 -1 0\n1 3 -2\n"));
        }
    }

    @Test
    @DisplayName("Неверная форма и подписи отклоняются")
    void rejectsWrongShapeAndLabels() {
        assertThrows(
                AssertionError.class, () -> PublicChecks.rows(EXPECTED, "-3 3 0 2 -1 0 1 3 -2"));
        assertThrows(
                AssertionError.class,
                () -> PublicChecks.rows(EXPECTED, "row: -3 3 0\n2 -1 0\n1 3 -2\n"));
    }
}
