import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContainerEqualityDemo {
    public static void main(String[] args) {
        System.out.println(new ArrayList<>(List.of(1, 2))
                .equals(new LinkedList<>(List.of(1, 2))));
        System.out.println(List.of(1, 2).equals(List.of(2, 1)));
        System.out.println(Set.of(1, 2).equals(Set.of(2, 1)));
        System.out.println(Map.of("a", 1, "b", 2)
                .equals(Map.of("b", 2, "a", 1)));
        System.out.println(new ArrayDeque<>(List.of(1, 2))
                .equals(new ArrayDeque<>(List.of(1, 2))));
    }
}
