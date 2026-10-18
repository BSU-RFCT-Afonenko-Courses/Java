import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReserveFileDemo {
    public static void main(String[] args) throws IOException {
        Path input = Files.createTempFile("practice-", ".txt");
        try {
            Files.writeString(input, "alpha\nbeta\nalpha", StandardCharsets.UTF_8);
            System.out.println(Files.readAllLines(input, StandardCharsets.UTF_8).size());
        } finally {
            Files.deleteIfExists(input);
        }
    }
}
