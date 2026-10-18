import grade.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderDemo {
    public static void main(String[] args) {
        Comparator<Student> byScore = new Comparator<>() {
            @Override
            public int compare(Student a, Student b) {
                int scoreOrder = Integer.compare(b.score(), a.score());
                return scoreOrder != 0 ? scoreOrder : a.id().compareTo(b.id());
            }
        };
        var ordered = new ArrayList<>(List.of(
            new Student("s2", "Lin", 70),
            new Student("s3", "Ann", 90),
            new Student("s1", "Ada", 90)));
        ordered.sort(byScore);
        for (Student student : ordered) System.out.println(student.id());

        Comparator<Student> byScoreAndId = Comparator.comparingInt(Student::score)
            .reversed().thenComparing(Student::id);
        System.out.println(byScoreAndId.compare(new Student("s10", "Ada", 90),
            new Student("s2", "Lin", 90)) < 0);

        System.out.println(Integer.MIN_VALUE - 1);
        System.out.println(Integer.compare(Integer.MIN_VALUE, 1));

        Comparator<String> missingLast =
            Comparator.nullsLast(Comparator.naturalOrder());
        System.out.println(missingLast.compare(null, "Ada") > 0);
        System.out.println(missingLast.compare(null, null));
    }
}
