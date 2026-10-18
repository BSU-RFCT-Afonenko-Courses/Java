public class Study {
static boolean prime(int x){if(x<2)return false;for(int d=2;d*d<=x;d++)if(x%d==0)return false;return true;}public static String primes(int n){return java.util.stream.IntStream.iterate(3,x->x+1).filter(Study::prime).limit(n).mapToObj(Integer::toString).collect(java.util.stream.Collectors.joining(" "));}public static void main(String[] args){int n=args.length==0?5:Integer.parseInt(args[0]);System.out.print(primes(n));}
}
