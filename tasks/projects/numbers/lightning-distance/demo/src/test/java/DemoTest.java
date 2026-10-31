import java.lang.Double;
import java.lang.Exception;
import java.lang.String;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void distance() throws Exception {
        String first=run("").lines().filter(s->!s.isBlank()).findFirst().orElseThrow();
        String[] values=tokens(first);assertEquals(1,values.length,"В первой непустой строке только расстояние");
        double actual=Double.parseDouble(values[0]);assertTrue(Double.isFinite(actual));assertEquals(2412.0,actual,1e-8);
    }
    @Test void first_required_result() throws Exception { distance(); }
}
