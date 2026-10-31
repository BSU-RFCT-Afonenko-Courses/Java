import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
static class Item implements Comparable<Item>{final int value;Item(int value){this.value=value;}public int compareTo(Item other){return Integer.compare(value,other.value);}}static class Token implements Comparable<Token>{final int value;Token(int value){this.value=value;}public int compareTo(Token other){return 23*(value-other.value);}}private static void kept(Object[] before,Object[] after){assertEquals(before.length,after.length);for(int i=0;i<before.length;i++)assertSame(before[i],after[i]);}
@Test @DisplayName("ra1") void ra1(org.junit.jupiter.api.TestReporter reporter)throws Throwable {
{Integer[] a={99,2,7,4},before=a.clone();Integer actual=Study.maximum(a,1,4);reporter.publishEntry(java.util.Map.of("public-case","normal","actual","Максимум="+actual+"; исходная ссылка="+(actual==before[2])));assertSame(before[2],actual);kept(before,a);}
for(Integer[] a:new Integer[][]{{99,2,7,4},{100,-9,-3,-4,101}}){Integer[] before=a.clone();assertSame(before[2],Study.maximum(a,1,4));kept(before,a);}
}
@Test @DisplayName("ra2") void ra2(org.junit.jupiter.api.TestReporter reporter)throws Throwable {
{String[] a={new String("я"),new String("б"),new String("в")},before=a.clone();String actual=Study.maximum(a,1,2);reporter.publishEntry(java.util.Map.of("public-case","boundary","actual","Максимум="+actual+"; исходная ссылка="+(actual==before[1])));assertSame(before[1],actual);kept(before,a);}
String[] a={new String("я"),new String("б"),new String("в")};var before=a.clone();assertSame(before[1],Study.maximum(a,1,2));kept(before,a);Integer[] b={-5};var old=b.clone();assertSame(old[0],Study.maximum(b,0,1));kept(old,b);
}
@Test @DisplayName("ra3") void ra3()throws Throwable {

Item a=new Item(2),b=new Item(2);Item[] input={new Item(-1),a,b},before=input.clone();Item actual=Study.maximum(input,0,3);assertTrue(actual==a||actual==b);kept(before,input);
}
@Test @DisplayName("ra4") void ra4()throws Throwable {

Token a=new Token(8),b=new Token(8);Token[] input={new Token(20),new Token(3),a,b,new Token(21)},before=input.clone();Token result=Study.maximum(input,1,4);assertTrue(result==a||result==b);kept(before,input);Integer[] data={-9,-3,-4},old=data.clone();assertSame(old[1],Study.maximum(data,0,3));kept(old,data);
}
}
