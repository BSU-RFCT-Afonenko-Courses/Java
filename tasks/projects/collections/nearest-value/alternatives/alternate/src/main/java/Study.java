import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object create(){return new alternate.Variant();}
 public static void add(Object storage, double value){((alternate.Variant)storage).insert(value);}
 public static void remove(Object storage, double value){((alternate.Variant)storage).erase(value);}
 public static OptionalDouble nearest(Object storage, double query){return ((alternate.Variant)storage).find(query);}
}
