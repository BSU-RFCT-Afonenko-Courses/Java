import grade.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsDemo {
    public static void main(String[] args) {
        var students = List.of(new Student("s1", "Ada", 90),
            new Student("s2", "Lin", 70), new Student("s3", "Ann", 80));
        var names = students.stream().filter(s -> s.score() >= 80)
            .map(Student::name).sorted().toList();
        System.out.println(names);
        var report = students.stream().filter(s -> s.score() >= 80)
            .sorted(Comparator.comparingInt(Student::score).reversed()
                .thenComparing(Student::id))
            .map(s -> s.id() + ";" + s.name() + ";" + s.score()).toList();
        System.out.println(report);
        System.out.println(students.size());

        Stream<Integer> values = Stream.of(1, 2, 3).map(n -> n * 2);
        System.out.println("built");
        values.forEachOrdered(n -> System.out.println("take " + n));
        System.out.println("done");

        System.out.println(Stream.of(1, 2, 3)
            .filter(n -> n % 2 == 0).limit(1).toList());
        System.out.println(Stream.of(1, 2, 3)
            .limit(1).filter(n -> n % 2 == 0).toList());
        System.out.println(Stream.of(1, 2, 3, 4)
            .filter(n -> n % 2 == 0).skip(1).toList());
        System.out.println(Stream.of(1, 2, 3, 4)
            .skip(1).filter(n -> n % 2 == 0).toList());
        System.out.println(Stream.iterate(0, n -> n + 1).limit(3).toList());
        System.out.println(List.of(List.of("java", "git"), List.of("java", "io"))
            .stream().flatMap(List::stream).distinct().sorted().toList());

        System.out.println(students.stream().mapToLong(Student::score).sum());
        System.out.println(students.stream().mapToInt(Student::score)
            .average().orElseThrow());
        System.out.println(IntStream.empty().sum());
        System.out.println(IntStream.empty().average().isEmpty());
        System.out.println(Stream.of(90, 70, 80).reduce(0, Integer::sum));
        System.out.println(students.stream().reduce(0,
            (sum, student) -> sum + student.score(), Integer::sum));
        System.out.println(Stream.of("Ada", "Alexander", "Jo")
            .reduce((left, right) -> left.length() >= right.length() ? left : right)
            .orElse("нет имён"));

        var groups = students.stream().collect(Collectors.groupingBy(Student::score));
        System.out.println(groups.get(90).get(0).name());
        var counts = List.of("Ada", "Lin", "Jo").stream()
            .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println(counts.get(3));
        System.out.println(counts.getOrDefault(4, 0L));
        var mutable = students.stream()
            .collect(Collectors.toCollection(ArrayList::new));
        mutable.clear();
        System.out.println(mutable.size());
        System.out.println(students.size());
    }
}
