public class Study {
public static String select(int[] numbers){var r=new java.util.ArrayList<String>();for(int n:numbers){String s=Long.toString(Math.abs((long)n));if(s.contentEquals(new StringBuilder(s).reverse()))r.add(Integer.toString(n));}return r.isEmpty()?"Подходящих чисел нет":String.join(" ",r);}
}
