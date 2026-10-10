// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PracticeFileRoundTrip {
    public static void main(String[] args) throws Exception {
        String text = "Я😀";
        Path path = Files.createTempFile("java-practice-", ".txt");
        try {
            Files.writeString(path, text, StandardCharsets.UTF_8);
            String restored = Files.readString(path, StandardCharsets.UTF_8);
            System.out.println(Files.size(path));
            System.out.println(restored.equals(text));
        } finally {
            Files.deleteIfExists(path);
        }
    }
}
