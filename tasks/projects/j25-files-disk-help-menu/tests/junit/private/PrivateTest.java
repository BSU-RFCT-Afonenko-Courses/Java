import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void unknownCaseIoAndEditableData()throws Exception{var file=input("#if\nsyntax\n\n");var missing=TaskBinding.lookup(file,"If");assertFalse(missing.found());assertFalse(missing.text().isBlank());var io=TaskBinding.lookup(dir.resolve("absent"),"if");assertFalse(io.found());assertFalse(io.text().isBlank());assertNotEquals(missing.text(),io.text());java.nio.file.Files.writeString(file,"#new\nnew syntax\n\n",java.nio.charset.StandardCharsets.UTF_8);var added=TaskBinding.lookup(file,"new");assertTrue(added.found());assertTrue(added.text().contains("new syntax"));}
}
