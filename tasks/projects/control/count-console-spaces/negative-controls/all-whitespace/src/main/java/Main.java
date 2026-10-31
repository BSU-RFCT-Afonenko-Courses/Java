public class Main { public static void main(String[] a) throws Exception {int n=0;for(int c;(c=System.in.read())!=-1&&c!='.';)if(Character.isWhitespace(c))n++;System.out.print(n); } }
