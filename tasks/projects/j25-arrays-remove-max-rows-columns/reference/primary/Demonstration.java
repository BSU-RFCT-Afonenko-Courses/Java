class Demonstration {
static void show(int[][] a){int[] r=Study.remove(a);System.out.println(r[0]+"x"+r[1]+" "+java.util.Arrays.toString(java.util.Arrays.copyOfRange(r,2,r.length)));}
void main(){show(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}});show(new int[][]{{9,9},{9,9}});}
}
