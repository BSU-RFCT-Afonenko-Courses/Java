import java.lang.Throwable;
import java.lang.AssertionError;
import java.lang.Integer;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class PublicChecks {
    public record Observation(String actual, boolean ok) {}
    private static final class CheckFailure extends AssertionError {
        @java.io.Serial
        private static final long serialVersionUID = 1L;

        CheckFailure(String message) {
            super(message);
        }
    }
    public static void checkNormal(Observation o) {
        if (!o.ok())
            throw new CheckFailure(
                "Ожидается: union={1,2,3}; intersection={2}; входы сохранены; порядок свободен."
                + "; получено: " + o.actual());
    }
    public static void checkBoundary(Observation o) {
        if (!o.ok())
            throw new CheckFailure(
                "Ожидается: union={4}; intersection={}; входы сохранены; изменение второго входа не меняет результат; порядок свободен."
                + "; получено: " + o.actual());
    }

    public static Observation observeNormal() throws Throwable {
        Set<Integer> a = new HashSet<>(Set.of(1, 2)), b = new HashSet<>(Set.of(2, 3));
        var u = TaskBinding.union(a, b);
        boolean first = a.equals(Set.of(1, 2)) && b.equals(Set.of(2, 3));
        var i = TaskBinding.intersection(a, b);
        boolean preserved = first && a.equals(Set.of(1, 2)) && b.equals(Set.of(2, 3));
        return new Observation("union=" + u + "; intersection=" + i + "; входы сохранены=" + preserved,
            preserved && Set.of(1, 2, 3).equals(u) && Set.of(2).equals(i));
    }
    public static Observation observeBoundary() throws Throwable {
        Set<Integer> a = new HashSet<>(), b = new HashSet<>(Set.of(4));
        var u = TaskBinding.union(a, b);
        boolean first = a.isEmpty() && b.equals(Set.of(4));
        var i = TaskBinding.intersection(a, b);
        boolean preserved = first && a.isEmpty() && b.equals(Set.of(4));
        b.add(5);
        return new Observation("union=" + u + "; intersection=" + i
                + "; входы сохранены при вызове=" + preserved + "; второй вход после изменения=" + b,
            preserved && Set.of(4).equals(u) && Set.of().equals(i) && a.isEmpty() && b.equals(Set.of(4, 5)));
    }
}
