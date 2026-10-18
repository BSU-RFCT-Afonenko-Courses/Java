import java.util.*;
class Demo {
    interface Arithmetic<T> {
        T zero();
        T add(T left, T right);
        T multiply(T left, T right);
    }
    static <T> T dot(List<T> left, List<T> right, Arithmetic<T> arithmetic) {
        if (left.size() != right.size()) {
            throw new IllegalArgumentException("Different vector lengths");
        }
        T result = arithmetic.zero();
        for (int i = 0; i < left.size(); i++) {
            T term = arithmetic.multiply(left.get(i), right.get(i));
            result = arithmetic.add(result, term);
        }
        return result;
    }
    static final class DoubleArithmetic implements Arithmetic<Double> {
        public Double zero() {
            return 0.0;
        }
        public Double add(Double a, Double b) {
            return a + b;
        }
        public Double multiply(Double a, Double b) {
            return a * b;
        }
    }
    record Complex(double re, double im) {}

    static final class ComplexArithmetic implements Arithmetic<Complex> {
        public Complex zero() {
            return new Complex(0.0, 0.0);
        }

        public Complex add(Complex a, Complex b) {
            return new Complex(a.re() + b.re(), a.im() + b.im());
        }

        public Complex multiply(Complex a, Complex b) {
            return new Complex(
                a.re() * b.re() - a.im() * b.im(), a.re() * b.im() + a.im() * b.re());
        }
    }
    static final class WrongZeroArithmetic implements Arithmetic<Double> {
        public Double zero() {
            return 1.0;
        }
        public Double add(Double a, Double b) {
            return a + b;
        }
        public Double multiply(Double a, Double b) {
            return a * b;
        }
    }

    public static void main(String[] args) {
        double value = dot(List.of(1.0, 2.0), List.of(3.0, 4.0), new DoubleArithmetic());
        System.out.println(value); // 11.0
        System.out.println(dot(List.of(1.0, 2.0), List.of(3.0, 4.0),
            new WrongZeroArithmetic())); // 12.0
        System.out.println(dot(List.<Double>of(), List.<Double>of(),
            new WrongZeroArithmetic())); // 1.0
        System.out.println(dot(List.of(new Complex(1, 1), new Complex(2, 0)),
            List.of(new Complex(3, 0), new Complex(4, -1)), new ComplexArithmetic()));
    }
}
