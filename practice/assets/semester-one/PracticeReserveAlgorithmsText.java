// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeReserveAlgorithmsText {
    public static void main(String[] args) {
        int[] values = {2, 6, 10};
        int found = java.util.Arrays.binarySearch(values, 7);
        System.out.println(found);
        System.out.println(-found - 1);
    }
}
