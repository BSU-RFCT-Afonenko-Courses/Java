import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@org.junit.jupiter.api.io.TempDir java.nio.file.Path dir;int sequence;
java.nio.file.Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");java.nio.file.Files.writeString(p,s,java.nio.charset.StandardCharsets.UTF_8);return p;}
java.nio.file.Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
java.util.List<String> lines(java.nio.file.Path p)throws Exception{return java.nio.file.Files.readAllLines(p,java.nio.charset.StandardCharsets.UTF_8);}
@Test void escapedQuotesBackslashAndBlockLineSeparators()throws Exception{var out=output();TaskBinding.run(input("class X { String s = \"quote: \\\" and slash \\\\\"; char quote='\\''; char backslash='\\\\'; /*a\r\nb\nc*/int/**/z;//tail\r\n}"),out);assertEquals("class X { String s = \"quote: \\\" and slash \\\\\"; char quote='\\''; char backslash='\\\\';  \r\n\nint z;\r\n}",java.nio.file.Files.readString(out));var last=output();TaskBinding.run(input("class X{}//EOF"),last);assertEquals("class X{}",java.nio.file.Files.readString(last));var e=output();TaskBinding.run(input(""),e);assertEquals("",java.nio.file.Files.readString(e));}
}
