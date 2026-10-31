import java.lang.Integer;
import java.lang.String;
import java.lang.Throwable;
import java.util.Map;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
static class All<T> implements Study.Property<T>{public boolean test(T value){return true;}}static class None<T> implements Study.Property<T>{public boolean test(T value){return false;}}static class Odd implements Study.Property<Integer>{public boolean test(Integer value){return value%2!=0;}}static class Negative implements Study.Property<Integer>{public boolean test(Integer value){return value<0;}}static class LongText implements Study.Property<String>{public boolean test(String value){return value.length()>3;}}record Payload(int id){}static class Select implements Study.Property<Payload>{final int rule;Select(int rule){this.rule=rule;}public boolean test(Payload value){return rule==0?value.id()<0:value.id()==7;}}private static <T>void count(T[] a,Study.Property<T> p,int expected){T[] before=a.clone();assertEquals(expected,Study.count(a,p));for(int i=0;i<a.length;i++)assertSame(before[i],a[i]);}
@Test @DisplayName("pa1") void pa1(TestReporter reporter)throws Throwable {
{Integer[] a={1,2,3};String[] b={"one","four",""};int x=Study.count(a,new Odd()),y=Study.count(b,new LongText());reporter.publishEntry(Map.of("public-case","normal","actual","Нечётность="+x+"; длина>3="+y));assertEquals(2,x);assertEquals(1,y);}
count(new Integer[]{1,2,3},new Odd(),2);count(new String[]{"one","four",""},new LongText(),1);
}
@Test @DisplayName("pa2") void pa2()throws Throwable {

for(Integer[] a:new Integer[][]{{-11,4,7,12,-3},{-3,2,-1}}){count(a,new All<>(),a.length);count(a,new None<>(),0);count(a,new Negative(),2);}for(String[] a:new String[][]{{"north","","west","x","abc"},{"","😀","abcd","abcde"}}){count(a,new LongText(),2);count(a,new All<>(),a.length);count(a,new None<>(),0);}
}
@Test @DisplayName("pa3") void pa3(TestReporter reporter)throws Throwable {
{int a=Study.count(new Integer[0],new Odd()),b=Study.count(new String[0],new LongText());reporter.publishEntry(Map.of("public-case","boundary","actual","Integer="+a+"; String="+b));assertEquals(0,a);assertEquals(0,b);}
count(new Integer[0],new Odd(),0);count(new String[0],new LongText(),0);
}
@Test @DisplayName("pa4") void pa4()throws Throwable {

Payload[] a={new Payload(-2),new Payload(0),new Payload(7),new Payload(-2)};count(a,new Select(0),2);count(a,new Select(1),1);
}
}
