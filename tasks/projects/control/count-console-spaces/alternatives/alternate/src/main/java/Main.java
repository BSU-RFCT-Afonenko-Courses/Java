public class Main { public static void main(String[] args) throws Exception { long n=0; for(int c;(c=System.in.read())!=-1;){if(c=='.')break;if(c==32)n++;}System.out.print("+"+n); } }
