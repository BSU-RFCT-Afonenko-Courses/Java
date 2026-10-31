import java.lang.Exception;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import coursesupport.JavaProcess;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.time.Duration;import java.util.*;
class CopyContractTest {
 private static byte[] utf(String s){return s.getBytes(StandardCharsets.UTF_8);}
 private static Path classes()throws Exception{return Path.of(TaskBinding.entry().getProtectionDomain().getCodeSource().getLocation().toURI());}
 private static JavaProcess.Result run(Path cwd,String from,String to)throws Exception{return JavaProcess.run(classes(),TaskBinding.entry().getName(),"",List.of(from,to),cwd,Duration.ofSeconds(10),65536);}
 private static void refused(Path cwd,String from,String to,Path preserved,byte[] bytes)throws Exception{var r=run(cwd,from,to);assertTrue(Files.isRegularFile(preserved));assertArrayEquals(bytes,Files.readAllBytes(preserved));assertTrue(!r.stdout().isBlank()||!r.stderr().isBlank(),"Ожидается диагностика отказа; её предметный смысл проверяет преподаватель");}
 @Test void exactBytesAndOverwrite()throws Exception{Path d=CopySupport.workspace();try{for(String s:List.of("","  \t\u00A0\r\n\r\n","Привет мир\nвторая строка","x ".repeat(5000)+"end")){byte[]in=utf(s),want=utf(s.replace(' ','-'));Path from=d.resolve("источник.txt"),out=d.resolve("старый.txt");Files.write(from,in);Files.write(out,utf("OLD OLD OLD LONGER"));JavaProcess.runCheckingUncaught(TaskBinding.entry(),"",from.toString(),out.toString());assertTrue(Files.isRegularFile(from));assertTrue(Files.isRegularFile(out));assertArrayEquals(in,Files.readAllBytes(from));assertArrayEquals(want,Files.readAllBytes(out));}}finally{CopySupport.cleanup(d);}}
 @Test void relativePathsAndNewOutput()throws Exception{Path d=CopySupport.workspace();try{Files.write(d.resolve("in space.txt"),utf("ю я\t\r\nlast"));var r=run(d,"in space.txt","new space.txt");assertTrue(Files.isRegularFile(d.resolve("new space.txt")));assertArrayEquals(utf("ю-я\t\r\nlast"),Files.readAllBytes(d.resolve("new space.txt")));}finally{CopySupport.cleanup(d);}}
 @Test void absentInputDoesNotCreateOrTruncate()throws Exception{Path d=CopySupport.workspace();try{Path out=d.resolve("old");byte[]old=utf("Не менять");Files.write(out,old);refused(d,"absent","old",out,old);var r=run(d,"absent","new");assertFalse(Files.exists(d.resolve("new")));assertTrue(!r.stdout().isBlank()||!r.stderr().isBlank());}finally{CopySupport.cleanup(d);}}
 @Test void physicalIdentityBeforeTruncation()throws Exception{Path d=CopySupport.workspace();try{byte[]old=utf("A B\t\r\n");Path in=d.resolve("in");Files.write(in,old);refused(d,"in","./in",in,old);Files.createLink(d.resolve("hard"),in);refused(d,"in","hard",in,old);Files.createSymbolicLink(d.resolve("sym"),Path.of("in"));refused(d,"in","sym",in,old);}finally{CopySupport.cleanup(d);}}
 @Test void outputDirectoryIsRefused()throws Exception{Path d=CopySupport.workspace();try{byte[]old=utf("A B");Path in=d.resolve("in");Files.write(in,old);Files.createDirectory(d.resolve("outdir"));var r=run(d,"in","outdir");assertArrayEquals(old,Files.readAllBytes(in));assertTrue(Files.isDirectory(d.resolve("outdir")));assertTrue(!r.stdout().isBlank()||!r.stderr().isBlank());}finally{CopySupport.cleanup(d);}}
}
