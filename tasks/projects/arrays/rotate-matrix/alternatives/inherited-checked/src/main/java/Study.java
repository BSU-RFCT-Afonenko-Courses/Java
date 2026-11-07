public class Study extends Base { private Study() {} }

class Base {
public static int[][] rotate(int[][] a,int angle) throws java.io.IOException {int n=a.length;int[][] r=new int[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)switch(angle){case 90->r[n-1-j][i]=a[i][j];case 180->r[n-1-i][n-1-j]=a[i][j];case 270->r[j][n-1-i]=a[i][j];}return r;}
}

class Show {
    void main() throws Throwable {
        int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}}; System.out.println(java.util.Arrays.deepToString(a)); for(int angle:new int[]{90,180,270}) System.out.println(java.util.Arrays.deepToString(Study.rotate(a,angle))); System.out.println(java.util.Arrays.deepToString(a));
    }
}
