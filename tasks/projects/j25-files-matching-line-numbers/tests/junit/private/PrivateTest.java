import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void exactLineEmptySearchAndNoMatch()throws Exception{var out=output();TaskBinding.run(input("\nAlpha\nalpha beta\n alpha\n\n"),out,"");var r=TaskBinding.read(out);assertEquals(java.util.List.of("","Alpha","alpha beta"," alpha",""),r.lines());assertEquals(java.util.List.of(1,5),r.numbers());var q=output();TaskBinding.run(input("alpha beta\nAlpha Beta\n"),q,"alpha beta");assertEquals(java.util.List.of(1),TaskBinding.read(q).numbers());var spaced=output();TaskBinding.run(input(" alpha\nalpha\nalpha \n"),spaced,"alpha");assertEquals(java.util.List.of(2),TaskBinding.read(spaced).numbers());var none=output();TaskBinding.run(input("alpha"),none,"absent");assertEquals(java.util.List.of(),TaskBinding.read(none).numbers());}
}
