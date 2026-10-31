package other;
public class Rows {public static void show(java.io.InputStream in){int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}};var s=new java.util.Scanner(in);if(s.hasNextInt())for(int i=0;i<3;i++)for(int j=0;j<3;j++)a[i][j]=s.nextInt();for(int[] row:a){int[] b=new int[3];int at=0;for(int x:row)if(x!=0)b[at++]=x;System.out.println(b[0]+"\t"+b[1]+" "+b[2]);}}}
