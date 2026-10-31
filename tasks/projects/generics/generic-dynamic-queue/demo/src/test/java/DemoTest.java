import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Integer;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
interface Throwing{void run()throws Throwable;}private static String capture(Throwing body)throws Throwable{var out=System.out;var err=System.err;var bytes=new ByteArrayOutputStream();try(var sink=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(StandardCharsets.UTF_8);}record Payload(int value){}static class Box<T>{final Study<T> value;Box(T[] storage)throws Throwable{value=new Study<T>(storage);}void put(T v)throws Throwable{value.put(v);}T get()throws Throwable{return value.get();}}private static void empty(Box<?> b)throws Throwable{assertFalse(capture(()->assertNull(b.get())).isBlank(),"Отказ пустого контейнера должен сообщить о себе");}private static <T>void full(Box<T>b,T rejected)throws Throwable{assertFalse(capture(()->b.put(rejected)).isBlank(),"Полный контейнер должен сообщить об отказе");}
@Test @DisplayName("normal") void normal()throws Throwable {

var s=new Box<String>(new String[2]);s.put("А");s.put("Б");s.put("В");assertEquals("А",s.get());s.put("Г");for(String v:new String[]{"Б","В","Г"})assertEquals(v,s.get());var i=new Box<Integer>(new Integer[2]);i.put(10);i.put(20);i.put(30);assertEquals(10,i.get());i.put(40);for(int v:new int[]{20,30,40})assertEquals(v,i.get());
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

var q=new Box<Integer>(new Integer[]{99});empty(q);q.put(7);q.put(8);assertEquals(7,q.get());assertEquals(8,q.get());empty(q);
}
}
