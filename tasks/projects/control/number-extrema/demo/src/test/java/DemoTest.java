import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input).stdout();
    }

    private static void exact(String expected, String actual) {
        assertEquals(expected.strip().replaceAll("\\p{javaWhitespace}+", " "),
                actual.strip().replaceAll("\\p{javaWhitespace}+", " "),
                "Сопоставьте подписи и значения экстремумов");
    }

    @Test
    @DisplayName("Ввод 4 7 -3 0 7: максимум 7, минимум -3")
    void published() throws Exception {
        exact("Максимум: 7\nМинимум: -3", run("4 7 -3 0 7"));
    }

    @Test
    @DisplayName("Количество 0: сообщение об ошибке без чтения последовательности")
    void zero() throws Exception {
        exact("Количество должно быть положительным", run("0"));
    }
}
