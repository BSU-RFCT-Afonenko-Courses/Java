package variant;
class Display {void main(){var e=new Example();int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}};for(int[][] input:new int[][][]{a,new int[][]{{9,9},{9,9}}}){var g=e.remaining(input);System.out.println(g.rows()+"x"+g.columns()+" "+g.values());}}}
