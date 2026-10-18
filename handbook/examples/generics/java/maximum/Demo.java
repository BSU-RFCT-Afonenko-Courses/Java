import java.util.*;
class Demo {
    static <T extends Comparable<T>> T maximum(List<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Empty list");
        }
        T best = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            T value = values.get(i);
            if (value.compareTo(best) > 0) {
                best = value;
            }
        }
        return best;
    }
    public static void main(String[] args) {
        System.out.println(maximum(List.of(4, 9, 2))); // 9
    }
}
