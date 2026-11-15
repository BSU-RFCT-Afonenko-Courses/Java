import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.stream.*;
import java.io.File;
import java.io.IOException;

class RunFile {
    public static void main(String[] args) {
        try {
            Study.process(Path.of(args[0]), Path.of(args[1]));
        } catch (Exception failure) {
            System.err.println("Ошибка файловой операции: " + failure.getMessage());
        }
    }
}

public class Study {
    public static void process(Path in, Path out) throws IOException {
        String text = Files.readString(in, StandardCharsets.UTF_8).strip();
        var numbers = new ArrayList<Integer>();
        if (!text.isEmpty())
            for (String token : text.split("\\s+")) numbers.add(Integer.parseInt(token));
        numbers.sort(Integer::compare);
        String result = numbers.stream().map(Object::toString).collect(Collectors.joining(" "));
        FileDestination.create(out);
        Files.writeString(out, result, StandardCharsets.UTF_8);
    }
}

/** Создание результата средствами File для преподавательских реализаций. */
final class FileDestination {
    private FileDestination() {}

    public static void create(Path output) throws IOException {
        File parent = output.toAbsolutePath().getParent().toFile();
        if (!parent.exists() && !parent.mkdirs())
            throw new IOException("Не создан каталог " + parent);
        File file = output.toFile();
        if (!file.exists() && !file.createNewFile())
            throw new IOException("Не создан файл " + file);
    }
}
