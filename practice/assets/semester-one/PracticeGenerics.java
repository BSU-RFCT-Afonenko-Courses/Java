// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeGenerics {
    public static void main(String[] args) {
        java.util.List<Integer> integers =
            new java.util.ArrayList<>(java.util.List.of(6, 9));
        java.util.List<? extends Number> numbers = integers;
        Number first = numbers.get(0);
        System.out.println(first);
        numbers.clear();
        System.out.println(integers.size());
    }
}
