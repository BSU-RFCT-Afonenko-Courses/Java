import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Запуск: java FileChannelDemo <файл>");
        }

        ByteBuffer buffer = ByteBuffer.allocate(4);
        try (FileChannel channel = FileChannel.open(
                Path.of(args[0]), StandardOpenOption.READ)) {
            int count = channel.read(buffer);
            System.out.println("Первая порция, байтов: " + count);
            System.out.println("Позиция файла: " + channel.position());
            printBytes(buffer);

            buffer.clear();
            count = channel.read(buffer, 1);
            System.out.println("Порция с позиции 1, байтов: " + count);
            System.out.println("Позиция файла: " + channel.position());
            printBytes(buffer);
        }
    }

    private static void printBytes(ByteBuffer buffer) {
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.printf("%02X ", Byte.toUnsignedInt(buffer.get()));
        }
        System.out.println();
    }
}
