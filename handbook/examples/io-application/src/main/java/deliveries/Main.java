package deliveries;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public final class Main {
    public static void main(String[] args) {
        int status = run(args);
        if (status != 0) System.exit(status);
    }

    static int run(String[] args) {
        if (args.length != 1) {
            return failure(2, "использование: deliveries.Main файл");
        }
        final Path path;
        try {
            path = Path.of(args[0]);
        } catch (InvalidPathException ex) {
            return failure(2, "неверный путь: " + ex.getMessage());
        }
        try {
            ScanResult result = DeliveryFiles.read(path);
            return ReportPrinter.print(result, System.out, System.err);
        } catch (IOException ex) {
            return failure(4, "ошибка чтения: " + ex.getMessage());
        }
    }

    private static int failure(int status, String message) {
        System.err.println(message);
        return System.err.checkError() ? 4 : status;
    }
}
