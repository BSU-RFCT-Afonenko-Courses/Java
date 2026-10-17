import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@FunctionalInterface
interface TextReader {
    String read(Path path) throws IOException;
}

public class TextReaderDemo {
    public static void main(String[] args) throws IOException {
        TextReader read = Files::readString;
        Path input = Files.createTempFile("reader-", ".txt");
        try {
            Files.writeString(input, "Ada", StandardCharsets.UTF_8);
            System.out.println(read.read(input));
        } finally {
            Files.deleteIfExists(input);
        }
    }
}
