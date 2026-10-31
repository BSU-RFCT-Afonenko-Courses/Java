import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
class Entry {
 public static Object create(int value){return new Test(value);}
 public static int value(Object test){return ((Test)test).a;}
 public static void swap(Object first, Object second){Test.swap((Test)first,(Test)second);}
}

public class Study extends Entry {}
