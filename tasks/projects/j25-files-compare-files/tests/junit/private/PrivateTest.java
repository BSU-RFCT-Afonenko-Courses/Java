import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void emptyPrefixExactUtf8AndFailure()throws Exception{var e=input("");assertTrue(TaskBinding.equal(e,input("")));assertFalse(TaskBinding.equal(e,input("x")));assertFalse(TaskBinding.equal(input("x"),e));assertFalse(TaskBinding.equal(input("Java text"),input("Java text\n")));assertTrue(TaskBinding.equal(input("Текст 😀\r\n"),input("Текст 😀\r\n")));assertThrows(Exception.class,()->TaskBinding.equal(dir.resolve("absent"),e));assertThrows(Exception.class,()->TaskBinding.equal(e,dir.resolve("absent")));}
}
