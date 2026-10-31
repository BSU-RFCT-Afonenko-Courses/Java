import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import queuecheck.QueueAccess;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
interface Throwing{void run()throws Throwable;}private static String capture(Throwing body)throws Throwable{var out=System.out;var err=System.err;var bytes=new ByteArrayOutputStream();try(var sink=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(StandardCharsets.UTF_8);}record Payload(int value){}static class Box<T>{final QueueAccess access;final Object value;final Class<? extends Throwable>full,empty;Box(T[] storage)throws Throwable{access=QueueAccess.select();value=access.create(storage);full=access.full;empty=access.empty;}void put(T v)throws Throwable{access.put(value,v);}@SuppressWarnings("unchecked")T get()throws Throwable{return (T)access.get(value);}}private static void empty(Box<?> b)throws Throwable{assertThrows(b.empty,()->b.get());}private static <T>void full(Box<T>b,T rejected)throws Throwable{assertThrows(b.full,()->b.put(rejected));}
@Test @DisplayName("normal") void normal()throws Throwable {

var s=new Box<String>(new String[2]);String a=new String("Лист"),b=new String("Ветка"),c=new String("Плод");s.put(a);s.put(b);assertSame(a,s.get());s.put(c);assertSame(b,s.get());assertSame(c,s.get());
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

var q=new Box<Integer>(new Integer[]{42});empty(q);q.put(7);full(q,8);assertEquals(7,q.get());empty(q);q.put(9);assertEquals(9,q.get());
}
}
