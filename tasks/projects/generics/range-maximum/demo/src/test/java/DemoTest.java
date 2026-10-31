import org.junit.jupiter.api.Test;import org.junit.jupiter.api.DisplayName;import static org.junit.jupiter.api.Assertions.*;
class DemoTest {
static class Item implements Comparable<Item>{final int value;Item(int value){this.value=value;}public int compareTo(Item other){return Integer.compare(value,other.value);}}static class Token implements Comparable<Token>{final int value;Token(int value){this.value=value;}public int compareTo(Token other){return 23*(value-other.value);}}private static void kept(Object[] before,Object[] after){assertEquals(before.length,after.length);for(int i=0;i<before.length;i++)assertSame(before[i],after[i]);}
@Test @DisplayName("normal") void normal()throws Throwable {

Integer[] a={99,2,7,4},before=a.clone();assertSame(before[2],Study.maximum(a,1,4));kept(before,a);
}
@Test @DisplayName("boundary") void boundary()throws Throwable {

String[] a={new String("я"),new String("б"),new String("в")},before=a.clone();assertSame(before[1],Study.maximum(a,1,2));kept(before,a);
}
}
