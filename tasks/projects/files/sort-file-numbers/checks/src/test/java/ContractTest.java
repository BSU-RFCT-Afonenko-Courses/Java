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
String contents(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readString(p);}

@TempDir Path dir;int sequence;
Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");Files.writeString(p,s,StandardCharsets.UTF_8);return p;}
Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
List<String> lines(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readAllLines(p,StandardCharsets.UTF_8);}
@Test void limitsSeparatorsEmptyAndReadBeforeOutput()throws Exception{var out=output();TaskBinding.run(input("2147483647\t-2147483648\n0 2147483647"),out);assertEquals("-2147483648 0 2147483647 2147483647",contents(out).strip());var empty=output();TaskBinding.run(input(" \t\r\n"),empty);assertTrue(contents(empty).isBlank());var absent=output();TaskBinding.refused(()->TaskBinding.run(dir.resolve("missing"),absent));assertFalse(Files.exists(absent));}
}
