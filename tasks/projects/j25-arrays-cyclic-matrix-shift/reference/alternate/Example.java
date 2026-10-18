package variant;
public class Example {
public int[][] shift(int[][] a,int k,int direction){int n=a.length,s=k%n;int[][] r=new int[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++){int x=i,y=j;switch(direction){case 0->y=(j+s)%n;case 1->y=(j-s+n)%n;case 2->x=(i-s+n)%n;case 3->x=(i+s)%n;}r[x][y]=a[i][j];}return r;}
}
