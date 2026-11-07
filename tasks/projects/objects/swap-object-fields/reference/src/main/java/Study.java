import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object create(int value){return new Test(value);}
 public static int value(Object test){return ((Test)test).a;}
 public static void swap(Object first, Object second){Test.swap((Test)first,(Test)second);}
}
