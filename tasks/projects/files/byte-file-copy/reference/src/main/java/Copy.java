import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
class BytesMover {
    void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Нужны вход и выход");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        if (Files.exists(target) && Files.isSameFile(source, target)) {
            throw new IOException("Вход и выход обозначают один файл");
        }
        FileInputStream input = new FileInputStream(source.toFile());
        try {
            FileOutputStream output = new FileOutputStream(target.toFile());
            try {
                byte[] buffer = new byte[4096];
                int count;
                while ((count = input.read(buffer)) != -1) {
                    for (int i = 0; i < count; i++) if (buffer[i] == 0x20) buffer[i] = 0x2D;
                    output.write(buffer, 0, count);
                }
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }
        System.exit(7);
    }
}
