import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Set<Integer> union(Set<Integer> first, Set<Integer> second){return SetOps.union(first,second);}
 public static Set<Integer> intersection(Set<Integer> first, Set<Integer> second){return SetOps.intersection(first,second);}
}
