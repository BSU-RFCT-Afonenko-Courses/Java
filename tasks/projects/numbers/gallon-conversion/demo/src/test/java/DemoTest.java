import java.lang.Double;
import java.lang.Exception;
import java.lang.String;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void gallons() throws Exception {
        String[] values=tokens(run(""));assertEquals(2,values.length,"Галлоны затем литры");
        double a=Double.parseDouble(values[0]),b=Double.parseDouble(values[1]);assertTrue(Double.isFinite(a)&&Double.isFinite(b));
        assertEquals(10,a,1e-8);assertEquals(37.854,b,1e-8);
    }
    @Test void required_pair() throws Exception { gallons(); }
}
