import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object fraction(long numerator, long denominator){return new alternate.Rational(numerator,denominator);}
 public static long[] parts(Object fraction){return new long[]{((alternate.Rational)fraction).numerator(),((alternate.Rational)fraction).denominator()};}
 public static Object[] array(Object... fractions){return Arrays.copyOf(fractions,fractions.length,alternate.Rational[].class);}
 public static Object add(Object first, Object second){return ((alternate.Rational)first).add((alternate.Rational)second);}
 public static Object subtract(Object first, Object second){return ((alternate.Rational)first).subtract((alternate.Rational)second);}
 public static Object multiply(Object first, Object second){return ((alternate.Rational)first).multiply((alternate.Rational)second);}
 public static Object divide(Object first, Object second){return ((alternate.Rational)first).divide((alternate.Rational)second);}
 public static void transform(Object[] fractions){alternate.Rational.transform((alternate.Rational[])fractions);}
}
