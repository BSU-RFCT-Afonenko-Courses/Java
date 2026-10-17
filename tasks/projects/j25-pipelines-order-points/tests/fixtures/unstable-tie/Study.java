public class Study {
record Point(int x,int y){int square(){return x*x+y*y;} public String toString(){return "("+x+":"+y+")";}}
public static String ordered(int[][] coordinates){return java.util.Arrays.stream(coordinates).map(a->new Point(a[0],a[1])).sorted((a,b)->java.util.Comparator.comparingInt(Point::square).thenComparingInt(Point::x).compare(a,b)).map(Point::toString).collect(java.util.stream.Collectors.joining(" "));}
}
