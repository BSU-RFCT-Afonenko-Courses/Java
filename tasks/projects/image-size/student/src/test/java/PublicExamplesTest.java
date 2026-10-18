import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test void statedSquareExample() throws Exception {assertImage(4194304L, 4.0, "1024 1024 4\n");}
    @Test void statedLargeExample() throws Exception {assertImage(7500000000L, 7152.557373046875, "50000 50000 3\n");}
    private static String run(String input, String... args) throws Exception {
        return coursesupport.JavaProcess.run(Main.class, input, args).stdout();
    }
    private static void assertImage(long expectedBytes, double expectedMiB, String input) throws Exception {
        String[] tokens = run(input).trim().split("\\s+");
        assertEquals(2, tokens.length, "Нужно вывести количество байтов и размер в МиБ");
        assertEquals(expectedBytes, Long.parseLong(tokens[0]), "Точное количество байтов");
        double actualMiB = Double.parseDouble(tokens[1]);
        assertTrue(Double.isFinite(actualMiB), "Размер в МиБ должен быть конечным");
        assertEquals(expectedMiB, actualMiB, 1e-8, "Размер в МиБ");
    }
}
