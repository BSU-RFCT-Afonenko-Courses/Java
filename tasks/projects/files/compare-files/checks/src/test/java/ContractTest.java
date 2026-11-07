import java.lang.Exception;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
@TempDir Path dir;int sequence;
Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");Files.writeString(p,s,StandardCharsets.UTF_8);return p;}
Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
List<String> lines(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readAllLines(p,StandardCharsets.UTF_8);}
@Test void emptyPrefixExactUtf8AndFailure()throws Exception{var e=input("");assertTrue(TaskBinding.equal(e,input("")));assertFalse(TaskBinding.equal(e,input("x")));assertFalse(TaskBinding.equal(input("x"),e));assertFalse(TaskBinding.equal(input("Java text"),input("Java text\n")));assertTrue(TaskBinding.equal(input("Текст 😀\r\n"),input("Текст 😀\r\n")));assertThrows(Exception.class,()->TaskBinding.equal(dir.resolve("absent"),e));assertThrows(Exception.class,()->TaskBinding.equal(e,dir.resolve("absent")));}
}
