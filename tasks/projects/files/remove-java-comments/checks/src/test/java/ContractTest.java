import java.lang.Exception;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
String contents(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readString(p);}

@TempDir Path dir;int sequence;
Path input(String s)throws Exception{var p=dir.resolve("вход-"+(sequence++)+".txt");Files.writeString(p,s,StandardCharsets.UTF_8);return p;}
Path output(){return dir.resolve("выход-"+(sequence++)).resolve("результат.txt");}
List<String> lines(Path p)throws Exception{assertTrue(Files.isRegularFile(p),"Результат должен существовать до чтения");return Files.readAllLines(p,StandardCharsets.UTF_8);}
@Test void escapedQuotesBackslashAndBlockLineSeparators()throws Exception{var out=output();TaskBinding.run(input("class X { String s = \"quote: \\\" and slash \\\\\"; char quote='\\''; char backslash='\\\\'; /*a\r\nb\nc*/int/**/z;//tail\r\n}"),out);var actual=contents(out);assertTrue(Set.of("class X { String s = \"quote: \\\" and slash \\\\\"; char quote='\\''; char backslash='\\\\';  \r\n\nint z;\r\n}","class X { String s = \"quote: \\\" and slash \\\\\"; char quote='\\''; char backslash='\\\\'; \r\n \nint z;\r\n}","class X { String s = \"quote: \\\" and slash \\\\\"; char quote='\\''; char backslash='\\\\'; \r\n\n int z;\r\n}").contains(actual),()->"Ожидается один замещающий пробел, CRLF и LF в исходном порядке; положение пробела свободно. Получено: "+CopySupport.escaped(actual.getBytes(StandardCharsets.UTF_8)));var last=output();TaskBinding.run(input("class X{}//EOF"),last);assertEquals("class X{}",contents(last));var e=output();TaskBinding.run(input(""),e);assertEquals("",contents(e));}
}
