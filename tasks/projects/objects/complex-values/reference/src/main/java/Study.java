import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static Object complex(double real, double imaginary) {
        return new Complex(real, imaginary);
    }

    public static double[] parts(Object complex) {
        return new double[] {((Complex) complex).real(), ((Complex) complex).imaginary()};
    }

    public static Object[] array(Object... values) {
        return Arrays.copyOf(values, values.length, Complex[].class);
    }

    public static Object add(Object first, Object second) {
        return ((Complex) first).add((Complex) second);
    }

    public static Object subtract(Object first, Object second) {
        return ((Complex) first).subtract((Complex) second);
    }

    public static Object multiply(Object first, Object second) {
        return ((Complex) first).multiply((Complex) second);
    }

    public static Object divide(Object first, Object second) {
        return ((Complex) first).divide((Complex) second);
    }

    public static Object sum(Object[] values) {
        return Complex.sum((Complex[]) values);
    }

    public static Object product(Object[] values) {
        return Complex.product((Complex[]) values);
    }
}

class Complex {
    private final double real, imaginary;

    public Complex(double r, double i) {
        real = r;
        imaginary = i;
    }

    public double real() {
        return real;
    }

    public double imaginary() {
        return imaginary;
    }

    public Complex add(Complex b) {
        return new Complex(real + b.real, imaginary + b.imaginary);
    }

    public Complex subtract(Complex b) {
        return new Complex(real - b.real, imaginary - b.imaginary);
    }

    public Complex multiply(Complex b) {
        return new Complex(
                real * b.real - imaginary * b.imaginary, real * b.imaginary + imaginary * b.real);
    }

    public Complex divide(Complex b) {
        double d = b.real * b.real + b.imaginary * b.imaginary;
        return new Complex(
                (real * b.real + imaginary * b.imaginary) / d,
                (imaginary * b.real - real * b.imaginary) / d);
    }

    public static Complex sum(Complex[] a) {
        Complex result = new Complex(0, 0);
        for (Complex b : a) result = result.add(b);
        return result;
    }

    public static Complex product(Complex[] a) {
        Complex result = new Complex(1, 0);
        for (Complex b : a) result = result.multiply(b);
        return result;
    }
}

class Main {
    public static void main(String[] args) {
        Complex a = new Complex(1, 2), b = new Complex(3, -4);
        for (Complex x :
                new Complex[] {
                    a.add(b),
                    a.subtract(b),
                    a.multiply(b),
                    a.divide(b),
                    Complex.sum(new Complex[] {new Complex(1, 1), new Complex(1, -1)}),
                    Complex.product(new Complex[] {new Complex(1, 1), new Complex(1, -1)})
                }) System.out.println(x.real() + " + " + x.imaginary() + "i");
        System.out.println("Пустое произведение: " + Complex.product(new Complex[0]).real());
    }
}
