import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void exact(String expected, String actual) {
        assertEquals(expected, actual.replaceFirst("\\r?\\n\\z", ""),
                "Сопоставьте текст результата с условием");
    }

    @Test
    @DisplayName("Номер месяца 1: январь")
    void month_1() throws Exception {
        exact("январь", run("1"));
    }

    @Test
    @DisplayName("Пустой ввод: «Некорректный месяц»")
    void empty() throws Exception {
        exact("Некорректный месяц", run(""));
    }
}
