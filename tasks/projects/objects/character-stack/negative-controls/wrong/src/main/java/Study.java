import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object create(int capacity){return new Stack(capacity);}
 public static void push(Object stack, char value){((Stack)stack).push(value);}
 public static char pop(Object stack){return ((Stack)stack).pop();}
}
