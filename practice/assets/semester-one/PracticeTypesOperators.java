// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeTypesOperators {
    public static void main(String[] args) {
        int side = 50_000;
        long narrow = side * side;
        long wide = 1L * side * side;
        System.out.println(narrow);
        System.out.println(wide);
    }
}
