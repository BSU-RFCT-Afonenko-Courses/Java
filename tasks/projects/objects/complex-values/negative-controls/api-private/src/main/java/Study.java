import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study { static { if(Boolean.parseBoolean("true")) throw new AssertionError("API admission executed student initialization"); }
 private static Object complex(double real, double imaginary){return new Complex(real,imaginary);}
 public static double[] parts(Object complex){return new double[]{((Complex)complex).real(),((Complex)complex).imaginary()};}
 public static Object[] array(Object... values){return Arrays.copyOf(values,values.length,Complex[].class);}
 public static Object add(Object first, Object second){return ((Complex)first).add((Complex)second);}
 public static Object subtract(Object first, Object second){return ((Complex)first).subtract((Complex)second);}
 public static Object multiply(Object first, Object second){return ((Complex)first).multiply((Complex)second);}
 public static Object divide(Object first, Object second){return ((Complex)first).divide((Complex)second);}
 public static Object sum(Object[] values){return Complex.sum((Complex[])values);}
 public static Object product(Object[] values){return Complex.product((Complex[])values);}
}
