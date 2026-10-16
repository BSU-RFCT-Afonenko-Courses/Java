import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void publicByteComparison()throws Exception{var a=input("Java text");var b=input("Java text");var c=input("Java test");byte[] before=java.nio.file.Files.readAllBytes(a);assertTrue(TaskBinding.equal(a,b));assertFalse(TaskBinding.equal(a,c));assertFalse(TaskBinding.equal(a,input("Java text!")));assertArrayEquals(before,java.nio.file.Files.readAllBytes(a));}
}
