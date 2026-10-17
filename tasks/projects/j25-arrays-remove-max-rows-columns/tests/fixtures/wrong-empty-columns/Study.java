public class Study {
public static int[] remove(int[][] a){int n=a.length,max=Integer.MIN_VALUE;for(var row:a)for(int x:row)max=Math.max(max,x);boolean[] rr=new boolean[n],cc=new boolean[n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)if(a[i][j]==max){rr[i]=true;cc[j]=true;}int nr=0,nc=0;for(boolean x:rr)if(!x)nr++;for(boolean x:cc)if(!x)nc++;int[] r=new int[2+nr*nc];r[0]=nr;r[1]=nr==0?0:nc;int at=2;for(int i=0;i<n;i++)if(!rr[i])for(int j=0;j<n;j++)if(!cc[j])r[at++]=a[i][j];return r;}
}
