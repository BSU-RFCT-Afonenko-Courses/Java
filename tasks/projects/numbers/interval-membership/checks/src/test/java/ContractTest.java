import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void intervals(int n,int m,int k) throws Exception {
        String expected=(n<k&&k<=m)+" "+(n<=k&&k<m)+" "+(n<k&&k<m)+" "+(n<=k&&k<=m);
        assertArrayEquals(expected.split(" "),tokens(run(n+"\t"+m+"\n"+k)),"Строгость каждой границы");
    }
    @Test void left() throws Exception { intervals(-2,4,-2); }
    @Test void right() throws Exception { intervals(-2,4,4); }
    @Test void inside() throws Exception { intervals(-17,23,6); }
    @Test void before() throws Exception { intervals(-17,23,-18); }
    @Test void after() throws Exception { intervals(-17,23,24); }
    @Test void negative_left() throws Exception { intervals(-9,-8,-9); }
    @Test void negative_right() throws Exception { intervals(-9,-8,-8); }
    @Test void wide() throws Exception { intervals(-2147483648,2147483647,1); }
    @Test void overflow() throws Exception { intervals(-2147483648,4,2147483647); }
    @Test void min_edge() throws Exception { intervals(-2147483648,-2147483647,-2147483648); }
    @Test void max_edge() throws Exception { intervals(2147483646,2147483647,2147483647); }
}
