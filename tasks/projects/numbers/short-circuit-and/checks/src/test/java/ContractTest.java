import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void quotient(String input,boolean expected) throws Exception {
        assertArrayEquals(new String[]{""+expected},tokens(run(input)),"Положительность целочисленного частного без исключения");
    }
    @Test void zero_divisor() throws Exception { quotient("7 0",false); }
    @Test void positive() throws Exception { quotient("7 2",true); }
    @Test void truncated() throws Exception { quotient("1 2",false); }
    @Test void negative() throws Exception { quotient("-7 2",false); }
    @Test void zero() throws Exception { quotient("0 3",false); }
    @Test void negative_divisor() throws Exception { quotient("8 -2",false); }
    @Test void overflow() throws Exception { quotient("-2147483648 -1",false); }
    @Test void both_negative() throws Exception { quotient("-8 -2",true); }
    @Test void empty_demo() throws Exception { quotient("",false); }
}
