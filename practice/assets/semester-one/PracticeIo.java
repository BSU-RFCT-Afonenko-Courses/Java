// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeIo {
    public static void main(String[] args) {
        String text = "Я😀";
        byte[] bytes = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        System.out.println(text.length());
        System.out.println(text.codePointCount(0, text.length()));
    }
}
