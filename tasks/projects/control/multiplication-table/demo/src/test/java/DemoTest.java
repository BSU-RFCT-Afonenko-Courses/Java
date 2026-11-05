import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void row(int multiplier) throws Exception {
        String[] rows = run("").strip().split("\\R");
        assertEquals(10, rows.length, "Таблица должна содержать десять строк");
        String[] values = rows[multiplier - 1].strip().split("\\p{javaWhitespace}+");
        assertEquals(10, values.length,
                "В строке множителя " + multiplier + " должно быть десять значений");
        for (int column = 1; column <= 10; column++) {
            assertEquals(BigInteger.valueOf((long) multiplier * column),
                    new BigInteger(values[column - 1]),
                    "Произведение в строке " + multiplier + ", столбце " + column);
        }
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 1")
    void row_1() throws Exception {
        row(1);
    }

    @Test
    @DisplayName("Таблица 1…10: строка множителя 10")
    void row_10() throws Exception {
        row(10);
    }
}
