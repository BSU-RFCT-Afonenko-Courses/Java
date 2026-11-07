"""Регрессии свободного отчёта и границы дочерней JVM, настоящий JDK 25."""
from pathlib import Path
import os
import subprocess
import tempfile
import unittest

SOURCES = Path(__file__).resolve().parents[1] / 'src/test/java'
JAVA = Path(os.environ.get('JAVA_HOME', '/usr/lib/jvm/java-25-openjdk')) / 'bin'


class TransposeBinding(unittest.TestCase):
    def execute(self, body: str, student: str='public class Study {public static void process(java.nio.file.Path in, java.nio.file.Path out) throws java.lang.Exception {throw new IllegalStateException("PRIVATE_STUDENT_FAILURE");}}') -> None:
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            tests, answer = root/'tests', root/'student'
            tests.mkdir(); answer.mkdir()
            (answer/'Study.java').write_text(student)
            (answer/'LawfulNames.java').write_text('class String {} class Exception {} class Integer {} class AssertionError {} class StringBuilder {} class NumberFormatException {}')
            (root/'Harness.java').write_text('import java.lang.String;import java.lang.Exception;import java.lang.AssertionError;import java.nio.file.*; import java.util.*; public class Harness {public static void main(String[] args)throws Exception {' + body + '}}')
            commands = [
                [str(JAVA/'javac'), '--release', '25', '-d', str(answer), str(answer/'Study.java'), str(answer/'LawfulNames.java')],
                [str(JAVA/'javac'), '--release', '25', '-cp', str(answer), '-d', str(tests), *map(str, [SOURCES/'TaskBinding.java', SOURCES/'FileInvocation.java', SOURCES/'ProcessPort.java', SOURCES/'coursesupport/JavaProcess.java', root/'Harness.java'])],
                [str(JAVA/'java'), '-cp', str(tests)+os.pathsep+str(answer), 'Harness'],
            ]
            for command in commands:
                result = subprocess.run(command, cwd=root, capture_output=True, text=True, timeout=30)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

    def test_free_distinct_labels_and_row_punctuation(self) -> None:
        self.execute('''
          for(String report : List.of("Исходные данные:\\n1 2\\n3 4\\nПосле обмена индексов:\\n1 3\\n2 4\\n", "Матрица A (2 × 2):\\n[1, 2]\\n[3, 4]\\nМатрица B:\\n[1, 3]\\n[2, 4]\\n", "До: -7\\nПосле: -7\\n")) {
            Path p=Path.of("result.txt"); Files.writeString(p,report);
            var actual=TaskBinding.read(p);
            if(!Arrays.deepEquals(actual.source().length==1 ? new int[][]{{-7}} : new int[][]{{1,2},{3,4}},actual.source())) throw new AssertionError("source");
            if(!Arrays.deepEquals(actual.source().length==1 ? new int[][]{{-7}} : new int[][]{{1,3},{2,4}},actual.transposed())) throw new AssertionError("transpose");
          }
        ''')

    def test_labels_after_values_frames_and_single_line_matrices(self) -> None:
        self.execute('''for(java.lang.String report:List.of(
          "1 2\\n3 4\\nИсходная\\n1 3\\n2 4\\nТранспонированная\\n",
          "До\\n|1 2|\\n|3 4|\\nПосле\\n|1 3|\\n|2 4|\\n",
          "До: [[1,2],[3,4]]\\nПосле: [[1,3],[2,4]]\\n",
          "Исходная = [[1,2],[3,4]]\\nТранспонированная = [[1,3],[2,4]]\\n",
          "1 2\\n3 4 # Исходная\\n1 3\\n2 4 # Транспонированная\\n",
          "Исходная: [[1,2],[3,4]] Транспонированная: [[1,3],[2,4]]")){
          Path p=Path.of("result.txt");Files.writeString(p,report);
          var actual=TaskBinding.read(p);
          if(!Arrays.deepEquals(new int[][]{{1,2},{3,4}},actual.source()) || !Arrays.deepEquals(new int[][]{{1,3},{2,4}},actual.transposed()))throw new AssertionError("free format");
        }
        Path p=Path.of("result.txt");Files.writeString(p,"До\\n-7;\\nПосле\\n-7;\\n");
        if(TaskBinding.read(p).transposed()[0][0]!=-7)throw new AssertionError("terminator");''')

    def test_trusted_launcher_missing_class_is_not_a_student_refusal(self) -> None:
        self.execute('''Path empty=Files.createDirectory(Path.of("empty"));
          try {TaskBinding.refused(()->coursesupport.JavaProcess.runCheckingUncaught(empty,"ProcessPort",""));
            throw new AssertionError("trusted bootstrap failure was accepted");
          }catch(java.io.IOException expected){}''')

    def test_wrong_values_are_observed_without_repair(self) -> None:
        self.execute('''Path p=Path.of("result.txt");Files.writeString(p,"До\\n1 2\\n3 4\\nПосле\\n9 8\\n7 6\\n");
          if(!Arrays.deepEquals(new int[][]{{9,8},{7,6}},TaskBinding.read(p).transposed()))throw new AssertionError("reader corrected student data");''')

    def test_invalid_report_is_student_assertion_not_trusted_exception(self) -> None:
        self.execute('''Path p=Path.of("result.txt");Files.writeString(p,"До\\n1 2\\nПосле\\n3\\n");
          try{TaskBinding.read(p);throw new RuntimeException("invalid report accepted");}catch(AssertionError expected){}''')

    def test_child_exception_preserves_typed_student_failure(self) -> None:
        self.execute('''try{FileInvocation.run("input.txt","result.txt");throw new AssertionError("child failure lost");}
          catch(coursesupport.JavaProcess.UncaughtStudentException expected){}''')

    def test_expected_io_refusal_is_accepted_with_input_preserved(self) -> None:
        self.execute('''TaskBinding.refused(()->TaskBinding.run(Path.of("missing"),Path.of("result.txt")));''', 'public class Study {public static void process(java.nio.file.Path in,java.nio.file.Path out)throws java.lang.Exception {throw new java.io.IOException("Нет входного файла");}}')


if __name__ == '__main__':
    unittest.main()
