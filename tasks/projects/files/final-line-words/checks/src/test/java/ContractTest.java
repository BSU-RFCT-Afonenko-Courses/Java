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
@Test void physicalEmptyRowsSpaceOnlyAndZero()throws Exception{var a=input("first  two\n\nlast   x\n");var out=output();TaskBinding.run(a,out,9,2);assertEquals(List.of("","last x"),lines(out));var zero=output();TaskBinding.run(a,zero,0,2);assertEquals(List.of("",""),lines(zero));var none=output();TaskBinding.run(a,none,2,0);assertEquals(0,Files.size(none));var e=output();TaskBinding.run(input(""),e,1,2);assertEquals(0,Files.size(e));var tab=output();TaskBinding.run(input(" a\tb  c \n"),tab,2,1);assertEquals(List.of("a\tb c"),lines(tab));}
}
