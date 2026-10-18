import grade.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class TreeEqualityDemo {
    public static void main(String[] args) {
        var a = new Student("s1", "Ada", 90);
        var b = new Student("s2", "Lin", 90);
        Comparator<Student> onlyScore = Comparator.comparingInt(Student::score);
        var tree = new TreeSet<Student>(onlyScore);
        tree.add(a);
        System.out.println(a.equals(b));
        System.out.println(tree.add(b));
        System.out.println(tree.contains(b));
        System.out.println(tree.size());

        var list = new ArrayList<>(List.of(a, b));
        list.sort(onlyScore);
        System.out.println(list.size());

        var changedName = new Student("s1", "Grace", 90);
        Comparator<Student> byScoreAndId = Comparator.comparingInt(Student::score)
            .reversed().thenComparing(Student::id);
        System.out.println(a.equals(changedName));
        System.out.println(byScoreAndId.compare(a, changedName));

        Comparator<Student> byAllComponents = byScoreAndId.thenComparing(Student::name);
        var full = new TreeSet<Student>(byAllComponents);
        full.add(a);
        full.add(b);
        full.add(changedName);
        System.out.println(full.size());

        var byScoreMap = new TreeMap<Student, String>(onlyScore);
        byScoreMap.put(a, "первое значение");
        byScoreMap.put(b, "второе значение");
        System.out.println(byScoreMap.size());
        System.out.println(byScoreMap.get(a));
    }
}
