import java.lang.AssertionError;
import java.lang.Integer;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
public class PublicChecks {
 public record Observation(String actual, boolean ok) {}
 private static final class CheckFailure extends AssertionError { CheckFailure(String message){super(message);} }
 public static void checkNormal(Observation o){if(!o.ok())throw new CheckFailure("Публичное обычное требование не выполнено");}
 public static void checkBoundary(Observation o){if(!o.ok())throw new CheckFailure("Публичное граничное требование не выполнено");}


 public static Observation observeNormal(){Set<Integer>a=new HashSet<>(Set.of(1,2)),b=new HashSet<>(Set.of(2,3));var u=TaskBinding.union(a,b);boolean first=a.equals(Set.of(1,2))&&b.equals(Set.of(2,3));var i=TaskBinding.intersection(a,b);boolean preserved=first&&a.equals(Set.of(1,2))&&b.equals(Set.of(2,3));return new Observation("union="+u+"; intersection="+i+"; входы сохранены="+preserved,preserved&&Set.of(1,2,3).equals(u)&&Set.of(2).equals(i));}
 public static Observation observeBoundary(){Set<Integer>a=new HashSet<>(),b=new HashSet<>(Set.of(4));var u=TaskBinding.union(a,b);boolean first=a.isEmpty()&&b.equals(Set.of(4));var i=TaskBinding.intersection(a,b);boolean preserved=first&&a.isEmpty()&&b.equals(Set.of(4));b.add(5);return new Observation("union="+u+"; intersection="+i+"; входы сохранены при вызове="+preserved+"; второй вход после изменения="+b,preserved&&Set.of(4).equals(u)&&Set.of().equals(i)&&a.isEmpty()&&b.equals(Set.of(4,5)));}

}
