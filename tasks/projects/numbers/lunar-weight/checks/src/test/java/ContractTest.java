import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void weightValue(String input,String output) {
        String[] values=tokens(output); assertEquals(1,values.length,"Одно число результата");
        assertTrue(Double.isFinite(Double.parseDouble(values[0])),"Конечный результат");
        var expected=new java.math.BigDecimal(Double.parseDouble(input)).multiply(new java.math.BigDecimal("0.17"));
        var tolerance=new java.math.BigDecimal("1e-8").max(expected.abs().multiply(new java.math.BigDecimal("1e-12")));
        var error=new java.math.BigDecimal(values[0]).subtract(expected).abs();
        assertTrue(error.compareTo(tolerance)<=0,()->"Ошибка "+error+" превышает "+tolerance);
    }
    private static void weight(String input) throws Exception {weightValue(input,run(input));}
    private static void defaults() throws Exception {
        String[] lines=run("").strip().split("\\R",-1);assertEquals(2,lines.length,"Два результата по одному в строке");
        weightValue("0",lines[0]);String[] second=tokens(lines[1]);assertEquals(1,second.length);
        assertTrue(Double.isFinite(Double.parseDouble(second[0])),"Собственный пример — конечное число");
    }
    @Test void normal() throws Exception { weight("100"); }
    @Test void zero() throws Exception { weight("0"); }
    @Test void fraction() throws Exception { weight("7.5"); }
    @Test void coefficient() throws Exception { weight("250"); }
    @Test void large() throws Exception { weight("1e20"); }
    @Test void rounding() throws Exception { weight("1000000000000001"); }
    @Test void subnormal() throws Exception { weight(Double.toString(Double.MIN_VALUE)); }
    @Test void normal_minimum() throws Exception { weight(Double.toString(Double.MIN_NORMAL)); }
    @Test void maximum() throws Exception { weight(Double.toString(Double.MAX_VALUE)); }
    @Test void empty_demo() throws Exception { defaults(); }
}
