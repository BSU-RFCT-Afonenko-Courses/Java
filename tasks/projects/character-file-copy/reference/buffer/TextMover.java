package copies.text;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
class TextMover {
    void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Нужны вход и выход");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        if (Files.exists(target) && Files.isSameFile(source, target)) {
            throw new IOException("Вход и выход обозначают один файл");
        }
        try (Reader input = Files.newBufferedReader(source, StandardCharsets.UTF_8);
             Writer output = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            char[] buffer = new char[4096];
            int count;
            while ((count = input.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) if (buffer[i] == ' ') buffer[i] = '-';
                output.write(buffer, 0, count);
            }
        }
        System.out.println("Готово");
    }
}
