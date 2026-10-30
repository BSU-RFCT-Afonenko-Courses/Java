public class Study {public static int[] sort(int[] a){int[] r=a.clone();for(int end=r.length-1;end>0;end--)for(int i=0;i<end;i++)if(r[i]>r[i+1]){int x=r[i];r[i]=r[i+1];r[i+1]=x;}return r;}}
