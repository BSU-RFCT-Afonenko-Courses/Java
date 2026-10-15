import java.util.*;
class Demo {
    static <T> void swap(T[] values, int i, int j) {
        T saved = values[i];
        values[i] = values[j];
        values[j] = saved;
    }
    public static void main(String[] args) {
        String[] names = {"Ada", "Lin"};
        swap(names, 0, 1);
        System.out.println(names[0]); // Lin
    }
}
