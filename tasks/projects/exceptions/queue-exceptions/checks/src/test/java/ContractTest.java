import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.TestReporter;
import queuecheck.CharAccess;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
private static String capture(Throwing body)throws Throwable {var out=System.out;var err=System.err;var bytes=new ByteArrayOutputStream();try(var sink=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(sink);System.setErr(sink);body.run();}finally{System.setOut(out);System.setErr(err);}return bytes.toString(StandardCharsets.UTF_8);}interface Throwing{void run()throws Throwable;}
@Test @DisplayName("qa1") void qa1(TestReporter reporter)throws Throwable {
{var a=CharAccess.select();Object q=a.create(2);char[] got=new char[2];String output=capture(()->{a.put(q,'A');a.put(q,'Б');got[0]=a.get(q);got[1]=a.get(q);});reporter.publishEntry(Map.of("public-case","normal","actual","Извлечено="+Arrays.toString(got)+"; операции молчат="+output.isEmpty()));assertArrayEquals(new char[]{'A','Б'},got);assertTrue(output.isEmpty());}
var a=CharAccess.select();for(char[] data:new char[][]{{'A','Б'},{'€','\ud800','Z'}}){Object q=a.create(data.length);assertEquals("",capture(()->{for(char c:data)a.put(q,c);for(char c:data)assertEquals(c,a.get(q));}));}
}
@Test @DisplayName("qa2") void qa2()throws Throwable {

var a=CharAccess.select();Object q=a.create(3);for(char c:new char[]{'a','b','c'})a.put(q,c);for(char c:new char[]{'X','Y'})assertThrows(a.full,()->a.put(q,c));for(char c:new char[]{'a','b','c'})assertEquals(c,a.get(q));
}
@Test @DisplayName("qa3") void qa3(TestReporter reporter)throws Throwable {
{var a=CharAccess.select();Object q=a.create(2);a.put(q,'A');a.put(q,'Б');a.get(q);a.get(q);boolean empty=false;try{a.get(q);}catch(Throwable e){if(!a.empty.isInstance(e))throw e;empty=true;}reporter.publishEntry(Map.of("public-case","boundary-empty","actual","QueueEmptyException="+empty));assertTrue(empty);}
var a=CharAccess.select();Object q=a.create(2);for(int i=0;i<2;i++)assertThrows(a.empty,()->a.get(q));a.put(q,'x');a.put(q,'y');assertEquals('x',a.get(q));assertEquals('y',a.get(q));
}
@Test @DisplayName("qa4") void qa4(TestReporter reporter)throws Throwable {
{var a=CharAccess.select();Object q=a.create(2);a.put(q,'A');a.put(q,'Б');char first=a.get(q);boolean full=false;try{a.put(q,'В');}catch(Throwable e){if(!a.full.isInstance(e))throw e;full=true;}char last=a.get(q);reporter.publishEntry(Map.of("public-case","boundary-full","actual","Извлечено="+first+","+last+"; QueueFullException="+full));assertEquals('A',first);assertTrue(full);assertEquals('Б',last);}
var a=CharAccess.select();Object q=a.create(2);a.put(q,'A');a.put(q,'Б');assertEquals('A',a.get(q));assertThrows(a.full,()->a.put(q,'В'));assertEquals('Б',a.get(q));assertThrows(a.empty,()->a.get(q));Object v=a.create(4);for(char c:new char[]{'a','a','b','c'})a.put(v,c);assertEquals('a',a.get(v));assertEquals('a',a.get(v));assertThrows(a.full,()->a.put(v,'d'));assertEquals('b',a.get(v));assertEquals('c',a.get(v));
}
@Test @DisplayName("qa5") void qa5()throws Throwable {

var a=CharAccess.select();Object q=a.create(1);a.put(q,'X');assertEquals('X',a.get(q));for(int i=0;i<2;i++){assertThrows(a.full,()->a.put(q,'Y'));assertThrows(a.empty,()->a.get(q));}
}
@Test @DisplayName("qa6") void qa6()throws Throwable {

var a=CharAccess.select();for(int n:new int[]{0,-1,Integer.MIN_VALUE})assertThrows(IllegalArgumentException.class,()->a.create(n));
}
@Test @DisplayName("qa7") void qa7()throws Throwable {

var a=CharAccess.select();Object q=a.create(2),v=a.create(1);a.put(q,'A');a.put(v,'Б');assertEquals('Б',a.get(v));a.put(q,'В');assertEquals('A',a.get(q));assertEquals('В',a.get(q));
}
}
