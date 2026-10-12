import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void includesTenthRowAndColumn() throws Exception {
        assertMultiplicationTable(run(""));
    }

    @Test
    void startsAtOne() throws Exception {
        assertMultiplicationTable(run(""));
    }

    private static String run(String input, String... args) throws Exception {
        var previousIn = System.in;
        var previousOut = System.out;
        var bytes = new java.io.ByteArrayOutputStream();
        try (var output = new java.io.PrintStream(bytes, true, java.nio.charset.StandardCharsets.UTF_8)) {
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(args);
            output.flush();
            return bytes.toString(java.nio.charset.StandardCharsets.UTF_8);
        } finally {
            System.setIn(previousIn);
            System.setOut(previousOut);
        }
    }
    private static void assertMultiplicationTable(String output) {
        String[] rows = output.trim().split("\\R");
        assertEquals(10, rows.length, "Множители от 1 до 10");
        int[][] expected = {
            {1,2,3,4,5,6,7,8,9,10}, {2,4,6,8,10,12,14,16,18,20},
            {3,6,9,12,15,18,21,24,27,30}, {4,8,12,16,20,24,28,32,36,40},
            {5,10,15,20,25,30,35,40,45,50}, {6,12,18,24,30,36,42,48,54,60},
            {7,14,21,28,35,42,49,56,63,70}, {8,16,24,32,40,48,56,64,72,80},
            {9,18,27,36,45,54,63,72,81,90}, {10,20,30,40,50,60,70,80,90,100}
        };
        for (int i = 0; i < 10; i++) {
            String[] cells = rows[i].trim().split("\\s+");
            assertEquals(10, cells.length);
            for (int j = 0; j < 10; j++) assertEquals(expected[i][j], Integer.parseInt(cells[j]));
        }
    }
}
