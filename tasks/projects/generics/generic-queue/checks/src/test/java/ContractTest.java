import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Class;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.TestReporter;
import queuecheck.QueueAccess;
import queuecheck.QueueAccess.Payload;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
interface Throwing{void run()throws Throwable;}private static String capture(Throwing body)throws Throwable{var out=System.out;var err=System.err;var bytes=new ByteArrayOutputStream();try(var sink=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(StandardCharsets.UTF_8);}static class Box<T>{final QueueAccess access;final Object value;final Class<? extends Throwable>full,empty;Box(T[] storage)throws Throwable{access=QueueAccess.select();value=access.create(storage);full=access.full;empty=access.empty;}void put(T v)throws Throwable{access.put(value,v);}@SuppressWarnings("unchecked")T get()throws Throwable{return (T)access.get(value);}}private static void empty(Box<?> b)throws Throwable{assertThrows(b.empty,()->b.get());}private static <T>void full(Box<T>b,T rejected)throws Throwable{assertThrows(b.full,()->b.put(rejected));}
@Test @DisplayName("gqa1") void gqa1(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{42});boolean empty=false;try{q.get();}catch(Throwable e){if(!q.empty.isInstance(e))throw e;empty=true;}reporter.publishEntry(Map.of("public-case","boundary-initial","actual","Проверяемый Empty="+empty));assertTrue(empty);}
var i=new Box<Integer>(new Integer[]{42});empty(i);Integer fresh=1000;i.put(fresh);assertSame(fresh,i.get());var s=new Box<String>(new String[]{"old","values"});empty(s);String x=new String("fresh");s.put(x);assertSame(x,s.get());
}
@Test @DisplayName("gqa2") void gqa2(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{42});q.put(7);boolean full=false;try{q.put(8);}catch(Throwable e){if(!q.full.isInstance(e))throw e;full=true;}Integer actual=q.get();reporter.publishEntry(Map.of("public-case","boundary-full","actual","Проверяемый Full="+full+"; извлечено="+actual));assertTrue(full);assertEquals(7,actual);}
for(int capacity:new int[]{1,2,5}){var q=new Box<Payload>(new Payload[capacity]);Payload[] data=new Payload[capacity];for(int i=0;i<capacity;i++){data[i]=new Payload(i);q.put(data[i]);}full(q,new Payload(-1));full(q,new Payload(-2));for(int j=0;j<capacity;j++)assertSame(data[j],q.get());}var i=new Box<Integer>(new Integer[1]);i.put(7);full(i,8);assertEquals(7,i.get());
}
@Test @DisplayName("gqa3") void gqa3(TestReporter reporter)throws Throwable {
{var q=new Box<String>(new String[2]);String a=new String("Лист"),b=new String("Ветка"),c=new String("Плод");q.put(a);q.put(b);String x=q.get();q.put(c);String y=q.get(),z=q.get();reporter.publishEntry(Map.of("public-case","normal","actual","Извлечено="+List.of(x,y,z)+"; прежние ссылки="+(x==a)+"/"+(y==b)+"/"+(z==c)));assertSame(a,x);assertSame(b,y);assertSame(c,z);}
var q=new Box<String>(new String[2]);String a=new String("Лист"),b=new String("Ветка"),c=new String("Плод");q.put(a);q.put(b);assertSame(a,q.get());q.put(c);assertSame(b,q.get());assertSame(c,q.get());var v=new Box<Number>(new Number[2]);Number x=Integer.valueOf(17),y=Double.valueOf(2.5);v.put(x);v.put(y);assertSame(x,v.get());assertSame(y,v.get());
}
@Test @DisplayName("gqa4") void gqa4(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{42});q.put(7);q.get();q.put(9);Integer actual=q.get();reporter.publishEntry(Map.of("public-case","boundary-reuse","actual","Извлечено="+actual));assertEquals(9,actual);}
for(int capacity:new int[]{2,3}){var q=new Box<Payload>(new Payload[capacity]);var expected=new ArrayDeque<Payload>();for(int i=0;i<capacity;i++){Payload p=new Payload(i);expected.add(p);q.put(p);}for(int i=capacity;i<40;i++){assertSame(expected.remove(),q.get());Payload p=new Payload(i);expected.add(p);q.put(p);}while(!expected.isEmpty())assertSame(expected.remove(),q.get());}var four=new Box<Payload>(new Payload[4]);Payload[] values={new Payload(71),new Payload(29),new Payload(83),new Payload(47)};for(Payload p:values)four.put(p);full(four,new Payload(-1));assertSame(values[0],four.get());four.put(values[0]);for(int j:new int[]{1,2,3,0})assertSame(values[j],four.get());var single=new Box<Integer>(new Integer[1]);for(int j=0;j<10;j++){Integer p=1000+j;single.put(p);assertSame(p,single.get());}single.put(9);assertEquals(9,single.get());
}
@Test @DisplayName("gqa5") void gqa5(TestReporter reporter)throws Throwable {
{var q=new Box<Integer>(new Integer[]{42});q.put(7);q.get();boolean empty=false;try{q.get();}catch(Throwable e){if(!q.empty.isInstance(e))throw e;empty=true;}reporter.publishEntry(Map.of("public-case","boundary-empty","actual","Проверяемый Empty="+empty));assertTrue(empty);}
var q=new Box<String>(new String[2]);empty(q);empty(q);String a=new String("a");q.put(a);assertSame(a,q.get());empty(q);empty(q);String b=new String("b");q.put(b);assertSame(b,q.get());
}
@Test @DisplayName("gqa6") void gqa6()throws Throwable {

var a=new Box<String>(new String[2]);var b=new Box<String>(new String[1]);String x=new String("A"),y=new String("B");a.put(x);b.put(y);full(b,new String("reject"));assertSame(y,b.get());empty(b);assertSame(x,a.get());
}
}
