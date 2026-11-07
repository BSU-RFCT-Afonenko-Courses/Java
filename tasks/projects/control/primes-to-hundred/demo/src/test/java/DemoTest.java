import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void check(String expected, String actual) {
        if (expected.equals("Подходящих чисел нет")) {
            assertEquals(expected, actual.replaceFirst("\\r?\\n\\z", ""),
                    "Сохраните сообщение об отсутствии результата");
            return;
        }
        String[] wanted = expected.strip().split("\\p{javaWhitespace}+");
        String[] found =
                actual.isBlank() ? new String[0] : actual.strip().split("\\p{javaWhitespace}+");
        assertEquals(wanted.length, found.length, "Сохраните число значений");
        for (int i = 0; i < wanted.length; i++)
            assertEquals(new BigInteger(wanted[i]), new BigInteger(found[i]),
                    "Сохраните знак, порядок и повторы");
    }

    private static int[] values() throws Exception {
        return Arrays.stream(run("").strip().split("\\p{javaWhitespace}+"))
                .mapToInt(value -> assertDoesNotThrow(() -> Integer.parseInt(value),
                        "Выведите простые числа в виде целых чисел, разделённых пробелами"))
                .toArray();
    }

    private static void ordered(int[] a) {
        for (int i = 1; i < a.length; i++)
            assertTrue(a[i - 1] < a[i], "Сохраните возрастающий порядок без повторов");
    }

    @Test
    @DisplayName("Простые числа идут по возрастанию: 7 включено, 9 исключено")
    void normal() throws Exception {
        int[] a = values();
        ordered(a);
        assertEquals(1, Arrays.stream(a).filter(x -> x == 7).count());
        assertFalse(Arrays.stream(a).anyMatch(x -> x == 9));
    }

    @Test
    @DisplayName("Границы последовательности простых: 2 и 97; 1 и 100 исключены")
    void boundary() throws Exception {
        int[] a = values();
        ordered(a);
        assertTrue(a.length > 0);
        assertEquals(2, a[0]);
        assertEquals(97, a[a.length - 1]);
        assertFalse(Arrays.stream(a).anyMatch(x -> x == 1 || x == 100));
    }
}
