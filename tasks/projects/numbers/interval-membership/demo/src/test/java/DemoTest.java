import java.lang.Exception;
import java.lang.String;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void intervals(int n,int m,int k) throws Exception {
        String expected=(n<k&&k<=m)+" "+(n<=k&&k<m)+" "+(n<k&&k<m)+" "+(n<=k&&k<=m);
        assertArrayEquals(expected.split(" "),tokens(run(n+"\t"+m+"\n"+k)),"Строгость каждой границы");
    }
    @Test void left() throws Exception { intervals(-2,4,-2); }
    @Test void right() throws Exception { intervals(-2,4,4); }
}
