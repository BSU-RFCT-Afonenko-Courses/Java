public class Main { public static void main(String[] args) { var s=new java.util.Scanner(System.in); Range.print(s.nextInt(),s.nextInt(),s.nextInt()); } }

class Range { public static void print(int n,int m,int k) { int a=Integer.compare(k,n),b=Integer.compare(k,m); if(a==0)System.out.print("false\ntrue\tfalse\ntrue");else if(b==0)System.out.print("true\nfalse\tfalse\ntrue");else if(a<0 || b>0)System.out.print("false false false false");else System.out.print("true true true true"); } }
