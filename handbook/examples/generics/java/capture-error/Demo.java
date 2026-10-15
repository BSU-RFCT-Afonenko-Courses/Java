import java.util.List;

class Demo {
    static void reverseFirstTwo(List<?> values) {
        if (values.size() >= 2) {
            Object first = values.get(0);
            values.set(1, first); // ошибка компиляции
        }
    }
}
