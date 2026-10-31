import java.lang.Double;
import java.lang.Exception;
import java.lang.Math;
import java.lang.String;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String... args) throws Exception {
        return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class,"",args).stdout());
    }
    private static String[] tokens(String text) {
        return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");
    }
    private static double[] parsed(String output) {
        String[] words=tokens(output);double[] values=new double[words.length];
        for(int i=0;i<words.length;i++) {
            assertTrue(words[i].matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"),"Конечные числа в десятичной или экспоненциальной записи");
            values[i]=Double.parseDouble(words[i]);assertTrue(Double.isFinite(values[i]),"Конечный корень");
        }
        return values;
    }
    private static boolean near(double actual,double expected) {
        return Math.abs(actual-expected)<=1e-8+1e-12*Math.abs(expected);
    }
    private static void roots(TestReporter publicReporter, String[] publicCaseIds, double[] expected,String... args) throws Exception {
        String output=run(publicReporter, publicCaseIds, args);
        if(expected.length==0) {
            assertEquals("Действительных корней нет.",output.strip(),"Отрицательный дискриминант");return;
        }
        double[] actual=parsed(output);assertEquals(expected.length,actual.length,"Количество различных действительных корней");
        boolean correct=near(actual[0],expected[0]);
        if(expected.length==2)correct=correct&&near(actual[1],expected[1])||near(actual[0],expected[1])&&near(actual[1],expected[0]);
        assertTrue(correct,"Корни взаимно однозначно сопоставляются математическим значениям в пределах допуска");
    }
    @Test @DisplayName("Два различных корня. Вход: 1 -3 2")
    void two_roots(TestReporter publicReporter) throws Exception { roots(publicReporter, new String[]{"normal"}, new double[]{1,2},"1","-3","2"); }
    @Test @DisplayName("Нулевой D: ровно один корень. Вход: 1 -2 1")
    void double_root(TestReporter publicReporter) throws Exception { roots(publicReporter, new String[]{"boundary"}, new double[]{1},"1","-2","1"); }
    @Test @DisplayName("Отрицательный D: сообщение об отсутствии. Вход: 1 0 1")
    void no_real() throws Exception { roots(null, null, new double[]{},"1","0","1"); }
    @Test @DisplayName("Отрицательный D на границе коэффициентов. Вход: 10000 0 10000")
    void range_no_real() throws Exception { roots(null, null, new double[]{},"10000","0","10000"); }
    @Test @DisplayName("Нулевой и большой корень при допустимом граничном b. Вход: 1 -10000 0")
    void zero_and_large() throws Exception { roots(null, null, new double[]{0,10000},"1","-10000","0"); }
    @Test @DisplayName("Отрицательный a, два иррациональных корня. Вход: -1 0 2")
    void negative_leading() throws Exception { roots(null, null, new double[]{-Math.sqrt(2),Math.sqrt(2)},"-1","0","2"); }
    @Test @DisplayName("Границы и противоположные знаки корней. Вход: 10000 10000 -10000")
    void all_extreme() throws Exception { roots(null, null, new double[]{(-1-Math.sqrt(5))/2,(-1+Math.sqrt(5))/2},"10000","10000","-10000"); }
    @Test @DisplayName("Допуск для двух разных масштабов; алгоритм свободен. Вход: 1 10000 1")
    void small_and_large() throws Exception { roots(null, null, new double[]{(-10000-Math.sqrt(99999996.0))/2,(-10000+Math.sqrt(99999996.0))/2},"1","10000","1"); }
    @Test @DisplayName("D=1 должен отличаться от нуля, два близких корня. Вход: 2500 9999 9998")
    void exact_positive_d() throws Exception { roots(null, null, new double[]{-2,-1.9996},"2500","9999","9998"); }
    @Test @DisplayName("Нулевой D при отрицательном a. Вход: -2 4 -2")
    void negative_leading_double() throws Exception { roots(null, null, new double[]{1},"-2","4","-2"); }
    @Test @DisplayName("Нулевой корень выводится один раз. Вход: 2 0 0")
    void zero_double() throws Exception { roots(null, null, new double[]{0},"2","0","0"); }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000) visible = visible.substring(0, 2000) + "…";
            for (String id : ids) reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
