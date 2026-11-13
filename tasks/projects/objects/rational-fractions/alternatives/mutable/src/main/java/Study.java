import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static Object fraction(long numerator, long denominator) {
        return new Rational(numerator, denominator);
    }

    public static long[] parts(Object fraction) {
        return new long[] {((Rational) fraction).numerator(), ((Rational) fraction).denominator()};
    }

    public static Object[] array(Object... fractions) {
        return Arrays.copyOf(fractions, fractions.length, Rational[].class);
    }

    public static Object add(Object first, Object second) {
        return ((Rational) first).add((Rational) second);
    }

    public static Object subtract(Object first, Object second) {
        return ((Rational) first).subtract((Rational) second);
    }

    public static Object multiply(Object first, Object second) {
        return ((Rational) first).multiply((Rational) second);
    }

    public static Object divide(Object first, Object second) {
        return ((Rational) first).divide((Rational) second);
    }

    public static void transform(Object[] fractions) {
        Rational.transform((Rational[]) fractions);
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println(
                "Договор: арифметические операции изменяют и возвращают текущий объект; следующий вызов использует его новое значение.");
        Rational a = new Rational(1, 2), b = new Rational(1, 3);
        System.out.println(
                a.add(b) + "; " + a.subtract(b) + "; " + a.multiply(b) + "; " + a.divide(b));
        Rational[] input = new Rational[args.length];
        for (int i = 0; i < args.length; i++) {
            String[] parts = args[i].split("/");
            input[i] = new Rational(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
        System.out.println("Введено через аргументы: " + java.util.Arrays.toString(input));
        Rational.transform(input);
        System.out.println("Результат: " + java.util.Arrays.toString(input));
    }
}

class Rational {
    private long m, n;

    public Rational(long m, long n) {
        this.m = m;
        this.n = n;
    }

    public long numerator() {
        return m;
    }

    public long denominator() {
        return n;
    }

    public Rational add(Rational b) {
        long a = m * b.n + b.m * n, d = n * b.n;
        m = a;
        n = d;
        return this;
    }

    public Rational subtract(Rational b) {
        long a = m * b.n - b.m * n, d = n * b.n;
        m = a;
        n = d;
        return this;
    }

    public Rational multiply(Rational b) {
        long a = m * b.m, d = n * b.n;
        m = a;
        n = d;
        return this;
    }

    public Rational divide(Rational b) {
        long a = m * b.n, d = n * b.m;
        m = a;
        n = d;
        return this;
    }

    public static void transform(Rational[] a) {
        Rational[] old = new Rational[a.length];
        for (int i = 0; i < a.length; i++) old[i] = new Rational(a[i].m, a[i].n);
        for (int i = 0; i + 1 < a.length; i += 2)
            a[i] = new Rational(old[i].m, old[i].n).add(old[i + 1]);
    }

    public String toString() {
        return m + "/" + n;
    }
}
