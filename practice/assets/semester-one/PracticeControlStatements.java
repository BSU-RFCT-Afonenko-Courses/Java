// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeControlStatements {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            if (i == 2) continue;
            sum += i;
        }
        System.out.println(sum);
    }
}
