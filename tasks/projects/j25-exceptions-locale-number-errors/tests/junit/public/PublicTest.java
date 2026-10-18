import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int counter;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("числа с пробелом "+counter+++".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
@Test void publicLocalesAndEmpty()throws Throwable{var r=TaskBinding.run(input("1,5\tru-BY\n2.5\ten-US\n"));assertEquals(2,r.count());assertEquals(4.0,r.sum());assertEquals(2.0,r.average().orElseThrow());var empty=TaskBinding.run(input(""));assertEquals(0,empty.count());assertTrue(empty.average().isEmpty());}
@Test void badLineNumberCauseAndMissingFile()throws Throwable{var e=assertThrows(Throwable.class,()->TaskBinding.run(input("1\ten-US\nbad\ten-US\n")));assertTrue(TaskBinding.recordError(e));assertFalse(e instanceof RuntimeException||e instanceof Error);assertEquals(2,TaskBinding.line(e));assertNotNull(e.getCause());assertThrows(java.io.IOException.class,()->TaskBinding.read(dir.resolve("absent")));}
}
