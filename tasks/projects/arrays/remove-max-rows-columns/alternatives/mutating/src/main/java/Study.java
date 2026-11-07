public class Study {
    public static MatrixResult remove(int[][] matrix) {
        var grid = new Example().remaining(matrix);
        int[][] values = new int[grid.rows()][grid.columns()];
        int at = 0;
        for (int[] row : values) for (int j = 0; j < row.length; j++) row[j] = grid.values().get(at++);
        return new MatrixResult(grid.rows(), grid.columns(), values);
    }
}

class Show {
    void main() throws Throwable {
        for(int[][] a:new int[][][]{ {{-3,0,3},{2,-1,0},{1,3,-2}}, {{3,3},{3,3}}, {{9,1},{9,2}}, {{9,9},{1,2}} }) { System.out.println(java.util.Arrays.deepToString(a)); MatrixResult r=Study.remove(a); System.out.println(r.rows()+"x"+r.columns()+" "+java.util.Arrays.deepToString(r.values())); }
    }
}

class Example {
record Grid(int rows,int columns,java.util.List<Integer> values){}
public Grid remaining(int[][] a){int max=java.util.Arrays.stream(a).flatMapToInt(java.util.Arrays::stream).max().orElseThrow();var rows=new java.util.ArrayList<Integer>();var cols=new java.util.ArrayList<Integer>();for(int i=0;i<a.length;i++){boolean good=true;for(int x:a[i])if(x==max)good=false;if(good)rows.add(i);}for(int j=0;j<a.length;j++){boolean good=true;for(var row:a)if(row[j]==max)good=false;if(good)cols.add(j);}var values=new java.util.ArrayList<Integer>();for(int i:rows)for(int j:cols)values.add(a[i][j]);for(int[] row:a)java.util.Arrays.fill(row,0);return new Grid(rows.size(),cols.size(),values);}
}
