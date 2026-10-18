import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void publicHelpLookupAndSelection()throws Exception{var file=input("#if\nif syntax\nsecond line\n\n#for\nfor syntax\n\n");var r=TaskBinding.lookup(file,"if");assertTrue(r.found());assertTrue(r.text().contains("if syntax"));assertTrue(r.text().indexOf("if syntax")<r.text().indexOf("second line"));assertFalse(r.text().contains("for syntax"));var choices=TaskBinding.selections(file);assertEquals(java.util.List.of("if","for"),choices.values());assertFalse(choices.closed());}
}
