import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void physicalEmptyRowsSpaceOnlyAndZero()throws Exception{var a=input("first  two\n\nlast   x\n");var out=output();TaskBinding.run(a,out,9,2);assertEquals(java.util.List.of("","last x"),lines(out));var zero=output();TaskBinding.run(a,zero,0,2);assertEquals(java.util.List.of("",""),lines(zero));var none=output();TaskBinding.run(a,none,2,0);assertEquals(0,java.nio.file.Files.size(none));var e=output();TaskBinding.run(input(""),e,1,2);assertEquals(0,java.nio.file.Files.size(e));var tab=output();TaskBinding.run(input(" a\tb  c \n"),tab,2,1);assertEquals(java.util.List.of("a\tb c"),lines(tab));}
}
