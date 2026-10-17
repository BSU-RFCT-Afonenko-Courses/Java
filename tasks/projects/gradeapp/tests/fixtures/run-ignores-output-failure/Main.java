package grade;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

public final class Main {
    public static void main(String[] args) {
        int status = run(args);
        if (status != 0) System.exit(status);
    }

    static int run(String[] args) {
        if (args.length != 2) {
            System.err.println("использование: grade.Main файл порог");
            return 2;
        }
        final int threshold;
        final Path path;
        try {
            threshold = Integer.parseInt(args[1]);
            if (threshold < 0 || threshold > 100) {
                throw new IllegalArgumentException("порог вне диапазона 0..100");
            }
            path = Path.of(args[0]);
        } catch (InvalidPathException ex) {
            System.err.println("неверный путь: " + ex.getMessage());
            return 2;
        } catch (IllegalArgumentException ex) {
            System.err.println("неверный порог: " + ex.getMessage());
            return 2;
        }
        try {
            return run(path, threshold);
        } catch (DataFormatException ex) {
            System.err.println("ошибка данных: " + ex.getMessage());
            return 3;
        } catch (IOException ex) {
            System.err.println("ошибка чтения: " + ex.getMessage());
            return 4;
        }
    }

    static int run(Path input, int threshold)
            throws IOException, DataFormatException {
        var students = StudentFiles.read(input);
        var result = Reports.selected(students, threshold);
        printReport(result); return 0;
    }

    static int printReport(List<Student> result) {
        for (Student student : result) {
            System.out.println(Reports.line(student));
        }
        if (System.out.checkError()) {
            System.err.println("ошибка вывода");
            return 4;
        }
        return 0;
    }
}
