import java.io.*;
import java.nio.charset.StandardCharsets;
/** Реальное наблюдение сообщений; оба потока восстановлены, исходный Throwable передаётся прямо. */
public final class PublicMessages {
 private PublicMessages(){}
 public static String capture(Runnable action){PrintStream previous=System.out,oldErr=System.err;ByteArrayOutputStream bytes=new ByteArrayOutputStream();try(PrintStream out=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(out);System.setErr(out);action.run();out.flush();return bytes.toString(StandardCharsets.UTF_8);}finally{System.setOut(previous);System.setErr(oldErr);}}
}
