import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class,"",args).stdout();
    }
    private static String[] tokens(String text) {
        return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");
    }
    private static java.util.Map<String,String[]> table() throws Exception {
        String[] lines=run().lines().filter(s->!s.isBlank()).toArray(String[]::new);
        assertEquals(5,lines.length,"Заголовок и четыре строки");
        assertArrayEquals(new String[]{"P","Q","AND","OR","XOR","NOT"},tokens(lines[0]),"Шесть колонок, пробельные разделители");
        var rows=new java.util.HashMap<String,String[]>();
        for(int i=1;i<5;i++) {
            String[] values=tokens(lines[i]);assertEquals(6,values.length,"Шесть значений в строке");
            for(String value:values)assertTrue(value.equals("0")||value.equals("1"),"Истина — 1, ложь — 0");
            assertNull(rows.put(values[0]+"/"+values[1],values),"Четыре различные пары P,Q");
        }
        return rows;
    }
    private static void pair(String key,String... expected) throws Exception {
        assertArrayEquals(expected,table().get(key),"Операции и отрицание P для пары "+key);
    }
    @Test @DisplayName("Операции AND/OR/XOR и отрицание P для самостоятельного сочетания. Вход: без аргументов")
    void pair_1_0() throws Exception { pair("1/0","1","0","0","1","1","0"); }
    @Test @DisplayName("Операции AND/OR/XOR и отрицание P для самостоятельного сочетания. Вход: без аргументов")
    void pair_0_0() throws Exception { pair("0/0","0","0","0","0","0","1"); }
}
