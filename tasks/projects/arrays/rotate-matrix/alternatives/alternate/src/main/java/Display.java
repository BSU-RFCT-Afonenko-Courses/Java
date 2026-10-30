package variant;
class Display {void main(){var e=new Example();int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}};System.out.println(java.util.Arrays.deepToString(a));for(int angle:new int[]{90,180,270})System.out.println(java.util.Arrays.deepToString(e.rotate(a,angle)));System.out.println(java.util.Arrays.deepToString(a));}}
