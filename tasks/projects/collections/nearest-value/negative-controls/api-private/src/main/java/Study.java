import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study { static { if(Boolean.parseBoolean("true")) throw new AssertionError("API admission executed student initialization"); }
 private static Object create(){return new Nearby();}
 public static void add(Object storage, double value){((Nearby)storage).add(value);}
 public static void remove(Object storage, double value){((Nearby)storage).remove(value);}
 public static OptionalDouble nearest(Object storage, double query){return ((Nearby)storage).nearest(query);}
}
