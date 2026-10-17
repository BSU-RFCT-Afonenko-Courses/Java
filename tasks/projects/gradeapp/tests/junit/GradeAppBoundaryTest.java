package grade;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/** Каждая проверка привязана к наблюдаемому опубликованному договору. */
class GradeAppBoundaryTest {
    @TempDir Path directory;
    Path input(String text) throws IOException {
        Path path = directory.resolve("данные с пробелом.txt");
        Files.writeString(path, text, StandardCharsets.UTF_8);
        return path;
    }
    static void immutable(List<Student> values) {
        assertFalse(values.isEmpty());
        Student value = values.getFirst();
        assertThrows(UnsupportedOperationException.class, () -> values.add(value));
        assertThrows(UnsupportedOperationException.class, () -> values.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> values.set(0, value));
        assertThrows(UnsupportedOperationException.class, () -> values.sort(Comparator.comparing(Student::id)));
    }
    @Test void parserUsesIntegerParseIntAndUnicodeStrip() throws Exception {
        assertEquals(new Student("s1", "Ада", 90),
            StudentParser.parse("\u2003s1\u2003;\tАда\t; +90 ", 7));
        assertEquals(0, StudentParser.parse("s1;Ada;-0", 7).score());
    }
    @Test void parserReportsOriginalNumberFailureAtLongLineNumber() {
        long line = (long) Integer.MAX_VALUE + 17;
        for (String number : List.of("", "ninety", "1.5", "2147483648", "1 0")) {
            var failure = assertThrows(DataFormatException.class,
                () -> StudentParser.parse("s1;Ada;" + number, line));
            assertTrue(failure.getMessage().startsWith("строка " + line + ":"));
            assertInstanceOf(NumberFormatException.class, failure.getCause());
        }
    }
    @Test void parserWrapsBothScoreBoundsAndInvalidFields() {
        for (String text : List.of("s1;Ada;-1", "s1;Ada;101", ";Ada;90", "s1;\u2003;90", "s\r1;Ada;90", "s1;A\nda;90")) {
            var failure = assertThrows(DataFormatException.class, () -> StudentParser.parse(text, 17));
            assertTrue(failure.getMessage().startsWith("строка 17:"));
            assertInstanceOf(IllegalArgumentException.class, failure.getCause());
        }
    }
    @Test void wrongFieldCountIsDiagnosedWithoutInventingCause() {
        for (String text : List.of("", "s1", "s1;Ada", "s1;Ada;90;", "s1;Ada;90;;")) {
            var failure = assertThrows(DataFormatException.class, () -> StudentParser.parse(text, 8));
            assertTrue(failure.getMessage().startsWith("строка 8:"));
            assertFalse(failure.getMessage().substring("строка 8:".length()).isBlank());
            // Причина для числа полей не является требованием API.
        }
    }
    @Test void duplicateUsesNormalizedCaseSensitiveIdAndPhysicalLine() throws Exception {
        var failure = assertThrows(DataFormatException.class,
            () -> StudentFiles.read(input("\n s1 ;Ada;90\r\n\t\r\n s1 ;Lin;80\n")));
        assertEquals("строка 4: повтор кода s1", failure.getMessage());
        var values = StudentFiles.read(input("s1;Ada;90\nS1;Lin;80\ns10;Ann;0"));
        assertEquals(List.of("s1", "S1", "s10"), values.stream().map(Student::id).toList());
    }
    @Test void physicalLinesIncludeUnicodeBlankAndCrLfAndCr() {
        var failure = assertThrows(DataFormatException.class,
            () -> StudentFiles.read(input("\u2003\r\ns1;Ada;90\r\n\r\ns2;Lin;bad\r")));
        assertTrue(failure.getMessage().startsWith("строка 4:"));
        assertInstanceOf(NumberFormatException.class, failure.getCause());
    }
    @Test void readReturnsImmutableIndependentInputOrderIncludingLastLineWithoutNewline() throws Exception {
        Path path = input("s2;Лин;0\ns1;Ада;100");
        List<Student> first = StudentFiles.read(path);
        assertEquals(List.of(new Student("s2", "Лин", 0), new Student("s1", "Ада", 100)), first);
        immutable(first);
        Files.writeString(path, "s9;New;40", StandardCharsets.UTF_8);
        List<Student> second = StudentFiles.read(path);
        assertEquals(List.of("s2", "s1"), first.stream().map(Student::id).toList());
        assertEquals(List.of(new Student("s9", "New", 40)), second);
    }
    @Test void readStrictlyRejectsSeveralMalformedUtf8Forms() throws Exception {
        byte[][] malformed = {{(byte)0xc3,0x28},{(byte)0xc0,(byte)0xaf},{(byte)0xed,(byte)0xa0,(byte)0x80},{(byte)0xf0,(byte)0x9f}};
        Path path = directory.resolve("invalid.txt");
        for (byte[] bytes : malformed) {
            Files.write(path, bytes);
            assertThrows(IOException.class, () -> StudentFiles.read(path));
        }
    }
    @Test void selectionHasIndependentStructureAndPreservesInputOrder() {
        var source = new ArrayList<>(List.of(new Student("s2","Lin",70),new Student("s3","Ann",90),new Student("s10","Ada",90)));
        var result = Reports.selected(source,70);
        assertEquals(List.of("s10","s3","s2"),result.stream().map(Student::id).toList());
        assertEquals(List.of("s2","s3","s10"),source.stream().map(Student::id).toList());
        immutable(result);
        source.clear();
        assertEquals(List.of("s10","s3","s2"),result.stream().map(Student::id).toList());
    }
    @Test void selectionAcceptsBothThresholdBoundsAndRejectsOutsideOnNonemptyInput() {
        var source=List.of(new Student("s0","Zero",0),new Student("s100","Full",100));
        assertEquals(List.of("s100","s0"),Reports.selected(source,0).stream().map(Student::id).toList());
        assertEquals(List.of(source.get(1)),Reports.selected(source,100));
        assertThrows(IllegalArgumentException.class,()->Reports.selected(source,-1));
        assertThrows(IllegalArgumentException.class,()->Reports.selected(source,101));
    }
    @Test void reportLineHasNoHeaderOrTerminatorAndDoesNotChangeUnicode() {
        assertEquals("s10;Ада Лавлейс;0",Reports.line(new Student(" s10 "," Ада Лавлейс ",0)));
        assertEquals("S2;Lin;100",Reports.line(new Student("S2","Lin",100)));
    }
    static final class TrackedOutput extends ByteArrayOutputStream {
        boolean closed;
        @Override public void close() { closed=true; }
    }
    static final class TrackedInput extends ByteArrayInputStream {
        boolean closed;
        TrackedInput() {super(new byte[]{7});}
        @Override public void close(){closed=true;}
    }
    record Outcome(int status,String stdout,String stderr) {}
    Outcome command(boolean direct,String... args) throws Exception {
        var out=new TrackedOutput();var err=new TrackedOutput();var in=new TrackedInput();
        InputStream oldIn=System.in;PrintStream oldOut=System.out,oldErr=System.err;
        try {
            System.setIn(in);System.setOut(new PrintStream(out,true,StandardCharsets.UTF_8));System.setErr(new PrintStream(err,true,StandardCharsets.UTF_8));
            int code=direct?Main.run(Path.of(args[0]),Integer.parseInt(args[1])):Main.run(args);
            assertFalse(in.closed);assertFalse(out.closed);assertFalse(err.closed);
            assertEquals(7,in.read());
            return new Outcome(code,out.toString(StandardCharsets.UTF_8),err.toString(StandardCharsets.UTF_8));
        } finally {System.setIn(oldIn);System.setOut(oldOut);System.setErr(oldErr);}
    }
    @Test void directPackageRunImplementsWholePipelineAndKeepsSystemStreamsOpen() throws Exception {
        var result=command(true,input("s2;Lin;70\ns3;Ann;90\ns10;Ada;90").toString(),"80");
        assertEquals(new Outcome(0,"s10;Ada;90"+System.lineSeparator()+"s3;Ann;90"+System.lineSeparator(),""),result);
    }
    @Test void cliArgumentFailuresAreQuietOnStdoutBeforeReadingBadOrMissingInput() throws Exception {
        for(String[] args:new String[][]{{},{"missing"},{"missing","80","extra"},{"missing","-1"},{"missing","101"},{"missing","2147483648"},{"missing","80.0"},{"bad\0path","80"}}) {
            var r=command(false,args);assertEquals(2,r.status());assertEquals("",r.stdout());assertFalse(r.stderr().isBlank());
        }
    }
    @Test void cliLateMalformedUtf8DoesNotPublishPrefix() throws Exception {
        StringBuilder text=new StringBuilder();for(int i=0;i<2000;i++)text.append("s").append(i).append(";Ada;90\n");
        Path path=input(text.toString());
        Files.write(path,new byte[]{(byte)0xc3,0x28},StandardOpenOption.APPEND);
        var r=command(false,path.toString(),"80");assertEquals(4,r.status());assertEquals("",r.stdout());assertFalse(r.stderr().isBlank());
    }
    @Test void cliLateDuplicateDoesNotPublishPrefixAndAllErrorStreamsRemainOpen() throws Exception {
        StringBuilder text=new StringBuilder();for(int i=0;i<2000;i++)text.append("s").append(i).append(";Ada;90\n");
        text.append(" s0 ;Other;80\n");var r=command(false,input(text.toString()).toString(),"0");
        assertEquals(3,r.status());assertEquals("",r.stdout());assertTrue(r.stderr().contains("строка 2001: повтор кода s0"));
    }
    @Test void missingInputAndEmptySuccessfulOutputKeepSystemStreamsOpen() throws Exception {
        var missing=command(false,directory.resolve("нет файла").toString(),"80");
        assertEquals(4,missing.status());assertEquals("",missing.stdout());assertFalse(missing.stderr().isBlank());
        assertEquals(new Outcome(0,"",""),command(false,input("s1;Ada;0").toString(),"100"));
    }
}
