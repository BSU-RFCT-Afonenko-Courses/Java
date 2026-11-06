public class Main { public static void main(String[] args) { var s = new java.util.Scanner(System.in); var b = Size.bytes(s.nextInt(),s.nextInt(),s.nextInt()); System.out.printf(java.util.Locale.ROOT,"%s\t%.18e", b, b.doubleValue()/1048576.0); } }

class Size { public static java.math.BigInteger bytes(int w,int h,int d) { return java.math.BigInteger.valueOf(w).multiply(java.math.BigInteger.valueOf(h)).multiply(java.math.BigInteger.valueOf(d)); } }
