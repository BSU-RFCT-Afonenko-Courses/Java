import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test void singlePixel() throws Exception {assertImage(1L, 0.00000095367431640625, "1 1 1\n");}
    @Test void nonsquareImage() throws Exception {assertImage(273L, 0.00026035308837890625, "7\n13\t3\n");}
    @Test void largestImage() throws Exception {assertImage(80000000000L, 76293.9453125, "100000 100000 8\n");}
    @Test void wideOneRowImage() throws Exception {assertImage(800000L, 0.762939453125, "100000 1 8\n");}
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
