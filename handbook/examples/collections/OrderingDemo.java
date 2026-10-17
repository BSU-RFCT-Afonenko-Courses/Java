import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import grade.Student;

public class OrderingDemo {
    static final class ByScore implements Comparator<Student> {
        @Override public int compare(Student a, Student b) {
            return Integer.compare(a.score(), b.score());
        }
    }
    public static void main(String[] args) {
        Student a = new Student("s17", "Ada", 90);
        Student b = new Student("s18", "Lin", 90);
        Student c = new Student("s19", "Eva", 80);
        var list = new ArrayList<>(List.of(b, c, a));
        list.sort(new ByScore());
        for (Student s : list) System.out.println(s.id());
        var set = new TreeSet<Student>(new ByScore());
        System.out.println(set.add(a));
        System.out.println(set.add(b));
        System.out.println(set.contains(b));
        System.out.println(a.equals(b));
        System.out.println(set.size());
        list.sort(new ReportOrder());
        for (Student s : list) System.out.println(s.id());
        Student renamed = new Student("s17", "Ann", 90);
        System.out.println(new ReportOrder().compare(a, renamed));
        System.out.println(a.equals(renamed));
    }
}
