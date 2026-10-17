import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void limitsSeparatorsEmptyAndReadBeforeOutput()throws Exception{var out=output();TaskBinding.run(input("2147483647\t-2147483648\n0 2147483647"),out);assertEquals("-2147483648 0 2147483647 2147483647",java.nio.file.Files.readString(out).strip());var empty=output();TaskBinding.run(input(" \t\r\n"),empty);assertTrue(java.nio.file.Files.readString(empty).isBlank());var absent=output();assertThrows(Exception.class,()->TaskBinding.run(dir.resolve("missing"),absent));assertFalse(java.nio.file.Files.exists(absent));}
}
