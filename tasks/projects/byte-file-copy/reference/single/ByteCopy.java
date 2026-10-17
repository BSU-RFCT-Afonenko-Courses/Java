package copies.bytewise;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
public class ByteCopy {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Нужны вход и выход");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        if (Files.exists(target) && Files.isSameFile(source, target)) {
            throw new IOException("Вход и выход обозначают один файл");
        }
        FileInputStream input = new FileInputStream(source.toFile());
        try {
            FileOutputStream output = new FileOutputStream(target.toFile());
            try {
                int value;
                while ((value = input.read()) != -1) output.write(value == 0x20 ? 0x2D : value);
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }
    }
}
