class ArrayCase {
static String run(int n){int[] vals=new int[n];try{vals[18]=10;return Integer.toString(vals[18]);}catch(ArrayIndexOutOfBoundsException exc){return "caught";}}
public static void main(String[] args){System.out.println(run(10));System.out.println(run(20));}
}
