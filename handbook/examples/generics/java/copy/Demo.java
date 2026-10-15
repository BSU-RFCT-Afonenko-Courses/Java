import java.util.*;
class Demo {
    static void printAll(List<?> values) {
        for (Object value : values) {
            System.out.println(value);
        }
    }
    static double sum(List<? extends Number> values) {
        double total = 0.0;
        for (Number value : values) {
            total += value.doubleValue();
        }
        return total;
    }
    static void addSmallIntegers(List<? super Integer> target) {
        target.add(1);
        target.add(2);
    }
    static <T> void copy(List<? extends T> source, List<? super T> target) {
        for (T value : source) {
            target.add(value);
        }
    }

    public static void main(String[] args) {
        List<Integer> ints = List.of(1, 2);
        List<Number> numbers = new ArrayList<>();
        numbers.add(0.5);
        copy(ints, numbers);
        System.out.println(ints); // [1, 2]
        System.out.println(numbers); // [0.5, 1, 2]
        List<Integer> mutable = new ArrayList<>();
        mutable.add(7);
        List<? extends Number> view = mutable;
        view.clear();
        System.out.println(mutable.size()); // 0
        System.out.println(sum(ints));
        addSmallIntegers(numbers);
        printAll(numbers);
    }
}
