import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static void partition(List<Integer> values, int threshold) {
        new Variant().apply(values, threshold);
    }
}

class Variant {
    public void apply(List<Integer> values, int x) {
        int next = 0;
        for (int i = 0; i < values.size(); i++)
            if (values.get(i) <= x) {
                Integer value = values.get(i);
                for (int j = i; j > next; j--) values.set(j, values.get(j - 1));
                values.set(next++, value);
            }
    }
}
