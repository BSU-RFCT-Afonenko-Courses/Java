package variant;
public class Example {
record Place(int x,int y){}
public String display(java.util.List<int[]> input){return input.stream().map(a->new Place(a[0],a[1])).sorted((a,b)->Double.compare(Math.hypot(a.x(),a.y()),Math.hypot(b.x(),b.y()))).map(p->"("+p.x()+":"+p.y()+")").collect(java.util.stream.Collectors.joining("\n"));}
}
