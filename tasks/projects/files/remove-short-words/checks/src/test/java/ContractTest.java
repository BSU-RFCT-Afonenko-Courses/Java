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
@Test void codepointsOddEligibleAndEmptyRows()throws Exception{var out=output();TaskBinding.run(input("😀😀😀 abc\nabc abcd abcde z\n a   bb \n\n"),out);assertEquals(List.of("","abcde z","a bb",""),lines(out));var e=output();TaskBinding.run(input(""),e);assertEquals(0,Files.size(e));}
@Test void punctuationCountsAsCodepoints()throws Exception{var out=output();TaskBinding.run(input("ab! xy!\n"),out);assertEquals(List.of(""),lines(out));}
}
