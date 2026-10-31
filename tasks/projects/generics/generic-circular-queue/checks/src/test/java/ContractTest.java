import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
interface Throwing{void run()throws Throwable;}private static String capture(Throwing body)throws Throwable{var out=System.out;var err=System.err;var bytes=new ByteArrayOutputStream();try(var sink=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(StandardCharsets.UTF_8);}record Payload(int value){}static class Box<T>{final Study<T> value;Box(T[] storage)throws Throwable{value=new Study<T>(storage);}void put(T v)throws Throwable{value.put(v);}T get()throws Throwable{return value.get();}}private static void empty(Box<?> b)throws Throwable{assertFalse(capture(()->assertNull(b.get())).isBlank(),"Отказ пустого контейнера должен сообщить о себе");}private static <T>void full(Box<T>b,T rejected)throws Throwable{assertFalse(capture(()->b.put(rejected)).isBlank(),"Полный контейнер должен сообщить об отказе");}
@Test @DisplayName("cqa1") void cqa1(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{99});Object[] value=new Object[1];String output=capture(()->value[0]=q.get());reporter.publishEntry(Map.of("public-case","boundary-initial","actual","Извлечено="+value[0]+"; сообщение="+!output.isBlank()));assertNull(value[0]);assertFalse(output.isBlank());}
var i=new Box<Integer>(new Integer[]{99});empty(i);Integer fresh=1000;i.put(fresh);assertSame(fresh,i.get());var s=new Box<String>(new String[]{"old","values"});empty(s);String x=new String("fresh");s.put(x);assertSame(x,s.get());
}
@Test @DisplayName("cqa2") void cqa2(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{99});q.put(7);String output=capture(()->q.put(8));Integer value=q.get();reporter.publishEntry(Map.of("public-case","boundary-full","actual","Сообщение="+!output.isBlank()+"; извлечено="+value));assertFalse(output.isBlank());assertEquals(7,value);}
for(int capacity:new int[]{1,2,4}){var q=new Box<Payload>(new Payload[capacity]);Payload[] data=new Payload[capacity];for(int i=0;i<capacity;i++){data[i]=new Payload(i);q.put(data[i]);}full(q,new Payload(-1));full(q,new Payload(-2));for(int j=0;j<capacity;j++)assertSame(data[j],q.get());}var i=new Box<Integer>(new Integer[1]);i.put(7);full(i,8);assertEquals(7,i.get());
}
@Test @DisplayName("cqa3") void cqa3(TestReporter reporter)throws Throwable {
{var s=new Box<String>(new String[3]);s.put("А");s.put("Б");s.put("В");var got=new ArrayList<Object>();got.add(s.get());got.add(s.get());s.put("Г");s.put("Д");got.add(s.get());got.add(s.get());got.add(s.get());var i=new Box<Integer>(new Integer[2]);i.put(10);i.put(20);got.add(i.get());i.put(30);got.add(i.get());got.add(i.get());reporter.publishEntry(Map.of("public-case","normal","actual","Извлечено="+got));assertEquals(List.of("А","Б","В","Г","Д",10,20,30),got);}
var q=new Box<String>(new String[2]);String a=new String("Лист"),b=new String("Ветка"),c=new String("Плод");q.put(a);q.put(b);assertSame(a,q.get());q.put(c);assertSame(b,q.get());assertSame(c,q.get());var v=new Box<Number>(new Number[2]);Number x=Integer.valueOf(17),y=Double.valueOf(2.5);v.put(x);v.put(y);assertSame(x,v.get());assertSame(y,v.get());var s=new Box<String>(new String[3]);for(String ch:new String[]{"А","Б","В"})s.put(ch);assertEquals("А",s.get());assertEquals("Б",s.get());s.put("Г");s.put("Д");for(String ch:new String[]{"В","Г","Д"})assertEquals(ch,s.get());var i=new Box<Integer>(new Integer[2]);i.put(10);i.put(20);assertEquals(10,i.get());i.put(30);assertEquals(20,i.get());assertEquals(30,i.get());var p=new Box<Payload>(new Payload[1]);Payload item=new Payload(71);p.put(item);assertSame(item,p.get());
}
@Test @DisplayName("cqa4") void cqa4(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{99});q.put(7);q.get();q.put(8);Integer value=q.get();reporter.publishEntry(Map.of("public-case","boundary-reuse","actual","Извлечено="+value));assertEquals(8,value);}
for(int capacity:new int[]{2,3}){var q=new Box<Payload>(new Payload[capacity]);var expected=new ArrayDeque<Payload>();for(int i=0;i<capacity;i++){Payload p=new Payload(i);expected.add(p);q.put(p);}for(int i=capacity;i<40;i++){assertSame(expected.remove(),q.get());Payload p=new Payload(i);expected.add(p);q.put(p);}while(!expected.isEmpty())assertSame(expected.remove(),q.get());}var four=new Box<Payload>(new Payload[4]);Payload[] values={new Payload(71),new Payload(29),new Payload(83),new Payload(47)};for(Payload p:values)four.put(p);full(four,new Payload(-1));assertSame(values[0],four.get());four.put(values[0]);for(int j:new int[]{1,2,3,0})assertSame(values[j],four.get());var single=new Box<Integer>(new Integer[1]);for(int j=0;j<10;j++){Integer p=1000+j;single.put(p);assertSame(p,single.get());}single.put(9);assertEquals(9,single.get());
}
@Test @DisplayName("cqa5") void cqa5(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{99});q.put(7);q.get();q.put(8);q.get();Object[] value=new Object[1];String output=capture(()->value[0]=q.get());reporter.publishEntry(Map.of("public-case","boundary-empty","actual","Извлечено="+value[0]+"; сообщение="+!output.isBlank()));assertNull(value[0]);assertFalse(output.isBlank());}
var q=new Box<String>(new String[2]);empty(q);empty(q);String a=new String("a");q.put(a);assertSame(a,q.get());empty(q);empty(q);String b=new String("b");q.put(b);assertSame(b,q.get());
}
@Test @DisplayName("cqa6") void cqa6()throws Throwable {

var a=new Box<String>(new String[2]);var b=new Box<String>(new String[1]);String x=new String("A"),y=new String("B");a.put(x);b.put(y);full(b,new String("reject"));assertSame(y,b.get());empty(b);assertSame(x,a.get());
}
}
