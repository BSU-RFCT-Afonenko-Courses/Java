public class Study {
 public interface Property<T>{boolean test(T value);}
 public static <T> int count(T[] array,Property<T> property){return 0;}
 static class Odd implements Property<Integer>{public boolean test(Integer value){return value%2!=0;}}
 static class LongText implements Property<String>{public boolean test(String value){return value.length()>3;}}
 void main(){System.out.println(count(new Integer[]{1,2,3},new Odd()));System.out.println(count(new String[]{"one","four",""},new LongText()));System.out.println(count(new Integer[]{1,3},new Odd()));System.out.println(count(new Integer[]{2,4},new Odd()));System.out.println(count(new Integer[0],new Odd()));}
}
