package variant;
public class Example {
public record Grid(int rows,int columns,java.util.List<Integer> values){}
public Grid remaining(int[][] a){int max=java.util.Arrays.stream(a).flatMapToInt(java.util.Arrays::stream).max().orElseThrow();var rows=new java.util.ArrayList<Integer>();var cols=new java.util.ArrayList<Integer>();for(int i=0;i<a.length;i++){boolean good=true;for(int x:a[i])if(x==max)good=false;if(good)rows.add(i);}for(int j=0;j<a.length;j++){boolean good=true;for(var row:a)if(row[j]==max)good=false;if(good)cols.add(j);}var values=new java.util.ArrayList<Integer>();for(int i:rows)for(int j:cols)values.add(a[i][j]);return new Grid(rows.size(),cols.size(),values);}
}
