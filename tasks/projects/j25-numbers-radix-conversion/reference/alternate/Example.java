package variant;
public class Example {
public String convert(String decimal,int radix){return new java.math.BigInteger(decimal).toString(radix).toUpperCase(java.util.Locale.ROOT);}
}
