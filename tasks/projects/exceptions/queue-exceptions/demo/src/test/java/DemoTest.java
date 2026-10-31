import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import queuecheck.CharAccess;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
private static String capture(Throwing body)throws Throwable {var out=System.out;var err=System.err;var bytes=new ByteArrayOutputStream();try(var sink=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(StandardCharsets.UTF_8);}interface Throwing{void run()throws Throwable;}
@Test @DisplayName("normal") void normal()throws Throwable {

var a=CharAccess.select();Object q=a.create(2);assertEquals("",capture(()->{a.put(q,'A');a.put(q,'Б');assertEquals('A',a.get(q));assertEquals('Б',a.get(q));}));
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

var a=CharAccess.select();Object q=a.create(2);a.put(q,'A');a.put(q,'Б');assertEquals('A',a.get(q));assertThrows(a.full,()->a.put(q,'В'));assertEquals('Б',a.get(q));assertThrows(a.empty,()->a.get(q));
}
}
