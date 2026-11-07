import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static int sum(int... values){return Arithmetic.sum(values);}
}

class Arithmetic { public static int sum(int... values){int result=0;for(int value:values)result+=value;return result;} }
