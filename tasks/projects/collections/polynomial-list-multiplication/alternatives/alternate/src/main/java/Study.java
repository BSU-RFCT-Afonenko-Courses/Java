import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static List<Double> multiply(List<Double> first, List<Double> second) {
        return new Variant().apply(first, second);
    }
}

class Variant {
    public List<Double> apply(List<Double> a, List<Double> b) {
        var result = new LinkedList<Double>();
        if (a.isEmpty() || b.isEmpty()) return result;
        for (int k = 0; k < a.size() + b.size() - 1; k++) {
            double sum = 0;
            for (int i = Math.max(0, k - b.size() + 1); i <= Math.min(k, a.size() - 1); i++)
                sum += a.get(i) * b.get(k - i);
            result.add(sum);
        }
        return result;
    }
}
