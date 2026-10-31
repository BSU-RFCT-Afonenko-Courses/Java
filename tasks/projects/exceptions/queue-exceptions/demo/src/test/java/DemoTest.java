import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
private static String capture(Throwing body)throws Throwable {var out=System.out;var err=System.err;var bytes=new java.io.ByteArrayOutputStream();try(var sink=new java.io.PrintStream(bytes,true,java.nio.charset.StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(java.nio.charset.StandardCharsets.UTF_8);}interface Throwing{void run()throws Throwable;}
@Test @DisplayName("normal") void normal()throws Throwable {

var a=queuecheck.CharAccess.select();Object q=a.create(2);assertEquals("",capture(()->{a.put(q,'A');a.put(q,'Б');assertEquals('A',a.get(q));assertEquals('Б',a.get(q));}));
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

var a=queuecheck.CharAccess.select();Object q=a.create(2);a.put(q,'A');a.put(q,'Б');assertEquals('A',a.get(q));assertThrows(a.full,()->a.put(q,'В'));assertEquals('Б',a.get(q));assertThrows(a.empty,()->a.get(q));
}
}
