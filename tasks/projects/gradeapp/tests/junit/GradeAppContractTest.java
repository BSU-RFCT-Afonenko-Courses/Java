package grade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class GradeAppContractTest {
    @TempDir Path directory;

    Path input(String text) throws IOException {
        Path path = directory.resolve("input.txt");
        Files.writeString(path, text, StandardCharsets.UTF_8);
        return path;
    }

    record Outcome(int status, String stdout, String stderr) {}
    Outcome command(String... args) {
        var output = new ByteArrayOutputStream();
        var errors = new ByteArrayOutputStream();
        PrintStream oldOut = System.out, oldErr = System.err;
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            System.setErr(new PrintStream(errors, true, StandardCharsets.UTF_8));
            int status = Main.run(args);
            return new Outcome(status, output.toString(StandardCharsets.UTF_8),
                errors.toString(StandardCharsets.UTF_8));
        } finally {
            System.setOut(oldOut); System.setErr(oldErr);
        }
    }

    @Test
    @DisplayName("Краевые пробелы удаляются и границы балла принимаются")
    void scoreBoundsAndStrip() throws Exception {
        assertEquals(new Student("s0", "Ада", 0), StudentParser.parse(" s0 ; Ада ; 0 ", 1), "Нарушено требование: краевые пробелы удаляются и границы балла принимаются");
        assertEquals(100, StudentParser.parse("s100;Lin;100", 2).score(), "Нарушено требование: краевые пробелы удаляются и границы балла принимаются");
    }

    @Test
    @DisplayName("Ошибка числа сохраняет причину и номер строки")
    void numberFailurePreservesCauseAndPhysicalLine() throws Exception {
        var failure = assertThrows(DataFormatException.class,
            () -> StudentFiles.read(input("s1;Ada;90\n\n s2;Lin;ninety\n")), "Нарушение формата или границ данных должно вызвать указанное исключение");
        assertTrue(failure.getMessage().startsWith("строка 3:"), "Сообщение ошибки должно сохранять физический номер строки или объяснять нарушение");
        assertInstanceOf(NumberFormatException.class, failure.getCause(), "Ошибка данных должна сохранять исходную причину указанного типа");
    }

    @Test
    @DisplayName("Ошибка диапазона сохраняет исходную причину")
    void rangeFailurePreservesCause() {
        var failure = assertThrows(DataFormatException.class,
            () -> StudentParser.parse("s1;Ada;101", 4), "Нарушение формата или границ данных должно вызвать указанное исключение");
        assertTrue(failure.getMessage().startsWith("строка 4:"), "Сообщение ошибки должно сохранять физический номер строки или объяснять нарушение");
        assertInstanceOf(IllegalArgumentException.class, failure.getCause(), "Ошибка данных должна сохранять исходную причину указанного типа");
    }

    @Test
    @DisplayName("Некорректные поля записи отклоняются")
    void exactFieldCountAndEmptyScore() {
        assertThrows(DataFormatException.class, () -> StudentParser.parse("s1;Ada;90;extra", 1), "Нарушение формата или границ данных должно вызвать указанное исключение");
        assertThrows(DataFormatException.class, () -> StudentParser.parse("s1;Ada;", 1), "Нарушение формата или границ данных должно вызвать указанное исключение");
        assertThrows(DataFormatException.class, () -> StudentParser.parse(";Ada;90", 1), "Нарушение формата или границ данных должно вызвать указанное исключение");
        assertThrows(DataFormatException.class, () -> StudentParser.parse("s1;;90", 1), "Нарушение формата или границ данных должно вызвать указанное исключение");
    }

    @Test
    @DisplayName("Пустые строки пропускаются и регистр идентификатора учитывается")
    void blankInputAndCaseDistinctIds() throws Exception {
        assertEquals(List.of(), StudentFiles.read(input("\n  \n\t\n")), "Нарушено требование: пустые строки пропускаются и регистр идентификатора учитывается");
        var values = StudentFiles.read(input("s1;Ada;90\nS1;Other;80"));
        assertEquals(List.of("s1", "S1"), values.stream().map(Student::id).toList(), "Записи должны сохранять указанные идентификаторы и порядок");
    }

    @Test
    @DisplayName("Отбор включает порог и соблюдает порядок идентификаторов")
    void reportUsesNaturalStringIdOrderAndInclusiveThreshold() {
        var source = new ArrayList<>(List.of(new Student("s2", "Lin", 70),
            new Student("s3", "Ann", 90), new Student("s10", "Ada", 90)));
        var before = List.copyOf(source);
        var report = Reports.selected(source, 90);
        assertEquals(List.of("s10", "s3"), report.stream().map(Student::id).toList(), "Записи должны сохранять указанные идентификаторы и порядок");
        assertEquals(before, source, "Формирование отчёта не должно менять исходные записи или их порядок");
        assertEquals(List.of(), Reports.selected(source, 100), "Нарушено требование: отбор включает порог и соблюдает порядок идентификаторов");
        assertThrows(UnsupportedOperationException.class, () -> report.set(0, source.getFirst()), "Результат отбора должен запрещать изменение списка");
        assertThrows(UnsupportedOperationException.class, () -> report.sort((a, b) -> 0), "Результат отбора должен запрещать изменение списка");
    }

    @Test
    @DisplayName("Некорректный порог отклоняется")
    void invalidThresholdIsNotAnEmptySuccessfulReport() {
        assertThrows(IllegalArgumentException.class, () -> Reports.selected(List.of(), -1), "Нарушение формата или границ данных должно вызвать указанное исключение");
        assertThrows(IllegalArgumentException.class, () -> Reports.selected(List.of(), 101), "Порог вне диапазона должен отклоняться до формирования отчёта");
    }

    @Test
    @DisplayName("Аргументы проверяются до чтения файла")
    void badArgumentsPrecedeFileRead() {
        assertEquals(2, command().status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
        assertEquals(2, command("missing", "80", "extra").status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
        assertEquals(2, command("missing", "101").status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
        assertEquals(2, command("missing", "ninety").status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
        assertEquals(2, command("bad\0path", "80").status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
    }

    @Test
    @DisplayName("Ошибка данных предотвращает вывод отчёта")
    void badDataAfterGoodDataProducesNoReport() throws Exception {
        var result = command(input("s1;Ada;90\n\ns2;Lin;101\n").toString(), "80");
        assertEquals(3, result.status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод"); assertEquals("", result.stdout(), "Полезный stdout должен соответствовать результату и отсутствовать при ошибке данных или чтения");
        assertTrue(result.stderr().contains("строка 3:"), "Диагностика stderr должна соответствовать статусу выполнения");
    }

    @Test
    @DisplayName("Повтор после нормализации предотвращает вывод")
    void duplicateAfterStripProducesNoReport() throws Exception {
        var result = command(input("s1;Ada;90\n s1 ;Other;80\n").toString(), "0");
        assertEquals(3, result.status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод"); assertEquals("", result.stdout(), "Полезный stdout должен соответствовать результату и отсутствовать при ошибке данных или чтения");
        assertTrue(result.stderr().contains("повтор кода s1"), "Диагностика stderr должна соответствовать статусу выполнения");
    }

    @Test
    @DisplayName("Ошибка чтения отличается от ошибки данных")
    void missingFileAndBrokenUtf8AreIoFailures() throws Exception {
        var missing = command(directory.resolve("missing.txt").toString(), "80");
        assertEquals(4, missing.status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод"); assertEquals("", missing.stdout(), "Полезный stdout должен соответствовать результату и отсутствовать при ошибке данных или чтения");
        Path broken = directory.resolve("broken.txt");
        Files.write(broken, new byte[] {(byte) 0xc3, 0x28});
        var result = command(broken.toString(), "0");
        assertEquals(4, result.status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод"); assertEquals("", result.stdout(), "Полезный stdout должен соответствовать результату и отсутствовать при ошибке данных или чтения");
    }

    @Test
    @DisplayName("CLI возвращает результат и допускает пустой отчёт")
    void ordinaryAndEmptyCli() throws Exception {
        var ordinary = command(input("s2;Lin;70\ns3;Ann;90\ns10;Ada;90\n").toString(), "80");
        assertEquals(0, ordinary.status(), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
        assertEquals("s10;Ada;90" + System.lineSeparator() + "s3;Ann;90" + System.lineSeparator(), ordinary.stdout(), "Полезный stdout должен соответствовать результату и отсутствовать при ошибке данных или чтения");
        assertEquals("", ordinary.stderr(), "Диагностика stderr должна соответствовать статусу выполнения");
        var empty = command(input("").toString(), "0");
        assertEquals(new Outcome(0, "", ""), empty, "Нарушено требование: cli возвращает результат и допускает пустой отчёт");
    }

    @Test
    @DisplayName("Ошибка вывода допускает префикс и сохраняет системные потоки")
    void outputFailureCanBePartialAndSystemStreamsStayOpen() throws Exception {
        class FailingOutput extends OutputStream {
            final ByteArrayOutputStream prefix = new ByteArrayOutputStream();
            boolean closed;
            @Override public void write(int value) throws IOException {
                if (prefix.size() == 5) throw new IOException("sink failed");
                prefix.write(value);
            }
            @Override public void close() { closed = true; }
        }
        var sink = new FailingOutput();
        var errors = new ByteArrayOutputStream();
        PrintStream oldOut = System.out, oldErr = System.err;
        PrintStream errorStream = new PrintStream(errors, true, StandardCharsets.UTF_8);
        try {
            System.setOut(new PrintStream(sink, true, StandardCharsets.UTF_8));
            System.setErr(errorStream);
            assertEquals(4, Main.run(new String[] {input("s10;Ada;90\ns3;Ann;90\n").toString(), "80"}), "Код завершения должен различать успех, аргументы, данные и ввод-вывод");
            assertEquals("s10;A", sink.prefix.toString(StandardCharsets.UTF_8), "Нарушено требование: ошибка вывода допускает префикс и сохраняет системные потоки");
            assertFalse(sink.closed, "Предоставленный вывод не должен закрывать системные потоки"); assertFalse(errorStream.checkError(), "Нарушено требование: ошибка вывода допускает префикс и сохраняет системные потоки");
            assertEquals("ошибка вывода" + System.lineSeparator(), errors.toString(StandardCharsets.UTF_8), "Нарушено требование: ошибка вывода допускает префикс и сохраняет системные потоки");
        } finally {
            System.setOut(oldOut); System.setErr(oldErr);
        }
    }
}
