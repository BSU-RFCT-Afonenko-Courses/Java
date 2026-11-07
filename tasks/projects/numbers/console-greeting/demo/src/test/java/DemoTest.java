import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DemoTest {
    private static String run(String input, String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, input, args).stdout();
    }

    private static String[] tokens(String text) {
        return text.strip().split("\\p{javaWhitespace}+");
    }

    private static void greeting(String expected, String... args) throws Exception {
        String value = run("stdin не имя", args).replace("\r\n", "\n").replace("\r", "\n");
        assertEquals(expected,
                value.endsWith("\n") ? value.substring(0, value.length() - 1) : value,
                "Сохраните сообщение, имя и одну строку без лишнего вывода");
    }

    @Test
    @DisplayName("Первый аргумент Анна: приветствие с неизменённым именем")
    void normal() throws Exception {
        greeting("Здравствуйте, Анна!", "Анна");
    }

    @Test
    @DisplayName("Без аргументов: сообщение «Укажите имя.»")
    void absent() throws Exception {
        greeting("Укажите имя.");
    }
}
