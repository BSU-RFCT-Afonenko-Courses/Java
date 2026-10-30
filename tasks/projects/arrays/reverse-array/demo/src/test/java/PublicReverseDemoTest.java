import coursesupport.JavaProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PublicReverseDemoTest {
    @Test @DisplayName("Необязательный открытый пример обратного вывода")
    void example() throws Exception {
        String output = JavaProcess.runCheckingUncaught(Main.class, "4 7 -2 7 0\n").stdout().strip();
        assertArrayEquals(new String[] {"0", "7", "-2", "7"}, output.split("\\s+"),
                "Ожидается 0 7 -2 7 с любыми пробельными разделителями");
    }
}
