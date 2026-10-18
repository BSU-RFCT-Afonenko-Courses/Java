import java.util.ArrayList;
import java.util.List;

class Demo {
    static void reverseFirstTwo(List<?> values) {
        if (values.size() >= 2) {
            swapFirstTwo(values);
        }
    }

    private static <T> void swapFirstTwo(List<T> values) {
        T first = values.get(0);
        values.set(0, values.get(1));
        values.set(1, first);
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Ada", "Lin"));
        reverseFirstTwo(names);
        System.out.println(names); // [Lin, Ada]
    }
}
