import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void overlappingAndSamePrefixesDuplicatesAndLeadingZeros()throws Exception{String text="291; Анна \n291; Анна \n029;Ноль\n33;Борис\n";var out=output();TaskBinding.run(input(text),out,"29","2");assertEquals(java.util.List.of("291; Анна ","291; Анна "),lines(out));var equal=output();TaskBinding.run(input(text),equal,"29","29");assertEquals(lines(out),lines(equal));var zero=output();TaskBinding.run(input(text),zero,"0","99");assertEquals(java.util.List.of("029;Ноль"),lines(zero));var none=output();TaskBinding.run(input(text),none,"8","9");assertTrue(lines(none).isEmpty());}
}
