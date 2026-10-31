public class Study {
 public static <T extends Comparable<T>> T maximum(T[] array,int begin,int end){java.util.Arrays.sort(array,begin,end);return array[end-1];}
 void main(){System.out.println(maximum(new Integer[]{99,2,7,4},1,4));System.out.println(maximum(new String[]{"я","б","в"},1,2));System.out.println(maximum(new Integer[]{1,7,7},0,3));}
}
