import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HashEqualityDemo {
    public static void main(String[] args) {
        var first = new StudentId("s17");
        var second = new StudentId("s17");
        var ids = new HashSet<StudentId>();
        System.out.println(first == second);
        System.out.println(first.equals(second));
        System.out.println(first.hashCode() == second.hashCode());
        System.out.println(ids.add(first));
        System.out.println(ids.add(second));
        System.out.println(ids.contains(second));
        System.out.println(ids.size());

        var counts = new HashMap<StudentId, Integer>();
        for (StudentId id : List.of(first, second, new StudentId("s18"))) {
            counts.put(id, counts.getOrDefault(id, 0) + 1);
        }
        System.out.println(counts.get(new StudentId("s17")));
        System.out.println(counts.size());

        var aa = new StudentId("Aa");
        var bb = new StudentId("BB");
        System.out.println(aa.hashCode() == bb.hashCode());
        System.out.println(aa.equals(bb));
        var collisions = new HashSet<StudentId>();
        collisions.add(aa);
        collisions.add(bb);
        System.out.println(collisions.size());
    }
}
