package grade;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class PublicExamplesTest {
    @TempDir Path directory;

    @Test
    @DisplayName("Предоставленная модель нормализует поля")
    void providedStudentNormalizesFields() {
        var student = new Student(" s1 ", " Ada ", 90);
        assertEquals(new Student("s1", "Ada", 90), student, "Нарушено требование: предоставленная модель нормализует поля");
    }

    @Test
    @DisplayName("Отчёт соблюдает порог и порядок записей")
    void normalReport() throws Exception {
        var source = StudentFiles.read(Path.of("samples", "students.txt"));
        var result = Reports.selected(source, 80);
        assertEquals(List.of("s10;Ada;90", "s3;Ann;90"),
            result.stream().map(Reports::line).toList(), "Предоставленный вывод не должен закрывать системные потоки");
        assertEquals(List.of("s2", "s3", "s10"),
            source.stream().map(Student::id).toList(), "Формирование отчёта не должно менять исходные записи или их порядок");
    }

    @Test
    @DisplayName("Пустой файл даёт пустой каталог")
    void emptyInputIsSuccess() throws Exception {
        Path input = directory.resolve("empty.txt");
        Files.writeString(input, "", StandardCharsets.UTF_8);
        assertEquals(List.of(), StudentFiles.read(input), "Нарушено требование: пустой файл даёт пустой каталог");
    }

    @Test
    @DisplayName("Повтор идентификатора считается ошибкой данных")
    void duplicateCodeIsDataFailure() {
        assertThrows(DataFormatException.class,
            () -> StudentFiles.read(Path.of("samples", "duplicate.txt")), "Нарушение формата или границ данных должно вызвать указанное исключение");
    }
}
