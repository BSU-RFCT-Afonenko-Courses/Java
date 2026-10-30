import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void quotient(String input,boolean expected) throws Exception {
        assertArrayEquals(new String[]{""+expected},tokens(run(input)),"Положительность целочисленного частного без исключения");
    }
    @Test void zero_divisor() throws Exception { quotient("7 0",false); }
    @Test void positive() throws Exception { quotient("7 2",true); }
}
