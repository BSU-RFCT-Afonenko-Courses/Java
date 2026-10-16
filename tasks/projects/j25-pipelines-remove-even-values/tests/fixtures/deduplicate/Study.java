public class Study {
public static void compact(int[] a){int[] odd=java.util.Arrays.stream(a).filter(n->n%2!=0).distinct().toArray();System.arraycopy(odd,0,a,0,odd.length);java.util.Arrays.fill(a,odd.length,a.length,0);}
}
