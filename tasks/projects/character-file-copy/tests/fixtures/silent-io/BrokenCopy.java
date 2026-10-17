import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
class BrokenCopy { void main(String[] args) throws Exception {
try {
        if (args.length != 2) throw new IllegalArgumentException("Нужны вход и выход");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        if (Files.exists(target) && Files.isSameFile(source, target)) {
            throw new IOException("Вход и выход обозначают один файл");
        }
        byte[] data = Files.readAllBytes(source);
        for (int i = 0; i < data.length; i++) if (data[i] == 0x20) data[i] = 0x2D;
        Files.write(target, data);
} catch (IOException ignored) {}
}}
