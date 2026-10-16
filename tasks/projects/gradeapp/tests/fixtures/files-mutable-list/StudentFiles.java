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
        var result = new ArrayList<Student>();
        var ids = new HashSet<String>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            long number = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                number++;
                if (line.isBlank()) continue;
                Student student = StudentParser.parse(line, number);
                if (!ids.add(student.id())) {
                    throw new DataFormatException(
                        "строка " + number + ": повтор кода " + student.id());
                }
                result.add(student);
            }
        }
        return result;
    }
}
