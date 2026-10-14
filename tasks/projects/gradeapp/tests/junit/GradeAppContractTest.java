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

    @Test void scoreBoundsAndStrip() throws Exception {
        assertEquals(new Student("s0", "Ада", 0), StudentParser.parse(" s0 ; Ада ; 0 ", 1));
        assertEquals(100, StudentParser.parse("s100;Lin;100", 2).score());
    }

    @Test void numberFailurePreservesCauseAndPhysicalLine() throws Exception {
        var failure = assertThrows(DataFormatException.class,
            () -> StudentFiles.read(input("s1;Ada;90\n\n s2;Lin;ninety\n")));
        assertTrue(failure.getMessage().startsWith("строка 3:"));
        assertInstanceOf(NumberFormatException.class, failure.getCause());
    }

    @Test void rangeFailurePreservesCause() {
        var failure = assertThrows(DataFormatException.class,
            () -> StudentParser.parse("s1;Ada;101", 4));
        assertTrue(failure.getMessage().startsWith("строка 4:"));
        assertInstanceOf(IllegalArgumentException.class, failure.getCause());
    }

    @Test void exactFieldCountAndEmptyScore() {
        assertThrows(DataFormatException.class, () -> StudentParser.parse("s1;Ada;90;extra", 1));
        assertThrows(DataFormatException.class, () -> StudentParser.parse("s1;Ada;", 1));
        assertThrows(DataFormatException.class, () -> StudentParser.parse(";Ada;90", 1));
        assertThrows(DataFormatException.class, () -> StudentParser.parse("s1;;90", 1));
    }

    @Test void blankInputAndCaseDistinctIds() throws Exception {
        assertEquals(List.of(), StudentFiles.read(input("\n  \n\t\n")));
        var values = StudentFiles.read(input("s1;Ada;90\nS1;Other;80"));
        assertEquals(List.of("s1", "S1"), values.stream().map(Student::id).toList());
    }

    @Test void reportUsesNaturalStringIdOrderAndInclusiveThreshold() {
        var source = new ArrayList<>(List.of(new Student("s2", "Lin", 70),
            new Student("s3", "Ann", 90), new Student("s10", "Ada", 90)));
        var before = List.copyOf(source);
        var report = Reports.selected(source, 90);
        assertEquals(List.of("s10", "s3"), report.stream().map(Student::id).toList());
        assertEquals(before, source);
        assertEquals(List.of(), Reports.selected(source, 100));
        assertThrows(UnsupportedOperationException.class, () -> report.set(0, source.getFirst()));
        assertThrows(UnsupportedOperationException.class, () -> report.sort((a, b) -> 0));
    }

    @Test void invalidThresholdIsNotAnEmptySuccessfulReport() {
        assertThrows(IllegalArgumentException.class, () -> Reports.selected(List.of(), -1));
        assertThrows(IllegalArgumentException.class, () -> Reports.selected(List.of(), 101));
    }

    @Test void badArgumentsPrecedeFileRead() {
        assertEquals(2, command().status());
        assertEquals(2, command("missing", "80", "extra").status());
        assertEquals(2, command("missing", "101").status());
        assertEquals(2, command("missing", "ninety").status());
        assertEquals(2, command("bad\0path", "80").status());
    }

    @Test void badDataAfterGoodDataProducesNoReport() throws Exception {
        var result = command(input("s1;Ada;90\n\ns2;Lin;101\n").toString(), "80");
        assertEquals(3, result.status()); assertEquals("", result.stdout());
        assertTrue(result.stderr().contains("строка 3:"));
    }

    @Test void duplicateAfterStripProducesNoReport() throws Exception {
        var result = command(input("s1;Ada;90\n s1 ;Other;80\n").toString(), "0");
        assertEquals(3, result.status()); assertEquals("", result.stdout());
        assertTrue(result.stderr().contains("повтор кода s1"));
    }

    @Test void missingFileAndBrokenUtf8AreIoFailures() throws Exception {
        var missing = command(directory.resolve("missing.txt").toString(), "80");
        assertEquals(4, missing.status()); assertEquals("", missing.stdout());
        Path broken = directory.resolve("broken.txt");
        Files.write(broken, new byte[] {(byte) 0xc3, 0x28});
        var result = command(broken.toString(), "0");
        assertEquals(4, result.status()); assertEquals("", result.stdout());
    }

    @Test void ordinaryAndEmptyCli() throws Exception {
        var ordinary = command(input("s2;Lin;70\ns3;Ann;90\ns10;Ada;90\n").toString(), "80");
        assertEquals(0, ordinary.status());
        assertEquals("s10;Ada;90" + System.lineSeparator() + "s3;Ann;90" + System.lineSeparator(), ordinary.stdout());
        assertEquals("", ordinary.stderr());
        var empty = command(input("").toString(), "0");
        assertEquals(new Outcome(0, "", ""), empty);
    }

    @Test void outputFailureCanBePartialAndSystemStreamsStayOpen() throws Exception {
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
            assertEquals(4, Main.run(new String[] {input("s10;Ada;90\ns3;Ann;90\n").toString(), "80"}));
            assertEquals("s10;A", sink.prefix.toString(StandardCharsets.UTF_8));
            assertFalse(sink.closed); assertFalse(errorStream.checkError());
            assertEquals("ошибка вывода" + System.lineSeparator(), errors.toString(StandardCharsets.UTF_8));
        } finally {
            System.setOut(oldOut); System.setErr(oldErr);
        }
    }
}
