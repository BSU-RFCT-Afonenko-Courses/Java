import java.lang.Boolean;
import java.lang.Exception;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void table() throws Exception {
        String text=run("").replace("\r\n","\n");
        if(text.endsWith("\n"))text=text.substring(0,text.length()-1);
        String[] lines=text.split("\n",-1);assertEquals(5,lines.length,"Заголовок и четыре строки");
        assertEquals("P\tQ\tAND\tOR\tXOR\tNOT",lines[0]);
        Set<String> found=new HashSet<>();
        for(int i=1;i<5;i++) {
            String[] values=lines[i].split("\t",-1);assertEquals(6,values.length,"Разделитель — табуляция");
            for(String v:values)assertTrue(v.equals("true")||v.equals("false"));
            boolean p=Boolean.parseBoolean(values[0]),q=Boolean.parseBoolean(values[1]);
            assertTrue(found.add(p+"/"+q),"Четыре различные пары");
            assertArrayEquals(new String[]{""+p,""+q,""+(p&&q),""+(p||q),""+(p^q),""+!p},values);
        }
    }
    @Test void all_operations_and_pairs() throws Exception { table(); }
}
