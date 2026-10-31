package other;
public class Bubbles {public int[] order(int[] a){boolean changed;do{changed=false;for(int j=1;j<a.length;j++)if(a[j-1]>a[j]){int t=a[j];a[j]=a[j-1];a[j-1]=t;changed=true;}}while(changed);return a;}}
