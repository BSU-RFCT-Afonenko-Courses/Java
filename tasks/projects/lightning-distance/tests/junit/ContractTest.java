import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test
    void doesNotHalveLightningDistance() throws Exception {
        assertEquals(2412.0, firstNumber(run("")), 1e-8);
    }

    private static String run(String input, String... args) throws Exception {
        var result = coursesupport.JavaProcess.run(Main.class, input, args);
        System.err.print(result.stderr());
        return result.stdout();
    }
    private static double firstNumber(String output) {
        return Double.parseDouble(output.lines().filter(line -> !line.isBlank()).findFirst().orElseThrow().trim());
    }
}
