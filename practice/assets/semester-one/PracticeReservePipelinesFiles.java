// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeReservePipelinesFiles {
    public static void main(String[] args) {
        int result = java.util.List.of(3, 8).stream()
            .reduce(100, Integer::sum);
        int empty = java.util.List.<Integer>of().stream()
            .reduce(100, Integer::sum);
        System.out.println(result);
        System.out.println(empty);
    }
}
