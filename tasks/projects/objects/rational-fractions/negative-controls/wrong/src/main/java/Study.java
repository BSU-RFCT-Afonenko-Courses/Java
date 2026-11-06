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

class Main {public static void main(String[]args){System.out.println("Договор: арифметические операции возвращают новые объекты.");Rational a=new Rational(1,2),b=new Rational(1,3);System.out.println(a.add(b)+"; "+a.subtract(b)+"; "+a.multiply(b)+"; "+a.divide(b));Rational[]input=new Rational[args.length];for(int i=0;i<args.length;i++){String[]parts=args[i].split("/");input[i]=new Rational(Long.parseLong(parts[0]),Long.parseLong(parts[1]));}System.out.println("Введено через аргументы: "+java.util.Arrays.toString(input));Rational.transform(input);System.out.println("Результат: "+java.util.Arrays.toString(input));}}

class Rational{private final long numerator,denominator;public Rational(long m,long n){numerator=m;denominator=n;}public long numerator(){return numerator;}public long denominator(){return denominator;}public Rational add(Rational b){return new Rational(numerator*b.denominator+b.numerator*denominator,denominator*b.denominator);}public Rational subtract(Rational b){return new Rational(numerator*b.denominator-b.numerator*denominator,denominator*b.denominator);}public Rational multiply(Rational b){return new Rational(numerator*b.numerator,denominator*b.denominator);}public Rational divide(Rational b){return new Rational(numerator*b.denominator,denominator*b.numerator);}public static void transform(Rational[]a){for(int i=0;i+1<a.length;i++)a[i]=a[i].add(a[i+1]);}public String toString(){return numerator+"/"+denominator;}}
