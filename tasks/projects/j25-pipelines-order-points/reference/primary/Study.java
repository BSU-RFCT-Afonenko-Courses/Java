public class Study {
record Point(int x,int y){int square(){return x*x+y*y;} public String toString(){return "("+x+":"+y+")";}}
public static String ordered(int[][] coordinates){return java.util.Arrays.stream(coordinates).map(a->new Point(a[0],a[1])).sorted((a,b)->Integer.compare(a.square(),b.square())).map(Point::toString).collect(java.util.stream.Collectors.joining(" "));}
}
