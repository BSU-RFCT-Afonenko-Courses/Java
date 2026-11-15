import java.util.*;

class SetOps {
    public static java.util.Set<Integer> union(java.util.Set<Integer> a, java.util.Set<Integer> b) {
        var out = new java.util.HashSet<>(a);
        out.addAll(b);
        return out;
    }

    public static java.util.Set<Integer> intersection(
            java.util.Set<Integer> a, java.util.Set<Integer> b) {
        var out = new java.util.HashSet<>(a);
        out.retainAll(b);
        return out;
    }
}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static Set<Integer> union(Set<Integer> first, Set<Integer> second) {
        return SetOps.union(first, second);
    }

    public static Set<Integer> intersection(Set<Integer> first, Set<Integer> second) {
        return SetOps.intersection(first, second);
    }
}
