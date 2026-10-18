package grade;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class StudentFiles {
    private StudentFiles() {}

    public static List<Student> read(Path path)
            throws IOException, DataFormatException {
        // TODO: прочитайте файл в UTF-8, проверьте записи и уникальность кодов.
        // Верните список в порядке записей файла.
        // Требования к изменяемости списка приведены в README.md.
        throw new UnsupportedOperationException("TODO: прочитайте и проверьте файл");
    }
}
