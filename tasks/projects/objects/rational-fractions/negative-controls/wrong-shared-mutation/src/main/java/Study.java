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
