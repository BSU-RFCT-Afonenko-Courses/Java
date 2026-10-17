import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    void togglesAsciiAndCounts() throws Exception {
        assertEquals("aB1!\n2\n", run("Ab1!.ignore"));
    }

    private static String run(String input, String... args) throws Exception {
        return coursesupport.JavaProcess.run(Main.class, input, args).stdout();
    }
}
