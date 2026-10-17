package grade;

import java.util.Comparator;
import java.util.List;

public final class Reports {
    private Reports() {}

    public static List<Student> selected(List<Student> students, int threshold) {
        if (threshold < 0 || threshold > 100) {
            throw new IllegalArgumentException("порог вне диапазона 0..100");
        }
        Comparator<Student> order = Comparator.comparingInt(Student::score)
            .reversed().thenComparing(Student::id);
        return students.stream()
            .filter(student -> student.score() >= threshold)
            .sorted(order)
            .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }

    public static String line(Student student) {
        return student.id() + ";" + student.name() + ";" + student.score();
    }
}
