import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object complex(double real, double imaginary){return new alternate.Complex(real,imaginary);}
 public static double[] parts(Object complex){return new double[]{((alternate.Complex)complex).real(),((alternate.Complex)complex).imaginary()};}
 public static Object[] array(Object... values){return Arrays.copyOf(values,values.length,alternate.Complex[].class);}
 public static Object add(Object first, Object second){return ((alternate.Complex)first).add((alternate.Complex)second);}
 public static Object subtract(Object first, Object second){return ((alternate.Complex)first).subtract((alternate.Complex)second);}
 public static Object multiply(Object first, Object second){return ((alternate.Complex)first).multiply((alternate.Complex)second);}
 public static Object divide(Object first, Object second){return ((alternate.Complex)first).divide((alternate.Complex)second);}
 public static Object sum(Object[] values){return alternate.Complex.sum((alternate.Complex[])values);}
 public static Object product(Object[] values){return alternate.Complex.product((alternate.Complex[])values);}
}
