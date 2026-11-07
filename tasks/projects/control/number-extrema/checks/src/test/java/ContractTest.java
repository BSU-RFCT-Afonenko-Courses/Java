import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
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

    private static void exact(String expected, String actual) {
        assertEquals(expected.strip().replaceAll("\\p{javaWhitespace}+", " "),
                actual.strip().replaceAll("\\p{javaWhitespace}+", " "),
                "Сопоставьте подписи и значения экстремумов");
    }

    @Test
    @DisplayName("Ввод 4 7 -3 0 7: максимум 7, минимум -3")
    void published(TestReporter publicReporter) throws Exception {
        exact("Максимум: 7\nМинимум: -3",
                run(publicReporter, new String[] {"normal"}, "4 7 -3 0 7"));
    }

    @Test
    @DisplayName("Экстремумы набора только из отрицательных чисел")
    void negative_only() throws Exception {
        exact("Максимум: -4\nМинимум: -31", run(null, null, "4 -19 -31 -4 -8"));
    }

    @Test
    @DisplayName("Экстремумы набора только из положительных чисел")
    void positive_only() throws Exception {
        exact("Максимум: 37\nМинимум: 4", run(null, null, "4 12 37 4 22"));
    }

    @Test
    @DisplayName("Одно значение является и минимумом, и максимумом")
    void single() throws Exception {
        exact("Максимум: -731\nМинимум: -731", run(null, null, "1 -731"));
    }

    @Test
    @DisplayName("Повторяющиеся равные значения")
    void repeated() throws Exception {
        exact("Максимум: 56\nМинимум: 56", run(null, null, "4 56 56 56 56"));
    }

    @Test
    @DisplayName("Экстремумы на границах int")
    void endpoints() throws Exception {
        exact("Максимум: 2147483647\nМинимум: -2147483648",
                run(null, null, "3 2147483647 -7 -2147483648"));
    }

    @Test
    @DisplayName("Экстремумы внутри последовательности")
    void middle_extremes() throws Exception {
        exact("Максимум: 80\nМинимум: -44", run(null, null, "5 15 -44 80 2 3"));
    }

    @Test
    @DisplayName("Экстремумы в начале последовательности")
    void first_extremes() throws Exception {
        exact("Максимум: 2147483647\nМинимум: -2147483648",
                run(null, null, "3 -2147483648 2147483647 5"));
    }

    @Test
    @DisplayName("Количество 0: сообщение об ошибке без чтения последовательности")
    void zero(TestReporter publicReporter) throws Exception {
        exact("Количество должно быть положительным",
                run(publicReporter, new String[] {"boundary"}, "0"));
    }

    @Test
    @DisplayName("Отрицательное количество отклоняется")
    void negative_count() throws Exception {
        exact("Количество должно быть положительным", run(null, null, "-13"));
    }

    @Test
    @DisplayName("Integer.MIN_VALUE как количество отклоняется")
    void minimum_count() throws Exception {
        exact("Количество должно быть положительным", run(null, null, "-2147483648"));
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
