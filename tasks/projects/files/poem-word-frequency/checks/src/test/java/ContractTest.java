import java.lang.Exception;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
class ContractTest {
 @TempDir Path dir;int seq;
 Path input(String text)throws Exception{Path p=dir.resolve("вход-"+(seq++));Files.writeString(p,text,StandardCharsets.UTF_8);return p;}
 Path output(){return dir.resolve("результат-"+(seq++));}
 @Test void independentRowsRepeatsStableZeros()throws Exception{
 Path out=output();TaskBinding.run(input("Лес лес! РЕКА\nни одного\n\nрека лес\n"),out,List.of("река","лес","ноль"));assertTrue(Files.isRegularFile(out));
 assertEquals(List.of(List.of(new TaskBinding.WordCount("ноль",0),new TaskBinding.WordCount("река",1),new TaskBinding.WordCount("лес",2)),List.of(new TaskBinding.WordCount("река",0),new TaskBinding.WordCount("лес",0),new TaskBinding.WordCount("ноль",0)),List.of(new TaskBinding.WordCount("река",0),new TaskBinding.WordCount("лес",0),new TaskBinding.WordCount("ноль",0)),List.of(new TaskBinding.WordCount("ноль",0),new TaskBinding.WordCount("река",1),new TaskBinding.WordCount("лес",1))),TaskBinding.read(out));
 Path empty=output();TaskBinding.run(input(""),empty,List.of("лес"));assertTrue(Files.isRegularFile(empty));assertTrue(TaskBinding.read(empty).isEmpty());
 }
}
