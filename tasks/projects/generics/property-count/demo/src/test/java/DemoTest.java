import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
static class All<T> implements Study.Property<T>{public boolean test(T value){return true;}}static class None<T> implements Study.Property<T>{public boolean test(T value){return false;}}static class Odd implements Study.Property<Integer>{public boolean test(Integer value){return value%2!=0;}}static class Negative implements Study.Property<Integer>{public boolean test(Integer value){return value<0;}}static class LongText implements Study.Property<String>{public boolean test(String value){return value.length()>3;}}record Payload(int id){}static class Select implements Study.Property<Payload>{final int rule;Select(int rule){this.rule=rule;}public boolean test(Payload value){return rule==0?value.id()<0:value.id()==7;}}private static <T>void count(T[] a,Study.Property<T> p,int expected){T[] before=a.clone();assertEquals(expected,Study.count(a,p));for(int i=0;i<a.length;i++)assertSame(before[i],a[i]);}
@Test @DisplayName("normal") void normal()throws Throwable {

count(new Integer[]{1,2,3},new Odd(),2);count(new String[]{"one","four",""},new LongText(),1);
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

count(new Integer[0],new Odd(),0);count(new String[0],new LongText(),0);
}
}
