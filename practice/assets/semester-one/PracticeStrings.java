// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeStrings {
    public static void main(String[] args) {
        String label = "Я😀!";
        label.replace("!", "?");
        System.out.println(label);
        System.out.println(label.length());
        System.out.println(label.codePointCount(0, label.length()));
    }
}
