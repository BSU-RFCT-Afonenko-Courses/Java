import java.lang.Integer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
class ContractTest {
    record Example(Set<Integer> a, Set<Integer> b, Set<Integer> union, Set<Integer> intersection) {}
    @Test void emptyEqualDisjointAndIndependentComposition() {
        var cases=List.of(
            new Example(Set.of(1,2),Set.of(),Set.of(1,2),Set.of()),
            new Example(Set.of(1,2),Set.of(1,2),Set.of(1,2),Set.of(1,2)),
            new Example(Set.of(1,2),Set.of(3,4),Set.of(1,2,3,4),Set.of()),
            new Example(Set.of(),Set.of(1,2),Set.of(1,2),Set.of()),
            new Example(Set.of(),Set.of(),Set.of(),Set.of()));
        for(var example:cases) {
            Set<Integer> a=new HashSet<>(example.a()),b=new HashSet<>(example.b());
            Set<Integer> union=TaskBinding.union(a,b);
            assertEquals(example.a(),a);assertEquals(example.b(),b);
            Set<Integer> intersection=TaskBinding.intersection(a,b);
            assertEquals(example.union(),union);
            assertEquals(example.intersection(),intersection);
            assertEquals(example.a(),a);assertEquals(example.b(),b);
            var unionBefore=new HashSet<>(union);var intersectionBefore=new HashSet<>(intersection);
            a.add(999);b.add(888);
            assertEquals(unionBefore,union);assertEquals(intersectionBefore,intersection);
        }
    }
}
