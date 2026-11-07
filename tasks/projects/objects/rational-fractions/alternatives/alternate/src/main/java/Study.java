import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object fraction(long numerator, long denominator){return new Rational(numerator,denominator);}
 public static long[] parts(Object fraction){return new long[]{((Rational)fraction).numerator(),((Rational)fraction).denominator()};}
 public static Object[] array(Object... fractions){return Arrays.copyOf(fractions,fractions.length,Rational[].class);}
 public static Object add(Object first, Object second){return ((Rational)first).add((Rational)second);}
 public static Object subtract(Object first, Object second){return ((Rational)first).subtract((Rational)second);}
 public static Object multiply(Object first, Object second){return ((Rational)first).multiply((Rational)second);}
 public static Object divide(Object first, Object second){return ((Rational)first).divide((Rational)second);}
 public static void transform(Object[] fractions){Rational.transform((Rational[])fractions);}
}

class Rational{private final long numerator,denominator;public Rational(long m,long n){long a=Math.abs(m),b=Math.abs(n);while(b!=0){long t=a%b;a=b;b=t;}long gcd=a==0?1:a;if(n<0)gcd=-gcd;numerator=m/gcd;denominator=n/gcd;}public long numerator(){return numerator;}public long denominator(){return denominator;}public Rational add(Rational b){return new Rational(numerator*b.denominator+b.numerator*denominator,denominator*b.denominator);}public Rational subtract(Rational b){return new Rational(numerator*b.denominator-b.numerator*denominator,denominator*b.denominator);}public Rational multiply(Rational b){return new Rational(numerator*b.numerator,denominator*b.denominator);}public Rational divide(Rational b){return new Rational(numerator*b.denominator,denominator*b.numerator);}public static void transform(Rational[]a){for(int i=0;i+1<a.length;i+=2)a[i]=a[i].add(a[i+1]);}public String toString(){return numerator+"/"+denominator;}}
