import java.io.*;
import java.nio.charset.StandardCharsets;
/** Наблюдение реальной печати; исходный Throwable передаётся вызывающему коду. */
public final class PublicOutput {
 private PublicOutput() {}
 public static String capture(Runnable action){PrintStream saved=System.out;var bytes=new ByteArrayOutputStream();try(var out=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(out);action.run();out.flush();return bytes.toString(StandardCharsets.UTF_8);}finally{System.setOut(saved);}}
 public static String escaped(String value){if(value.isEmpty())return "Пустой стандартный вывод.";return "\""+value.replace("\\","\\\\").replace("\"","\\\"").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t")+"\"";}
}
