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
        assertEquals(expected, actual.replaceFirst("\\r?\\n\\z", ""),
                "Сопоставьте текст результата с условием");
    }

    @Test
    @DisplayName("Номер месяца 1: январь")
    void month_1(TestReporter publicReporter) throws Exception {
        exact("январь", run(publicReporter, new String[] {"normal"}, "1"));
    }

    @Test
    @DisplayName("Номер месяца 2: февраль")
    void month_2() throws Exception {
        exact("февраль", run(null, null, "2"));
    }

    @Test
    @DisplayName("Номер месяца 3: март")
    void month_3() throws Exception {
        exact("март", run(null, null, "3"));
    }

    @Test
    @DisplayName("Номер месяца 4: апрель")
    void month_4() throws Exception {
        exact("апрель", run(null, null, "4"));
    }

    @Test
    @DisplayName("Номер месяца 5: май")
    void month_5() throws Exception {
        exact("май", run(null, null, "5"));
    }

    @Test
    @DisplayName("Номер месяца 6: июнь")
    void month_6() throws Exception {
        exact("июнь", run(null, null, "6"));
    }

    @Test
    @DisplayName("Номер месяца 7: июль")
    void month_7() throws Exception {
        exact("июль", run(null, null, "7"));
    }

    @Test
    @DisplayName("Номер месяца 8: август")
    void month_8() throws Exception {
        exact("август", run(null, null, "8"));
    }

    @Test
    @DisplayName("Номер месяца 9: сентябрь")
    void month_9() throws Exception {
        exact("сентябрь", run(null, null, "9"));
    }

    @Test
    @DisplayName("Номер месяца 10: октябрь")
    void month_10() throws Exception {
        exact("октябрь", run(null, null, "10"));
    }

    @Test
    @DisplayName("Номер месяца 11: ноябрь")
    void month_11() throws Exception {
        exact("ноябрь", run(null, null, "11"));
    }

    @Test
    @DisplayName("Номер месяца 12: декабрь")
    void month_12() throws Exception {
        exact("декабрь", run(null, null, "12"));
    }

    @Test
    @DisplayName("Номер 0 вне диапазона месяцев")
    void zero() throws Exception {
        exact("Некорректный месяц", run(null, null, "0"));
    }

    @Test
    @DisplayName("Номер 13 вне диапазона месяцев")
    void after_range() throws Exception {
        exact("Некорректный месяц", run(null, null, "13"));
    }

    @Test
    @DisplayName("Отрицательный номер месяца отклоняется")
    void negative() throws Exception {
        exact("Некорректный месяц", run(null, null, "-1"));
    }

    @Test
    @DisplayName("Integer.MAX_VALUE вне диапазона месяцев")
    void maximum_int() throws Exception {
        exact("Некорректный месяц", run(null, null, "2147483647"));
    }

    @Test
    @DisplayName("Integer.MIN_VALUE вне диапазона месяцев")
    void minimum_int() throws Exception {
        exact("Некорректный месяц", run(null, null, "-2147483648"));
    }

    @Test
    @DisplayName("Дробный токен не является номером месяца")
    void fraction() throws Exception {
        exact("Некорректный месяц", run(null, null, "1.5"));
    }

    @Test
    @DisplayName("Текстовый токен отклоняется")
    void word() throws Exception {
        exact("Некорректный месяц", run(null, null, "abc"));
    }

    @Test
    @DisplayName("Пустой ввод: «Некорректный месяц»")
    void empty(TestReporter publicReporter) throws Exception {
        exact("Некорректный месяц", run(publicReporter, new String[] {"boundary"}, ""));
    }

    @Test
    @DisplayName("Ввод только из пробельных символов")
    void whitespace() throws Exception {
        exact("Некорректный месяц", run(null, null, " \t\n"));
    }

    @Test
    @DisplayName("После номера 12 остальные токены не учитываются")
    void first_token_only() throws Exception {
        exact("декабрь", run(null, null, "12 abc 1"));
    }

    @Test
    @DisplayName("Неверный первый токен не заменяется последующим номером")
    void invalid_first_token() throws Exception {
        exact("Некорректный месяц", run(null, null, "abc 1"));
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
