import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.String;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void image(int width,int height,int depth) throws Exception {
        long bytes=(long)width*height*depth;
        String[] values=tokens(run(width+"\n\t"+height+" "+depth));
        assertEquals(2,values.length,"Нужны байты и МиБ");
        assertEquals(bytes,Long.parseLong(values[0]),"Точное целое количество байтов");
        double actual=Double.parseDouble(values[1]);assertTrue(Double.isFinite(actual));
        assertEquals(bytes/1048576.0,actual,1e-8,"МиБ и абсолютный допуск");
    }
    @Test void normal() throws Exception { image(1024,1024,4); }
    @Test void pixel() throws Exception { image(1,1,1); }
}
