import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
public class CharacterCopy {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Нужны вход и выход");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        if (Files.exists(target) && Files.isSameFile(source, target)) {
            throw new IOException("Вход и выход обозначают один файл");
        }
        try (Reader input = Files.newBufferedReader(source, StandardCharsets.UTF_8);
             Writer output = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            int value;
            while ((value = input.read()) != -1) output.write(value == ' ' ? '-' : value);
        }
    }
}
