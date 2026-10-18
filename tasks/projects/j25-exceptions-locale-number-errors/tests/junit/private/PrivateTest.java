import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void actuallyServicedUnicodeExtension()throws Throwable {
 var locale=new java.util.Locale.Builder().setLanguageTag("en-US-u-nu-latn").build();
 var pos=new java.text.ParsePosition(0);var parsed=java.text.NumberFormat.getInstance(locale).parse("2.5",pos);
 assertNotNull(parsed);assertEquals(3,pos.getIndex());assertEquals(2.5,parsed.doubleValue());
 var result=TaskBinding.run(input("2.5\ten-US-u-nu-latn\n"));assertEquals(1,result.count());assertEquals(2.5,result.sum());
}

@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int counter;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("числа с пробелом "+counter+++".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
void bad(String line)throws Exception{var e=assertThrows(Throwable.class,()->TaskBinding.run(input("1\ten-US\n"+line+"\n")));assertTrue(TaskBinding.recordError(e));assertFalse(e instanceof RuntimeException||e instanceof Error);assertEquals(2,TaskBinding.line(e));assertNotNull(e.getCause());}
@Test void wholeParseTagFieldsFiniteAndPhysicalRows()throws Exception{for(String line:new String[]{"2.5tail\ten-US","\ten-US","1\t","1\ten_US","1\ten-US\textra","NaN\ten-US","∞\ten-US","9".repeat(400)+"\ten-US",""})bad(line);}
@Test void readPreservesOriginalAndDoesNotValidate()throws Exception{var file=input("1,5\tru-BY\n2.5tail\ten-US\n");assertEquals(java.util.List.of(java.util.List.of("1,5","ru-BY"),java.util.List.of("2.5tail","en-US")),TaskBinding.read(file));}
@Test void finiteCancellationAndInvalidFinalSum()throws Throwable{String big="1"+"0".repeat(308);var r=TaskBinding.run(input(big+"\ten-US\n"+big+"\ten-US\n-"+big+"\ten-US\n"));assertEquals(3,r.count());assertEquals(1e308,r.sum());assertTrue(Double.isFinite(r.average().orElseThrow()));String over="9"+"0".repeat(307);var e=assertThrows(Throwable.class,()->TaskBinding.run(input(over+"\ten-US\n"+over+"\ten-US\n")));assertTrue(TaskBinding.recordError(e));assertEquals(2,TaskBinding.line(e));assertNotNull(e.getCause());String huge="9".repeat(400);var first=assertThrows(Throwable.class,()->TaskBinding.run(input(huge+"\ten-US\n-"+huge+"\ten-US\n")));assertTrue(TaskBinding.recordError(first));assertEquals(1,TaskBinding.line(first));}
}
