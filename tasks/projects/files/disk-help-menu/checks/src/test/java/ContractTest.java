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
@Test void unknownCaseIoAndEditableData()throws Exception{var file=input("#if\nsyntax\n\n");var missing=TaskBinding.lookup(file,"If");assertFalse(missing.found());assertFalse(missing.text().isBlank());var io=TaskBinding.lookup(dir.resolve("absent"),"if");assertFalse(io.found());assertFalse(io.text().isBlank());assertNotEquals(missing.text(),io.text());Files.writeString(file,"#new\nnew syntax\n\n",StandardCharsets.UTF_8);var added=TaskBinding.lookup(file,"new");assertTrue(added.found());assertTrue(added.text().contains("new syntax"));}
}
