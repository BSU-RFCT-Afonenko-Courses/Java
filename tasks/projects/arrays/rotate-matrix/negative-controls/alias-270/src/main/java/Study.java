public class Study {
public static int[][] rotate(int[][] a,int angle){if(angle==270)return a;int n=a.length;int[][] r=new int[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)switch(angle){case 90->r[n-1-j][i]=a[i][j];case 180->r[n-1-i][n-1-j]=a[i][j];case 270->r[j][n-1-i]=a[i][j];}return r;}
}
