import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.*;
/** Только файловые наблюдения; запуск JVM остаётся в каноническом JavaProcess. */
public final class CopySupport {
 private CopySupport(){}
 public static Path workspace()throws IOException{return Files.createDirectory(Path.of(System.getProperty("java.io.tmpdir"),"copy-observation-"+ProcessHandle.current().pid()+"-"+Long.toUnsignedString(System.nanoTime())));}
 public static void cleanup(Path root)throws IOException{if(!Files.exists(root))return;try(var paths=Files.walk(root)){for(Path p:paths.sorted(Comparator.reverseOrder()).toList())Files.deleteIfExists(p);}}
 public static String escaped(byte[] bytes){return "UTF-8="+quote(new String(bytes,StandardCharsets.UTF_8))+"; hex="+HexFormat.of().formatHex(bytes);}
 public static String quote(String s){return "\""+s.replace("\\","\\\\").replace("\"","\\\"").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t").replace("\u00A0","\\u00A0")+"\"";}
 public static String contents(Path p)throws IOException{return Files.isRegularFile(p)?escaped(Files.readAllBytes(p)):"Файл отсутствует";}
}
