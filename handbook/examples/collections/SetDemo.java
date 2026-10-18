import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo {
    public static void main(String[] args) {
        var words = List.of("b", "a", "b");
        System.out.println(new HashSet<>(words).size());
        System.out.println(new LinkedHashSet<>(words));
        System.out.println(new TreeSet<>(words));
        var a = Set.of(1, 2);
        var b = Set.of(2, 3);
        var union = new HashSet<>(a);
        var intersection = new HashSet<>(a);
        union.addAll(b);
        intersection.retainAll(b);
        System.out.println(union.equals(Set.of(1, 2, 3)));
        System.out.println(intersection.equals(Set.of(2)));
        System.out.println(a.equals(Set.of(1, 2)));
        System.out.println(b.equals(Set.of(2, 3)));
        System.out.println(Set.copyOf(List.of("a", "a")).size());
        try { Set.of("a", "a"); }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        var neighbors = new TreeSet<>(List.of(10, 20, 40));
        System.out.println(neighbors.floor(25));
        System.out.println(neighbors.ceiling(25));
        System.out.println(neighbors.lower(10));
    }
}
