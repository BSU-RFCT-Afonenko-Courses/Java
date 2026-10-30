public class Main {public static void main(){System.out.println("P Q AND OR XOR NOT");for(int p=0;p<2;p++)for(int q=0;q<2;q++)System.out.println(p+" "+q+" "+(p&q)+" "+(p|q)+" "+(p^q)+" "+(1-p));}}
