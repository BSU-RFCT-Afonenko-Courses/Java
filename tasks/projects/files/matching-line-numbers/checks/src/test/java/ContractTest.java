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
TaskBinding.Observation read(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Обязательный отчёт не создан");assertTrue(Files.size(p)>0,"Пустой отчёт не содержит обязательных данных");return TaskBinding.read(p);}

@TempDir Path dir;int sequence;
Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");Files.writeString(p,s,StandardCharsets.UTF_8);return p;}
Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
List<String> lines(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readAllLines(p,StandardCharsets.UTF_8);}
@Test void exactLineEmptySearchAndNoMatch()throws Exception{var out=output();TaskBinding.run(input("\nAlpha\nalpha beta\n alpha\n\n"),out,"");var r=read(out);assertEquals(List.of("","Alpha","alpha beta"," alpha",""),r.lines());assertEquals(List.of(1,5),r.numbers());var q=output();TaskBinding.run(input("alpha beta\nAlpha Beta\n"),q,"alpha beta");assertEquals(List.of(1),read(q).numbers());var spaced=output();TaskBinding.run(input(" alpha\nalpha\nalpha \n"),spaced,"alpha");assertEquals(List.of(2),read(spaced).numbers());var none=output();TaskBinding.run(input("alpha"),none,"absent");assertEquals(List.of(),read(none).numbers());}
}
