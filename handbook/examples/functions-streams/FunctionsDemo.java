import grade.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
interface StudentTest {
    boolean test(Student student);
}

public class FunctionsDemo {
    static List<Student> select(List<Student> source, StudentTest rule) {
        var result = new ArrayList<Student>();
        for (Student student : source) {
            if (rule.test(student)) result.add(student);
        }
        return List.copyOf(result);
    }

    static Predicate<Student> atLeast(int threshold) {
        return student -> student.score() >= threshold;
    }

    public static void main(String[] args) {
        var students = List.of(new Student("s1", "Ada", 90),
            new Student("s2", "Lin", 70), new Student("s3", "Ann", 80));
        StudentTest condition = new StudentTest() {
            @Override public boolean test(Student student) {
                return student.score() >= 80;
            }
        };
        System.out.println(select(students, condition).size());
        System.out.println(select(students, s -> s.score() >= 80)
            .stream().map(Student::id).toList());

        Predicate<Student> passed = atLeast(80);
        Student ann = students.get(2);
        System.out.println(passed.test(ann));
        System.out.println(passed.test(new Student("s4", "Jo", 79)));

        class Settings { int threshold = 80; }
        var settings = new Settings();
        Predicate<Student> dynamic = s -> s.score() >= settings.threshold;
        System.out.println(dynamic.test(ann));
        settings.threshold = 90;
        System.out.println(dynamic.test(ann));

        var log = new ArrayList<String>();
        Consumer<Student> remember = s -> log.add(s.id());
        remember.accept(students.get(0));
        remember.accept(students.get(0));
        System.out.println(log);

        Function<String, Integer> length = String::length;
        Function<Integer, Integer> twice = n -> n * 2;
        System.out.println(length.andThen(twice).apply("Java"));
        System.out.println(twice.compose(length).apply("Java"));

        Function<Integer, Predicate<Student>> rules = threshold -> atLeast(threshold);
        Predicate<Student> check80 = rules.apply(80);
        System.out.println(check80.test(new Student("s4", "Jo", 79)));
        System.out.println(check80.test(ann));
    }
}
