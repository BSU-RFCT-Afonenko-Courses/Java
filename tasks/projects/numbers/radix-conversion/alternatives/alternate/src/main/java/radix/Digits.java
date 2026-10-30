package radix;
public class Digits {
    public static String convert(String value,int radix) {return new java.math.BigInteger(value).toString(radix).toUpperCase(java.util.Locale.ROOT);}
}
