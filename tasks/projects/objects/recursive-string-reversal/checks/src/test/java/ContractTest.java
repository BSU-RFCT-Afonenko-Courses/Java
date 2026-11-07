import java.lang.Throwable;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContractTest {
    static void printed(String input, String expected) throws Throwable {
        String actual = TaskBinding.printed(input);
        assertTrue(actual.equals(expected) || actual.equals(expected + "\n")
                   || actual.equals(expected + "\r\n"));
    }
    @Test
    @DisplayName("Обратная печать сохраняет пробелы, char и строку длиной 256")
    void spacesBmpAndLengthBoundary() throws Throwable {
        printed(" A! ", " !A ");
        printed("Я€", "€Я");
        printed("A\n", "\nA");
        printed("x".repeat(256), "x".repeat(256));
    }
}
