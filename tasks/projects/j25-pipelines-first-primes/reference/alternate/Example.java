package variant;
public class Example {
static boolean prime(int v){if(v==2)return true;if(v<2||v%2==0)return false;for(int d=3;d<=v/d;d+=2)if(v%d==0)return false;return true;}public int[] sequence(int n){return java.util.stream.IntStream.iterate(2,x->x+1).filter(Example::prime).limit(n).toArray();}public void demonstrate(int n){for(int p:sequence(n))System.out.println(p);}
}
