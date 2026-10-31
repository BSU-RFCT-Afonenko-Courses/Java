import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static List<Integer> arrayProcess(int count){var r=Elimination.array(count);var a=new ArrayList<>(r.removed());a.add(r.survivor());return a;}
 public static List<Integer> linkedProcess(int count){var r=Elimination.linked(count);var a=new ArrayList<>(r.removed());a.add(r.survivor());return a;}
}
