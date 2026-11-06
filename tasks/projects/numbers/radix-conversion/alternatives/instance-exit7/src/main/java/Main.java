public class Main {void main(String[] args){try {System.out.println(Study.convert(args[0],Integer.parseInt(args[1])));} finally {System.exit(7); }}}

class Study {
public static String convert(String decimal,int radix){java.math.BigInteger n=new java.math.BigInteger(decimal);if(n.signum()==0)return "0";var b=new StringBuilder();var base=java.math.BigInteger.valueOf(radix);while(n.signum()>0){var qr=n.divideAndRemainder(base);b.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt(qr[1].intValue()));n=qr[0];}return b.reverse().toString();}
}
