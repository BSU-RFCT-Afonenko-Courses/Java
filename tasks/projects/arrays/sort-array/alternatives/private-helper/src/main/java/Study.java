public class Study {public static int[] sort(int[] a) throws java.io.IOException { return bubble(a); }
private static int[] bubble(int[] a){int[] r=a.clone();for(int end=r.length-1;end>0;end--)for(int i=0;i<end;i++)if(r[i]>r[i+1]){int x=r[i];r[i]=r[i+1];r[i+1]=x;}return r;}}

class Display {void main() throws java.io.IOException {int[] a={7,-2,7,0,3,-5};System.out.println(java.util.Arrays.toString(a));System.out.println(java.util.Arrays.toString(Study.sort(a)));for(int[] b:new int[][]{new int[0],{7}})System.out.println(java.util.Arrays.toString(Study.sort(b)));}}
