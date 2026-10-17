import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void publicTranspose()throws Exception{var in=input("1 2 3\n4 5 6\n7 8 9\n");byte[] bytes=java.nio.file.Files.readAllBytes(in);var out=output();TaskBinding.run(in,out);var r=TaskBinding.read(out);assertTrue(java.util.Arrays.deepEquals(new int[][]{{1,2,3},{4,5,6},{7,8,9}},r.source()));assertTrue(java.util.Arrays.deepEquals(new int[][]{{1,4,7},{2,5,8},{3,6,9}},r.transposed()));assertArrayEquals(bytes,java.nio.file.Files.readAllBytes(in));}
}
