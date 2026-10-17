package variant;
class DifferentArrayCase {
String run(int n){int[] vals=new int[n];try{vals[18]=10;return Integer.toString(vals[18]);}catch(ArrayIndexOutOfBoundsException exc){return "caught";}}
void demonstrate(){System.out.println(run(10));System.out.println(run(20));}
}
