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
@Test void overlappingAndSamePrefixesDuplicatesAndLeadingZeros()throws Exception{String text="291; Анна \n291; Анна \n029;Ноль\n33;Борис\n";var out=output();TaskBinding.run(input(text),out,"29","2");assertEquals(List.of("291; Анна ","291; Анна "),lines(out));var equal=output();TaskBinding.run(input(text),equal,"29","29");assertEquals(lines(out),lines(equal));var zero=output();TaskBinding.run(input(text),zero,"0","99");assertEquals(List.of("029;Ноль"),lines(zero));var none=output();TaskBinding.run(input(text),none,"8","9");assertTrue(lines(none).isEmpty());}
}
