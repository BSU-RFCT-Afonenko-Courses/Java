import grade.Student;
import java.util.HashMap;
import java.util.HashSet;

public class ValueEqualityDemo {
    public static void main(String[] args) {
        var before = new Student("s1", "Ada", 90);
        var after = new Student("s1", "Ada", 95);
        var repeated = new Student("s1", "Ada", 90);
        System.out.println(before.equals(repeated));
        System.out.println(before.equals(after));
        System.out.println(before.id().equals(after.id()));

        var versions = new HashSet<Student>();
        versions.add(before);
        versions.add(after);
        versions.add(repeated);
        System.out.println(versions.size());

        var codes = new HashSet<String>();
        System.out.println(codes.add(before.id()));
        System.out.println(codes.add(after.id()));

        var students = new HashMap<StudentId, Student>();
        var id = new StudentId("s1");
        students.put(id, before);
        students.put(id, after);
        System.out.println(students.get(new StudentId("s1")).score());
        System.out.println(students.size());
    }
}
