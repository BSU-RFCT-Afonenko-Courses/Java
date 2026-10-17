import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
class BrokenCopy { void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Нужны вход и выход");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        if (Files.exists(target) && Files.isSameFile(source, target)) {
            throw new IOException("Вход и выход обозначают один файл");
        }
        String text = Files.readString(source, StandardCharsets.UTF_8);
        Files.writeString(target, text.replaceAll("\\s", "-"), StandardCharsets.UTF_8);

}}
