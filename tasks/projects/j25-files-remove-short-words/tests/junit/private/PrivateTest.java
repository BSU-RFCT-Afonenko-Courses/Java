import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void codepointsOddEligibleAndEmptyRows()throws Exception{var out=output();TaskBinding.run(input("😀😀😀 abc\nabc abcd abcde z\n a   bb \n\n"),out);assertEquals(java.util.List.of("","abcde z","a bb",""),lines(out));var e=output();TaskBinding.run(input(""),e);assertEquals(0,java.nio.file.Files.size(e));}
}
