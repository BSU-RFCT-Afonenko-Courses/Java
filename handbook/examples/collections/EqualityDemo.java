import java.util.HashSet;
import java.util.HashMap;
import grade.Student;

public class EqualityDemo {
    public static void main(String[] args) {
        String a = new String("Ada");
        String b = new String("Ada");
        String alias = a;
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(alias == a);

        StudentId first = new StudentId("s17");
        StudentId same = new StudentId("s17");
        var seen = new HashSet<StudentId>();
        System.out.println(seen.add(first));
        System.out.println(seen.add(same));
        System.out.println(seen.size());
        System.out.println(seen.contains(new StudentId("s17")));
        System.out.println("Aa".hashCode() == "BB".hashCode());
        System.out.println("Aa".equals("BB"));

        Student oldVersion = new Student("s17", "Ada", 80);
        Student newVersion = new Student("s17", "Ada", 95);
        System.out.println(oldVersion.equals(newVersion));
        var byId = new HashMap<String, Student>();
        byId.put(oldVersion.id(), oldVersion);
        System.out.println(byId.put(newVersion.id(), newVersion).score());
        System.out.println(byId.size());
        System.out.println(byId.get("s17").score());
    }
}
