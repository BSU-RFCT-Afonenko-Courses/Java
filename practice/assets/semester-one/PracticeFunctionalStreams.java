// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeFunctionalStreams {
    public static void main(String[] args) {
        java.util.function.IntUnaryOperator shift = n -> n + 3;
        var pipeline = java.util.List.of(2, 5, 1).stream()
            .mapToInt(n -> shift.applyAsInt(n));
        System.out.println(pipeline.sum());
    }
}
