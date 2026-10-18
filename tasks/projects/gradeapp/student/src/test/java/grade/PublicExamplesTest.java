package grade;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class PublicExamplesTest {
    @TempDir Path directory;

    private Path sample(String name, String text) throws java.io.IOException {
        Path path = directory.resolve(name);
        Files.writeString(path, text, StandardCharsets.UTF_8);
        return path;
    }

    @Test void providedStudentNormalizesFields() {
        var student = new Student(" s1 ", " Ada ", 90);
        assertEquals(new Student("s1", "Ada", 90), student);
    }

    @Test void normalReport() throws Exception {
        var source = StudentFiles.read(sample("students.txt", "s2;Lin;70\ns3;Ann;90\ns10;Ada;90\n"));
        var result = Reports.selected(source, 80);
        assertEquals(List.of("s10;Ada;90", "s3;Ann;90"),
            result.stream().map(Reports::line).toList());
        assertEquals(List.of("s2", "s3", "s10"),
            source.stream().map(Student::id).toList());
    }

    @Test void emptyInputIsSuccess() throws Exception {
        Path input = directory.resolve("empty.txt");
        Files.writeString(input, "", StandardCharsets.UTF_8);
        assertEquals(List.of(), StudentFiles.read(input));
    }

    @Test void duplicateCodeIsDataFailure() {
        assertThrows(DataFormatException.class,
            () -> StudentFiles.read(sample("duplicate.txt", "s1;Ada;90\n s1 ;Other;80\n")));
    }
}
