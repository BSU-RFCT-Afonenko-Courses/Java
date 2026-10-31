import java.io.IOException;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Math;
import java.lang.String;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
@TempDir Path dir;private int counter;private Path input(String text)throws Exception{var p=dir.resolve("Собственные данные "+counter+++".txt");Files.writeString(p,text,StandardCharsets.UTF_8);return p;}private static void near(double expected,double actual){assertTrue(Double.isFinite(actual),"Результат должен быть конечным");assertEquals(expected,actual,Math.max(1e-12,1e-12*Math.abs(expected)));}private void bad(String text,long line)throws Exception{var p=input(text);var rows=Study.read(p);Study.BadRecord e=assertThrows(Study.BadRecord.class,()->Study.analyze(rows));assertEquals(line,e.line());assertNotNull(e.getCause());}private void second(String line)throws Exception{bad("1\ten-US\n"+line+"\n",2);}
@Test @DisplayName("la1") void la1(TestReporter reporter)throws Throwable {
{var rows=Study.read(input("1,5\tru-BY\n2.5\ten-US\n"));var texts=new ArrayList<String>();for(var row:rows)texts.add(row.text());reporter.publishEntry(Map.of("public-case","normal-read","actual","Исходные строки="+texts));assertEquals(List.of("1,5\tru-BY","2.5\ten-US"),texts);}
String[] lines={"1,5\tru-BY","2.5tail\ten-US","","1\ten-US\textra"};for(String ending:new String[]{"\n","\r\n"}){var rows=Study.read(input(String.join(ending,lines)+ending));assertEquals(4,rows.size());for(int i=0;i<lines.length;i++){assertEquals(i+1L,rows.get(i).line());assertEquals(lines[i],rows.get(i).text());}}
}
@Test @DisplayName("la2") void la2(TestReporter reporter)throws Throwable {
{var result=Study.analyze(Study.read(input("1,5\tru-BY\n2.5\ten-US\n")));reporter.publishEntry(Map.of("public-case","normal","actual","Количество="+result.count()+"; сумма="+result.sum()+"; среднее="+result.average()));assertEquals(2,result.count());near(4,result.sum());near(2,result.average().orElseThrow());}
var a=Study.analyze(Study.read(input("1,5\tru-BY\n2.5\ten-US\n")));assertEquals(2,a.count());near(4,a.sum());near(2,a.average().orElseThrow());var b=Study.analyze(Study.read(input("3.25\ten-US\n-1,5\tru-BY\n3.25\ten-US\n")));assertEquals(3,b.count());near(5,b.sum());near(5.0/3,b.average().orElseThrow());
}
@Test @DisplayName("la3") void la3(TestReporter reporter)throws Throwable {
{var result=Study.analyze(Study.read(input("")));reporter.publishEntry(Map.of("public-case","boundary","actual","Количество="+result.count()+"; среднее="+result.average()));assertEquals(0,result.count());assertTrue(result.average().isEmpty());}
var rows=Study.read(input(""));assertTrue(rows.isEmpty());var result=Study.analyze(rows);assertEquals(0,result.count());assertTrue(result.average().isEmpty());
}
@Test @DisplayName("la4") void la4()throws Throwable {

assertThrows(IOException.class,()->Study.read(dir.resolve("несуществующий файл.txt")));
}
@Test @DisplayName("la5") void la5()throws Throwable {

for(String line:new String[]{"\ten-US","1\t","1","1\ten-US\textra",""})second(line);var p=input("3.25\ten-US\n4,5\tru-BY\n\n8\ten-US\n");var rows=Study.read(p);assertEquals(4,rows.size());assertEquals("",rows.get(2).text());var e=assertThrows(Study.BadRecord.class,()->Study.analyze(rows));assertEquals(3,e.line());assertNotNull(e.getCause());
}
@Test @DisplayName("la6") void la6()throws Throwable {

bad("1\ten-US\n1\ten_US\n1\tanother_bad_tag\n",2);
}
@Test @DisplayName("la7") void la7()throws Throwable {

for(String line:new String[]{"bad\ten-US","2.5tail\ten-US"})second(line);
}
@Test @DisplayName("la8") void la8()throws Throwable {

for(String line:new String[]{"NaN\ten-US","∞\ten-US","9".repeat(400)+"\ten-US"})second(line);String huge="9".repeat(400);bad(huge+"\ten-US\n-"+huge+"\ten-US\n",1);
}
@Test @DisplayName("la9") void la9()throws Throwable {

var locale=new Locale.Builder().setLanguageTag("en-US-u-nu-latn").build();var pos=new ParsePosition(0);var parsed=NumberFormat.getInstance(locale).parse("2.5",pos);assertNotNull(parsed,"Требуется обслуживаемое расширение JDK25");assertEquals(3,pos.getIndex());var r=Study.analyze(Study.read(input("2.5\ten-US-u-nu-latn\n")));assertEquals(1,r.count());near(2.5,r.sum());near(2.5,r.average().orElseThrow());
}
@Test @DisplayName("la10") void la10()throws Throwable {

String big="1"+"0".repeat(308);var r=Study.analyze(Study.read(input(big+"\ten-US\n"+big+"\ten-US\n-"+big+"\ten-US\n")));assertEquals(3,r.count());near(1e308,r.sum());near(1e308/3,r.average().orElseThrow());
}
@Test @DisplayName("la11") void la11()throws Throwable {

String large="9"+"0".repeat(307);bad(large+"\ten-US\n"+large+"\ten-US\n",2);bad(large+"\ten-US\n"+large+"\ten-US\nbad\ten-US\n",3);
}
}
