public class Study { static final boolean INIT=broken();private static boolean broken(){throw new IllegalStateException("Проверка инициализации");}
 public static <T extends Comparable<T>> T maximum(T[] array,int begin,int end){T best=array[begin];for(int i=begin+1;i<end;i++)if(array[i].compareTo(best)>0)best=array[i];return best;}
 void main(){System.out.println(maximum(new Integer[]{99,2,7,4},1,4));System.out.println(maximum(new String[]{"я","б","в"},1,2));System.out.println(maximum(new Integer[]{1,7,7},0,3));}
}
