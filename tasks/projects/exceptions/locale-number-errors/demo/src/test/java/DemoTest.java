import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;private int counter;private java.nio.file.Path input(String text)throws Exception{var p=dir.resolve("Собственные данные "+counter+++".txt");java.nio.file.Files.writeString(p,text,java.nio.charset.StandardCharsets.UTF_8);return p;}private static void near(double expected,double actual){assertTrue(Double.isFinite(actual),"Результат должен быть конечным");assertEquals(expected,actual,Math.max(1e-12,1e-12*Math.abs(expected)));}private void bad(String text,long line)throws Exception{var p=input(text);var rows=Study.read(p);Study.BadRecord e=assertThrows(Study.BadRecord.class,()->Study.analyze(rows));assertEquals(line,e.line());assertNotNull(e.getCause());}private void second(String line)throws Exception{bad("1\ten-US\n"+line+"\n",2);}
@Test @DisplayName("normal") void normal()throws Throwable {

var rows=Study.read(input("1,5\tru-BY\n2.5\ten-US\n"));assertEquals(2,rows.size());assertEquals("1,5\tru-BY",rows.get(0).text());assertEquals("2.5\ten-US",rows.get(1).text());var r=Study.analyze(rows);assertEquals(2,r.count());near(4,r.sum());near(2,r.average().orElseThrow());
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

var rows=Study.read(input(""));assertTrue(rows.isEmpty());var r=Study.analyze(rows);assertEquals(0,r.count());assertTrue(r.average().isEmpty());
}
}
