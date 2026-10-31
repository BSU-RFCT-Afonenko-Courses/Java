import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object complex(double real, double imaginary){return new Complex(real,imaginary);}
 public static double[] parts(Object complex){return new double[]{((Complex)complex).real(),((Complex)complex).imaginary()};}
 public static Object[] array(Object... values){return values.length==0?new String[0]:Arrays.copyOf(values,values.length,Complex[].class);}
 public static Object add(Object first, Object second){return ((Complex)first).add((Complex)second);}
 public static Object subtract(Object first, Object second){return ((Complex)first).subtract((Complex)second);}
 public static Object multiply(Object first, Object second){return ((Complex)first).multiply((Complex)second);}
 public static Object divide(Object first, Object second){return ((Complex)first).divide((Complex)second);}
 public static Object sum(Object[] values){return values.length==0?new Complex(0,0):Complex.sum((Complex[])values);}
 public static Object product(Object[] values){return values.length==0?new Complex(1,0):Complex.product((Complex[])values);}
}
