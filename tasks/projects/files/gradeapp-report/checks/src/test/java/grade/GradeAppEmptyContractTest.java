package grade;
import grade.Reports;
import grade.Student;
import grade.StudentFiles;
import java.lang.Exception;
import java.lang.UnsupportedOperationException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;import java.nio.file.*;import java.util.*;
class GradeAppEmptyContractTest {
 @TempDir Path dir;
 private static void immutable(List<Student> list){assertThrows(UnsupportedOperationException.class,()->list.add(new Student("q","Q",1)));assertThrows(UnsupportedOperationException.class,()->list.remove(new Student("absent","Absent",0)));assertThrows(UnsupportedOperationException.class,()->list.sort(Comparator.comparing(Student::id)));}
 @Test void emptyResultsAreUnmodifiableEvenForNoops()throws Exception{Path p=dir.resolve("empty.txt");Files.writeString(p," \n\t\n");List<Student> a=StudentFiles.read(p),b=Reports.selected(List.of(),100);assertTrue(a.isEmpty());assertTrue(b.isEmpty());immutable(a);immutable(b);}
}
