import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.math.BigInteger;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input)
            throws Exception {
        return publicOutput(publicReporter, publicCaseIds,
                JavaProcess.runCheckingUncaught(Main.class, input).stdout());
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

    @Test
    @DisplayName("Первое число со строго возрастающими цифрами, с сохранением знака")
    void case_1(TestReporter publicReporter) throws Exception {
        check("-128", run(publicReporter, new String[] {"normal"}, "4 122 -128 137 0"));
    }

    @Test
    @DisplayName("Единственный ноль подходит как одноцифровое число")
    void case_2(TestReporter publicReporter) throws Exception {
        check("0", run(publicReporter, new String[] {"boundary"}, "1 0"));
    }

    @Test
    @DisplayName("Выбирается первое подходящее число, независимо от величины")
    void case_3() throws Exception {
        check("137", run(null, null, "2 137 128"));
    }

    @Test
    @DisplayName("Границы int и отрицательное одноцифровое число")
    void case_4() throws Exception {
        check("-9", run(null, null, "4 -2147483648 2147483647 122 -9"));
    }

    @Test
    @DisplayName("Непустой набор без подходящих чисел")
    void case_5() throws Exception {
        check("Подходящих чисел нет", run(null, null, "3 122 321 -111"));
    }

    @Test
    @DisplayName("Пустой набор: сообщение «Подходящих чисел нет»")
    void case_6() throws Exception {
        check("Подходящих чисел нет", run(null, null, "0"));
    }

    @Test
    @DisplayName("Ведущие нули входной записи не являются цифрами значения")
    void case_7() throws Exception {
        check("128", run(null, null, "3 000128 0 9"));
    }

    @Test
    @DisplayName("Подходящий ноль выбирается раньше следующего совпадения")
    void case_8() throws Exception {
        check("0", run(null, null, "2 0 128"));
    }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000)
                visible = visible.substring(0, 2000) + "…";
            for (String id : ids)
                reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
