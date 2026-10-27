import java.util.*;
public class PublicChecks {
 public record Observation(String actual, boolean ok) {}
 private static final class CheckFailure extends AssertionError { CheckFailure(String message){super(message);} }
 public static void checkNormal(Observation o){if(!o.ok())throw new CheckFailure("Публичное обычное требование не выполнено");}
 public static void checkBoundary(Observation o){if(!o.ok())throw new CheckFailure("Публичное граничное требование не выполнено");}
 public static void main(String[] args){try{var n=observeNormal();System.out.println("PUBLIC normal | ТРЕБОВАНИЕ: Объединение, пересечение и сохранение входов {1,2}/{2,3} | ОЖИДАЕТСЯ: {1,2,3}; {2}; входы сохранены | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: выполнено");var b=observeBoundary();System.out.println("PUBLIC boundary | ТРЕБОВАНИЕ: Независимость результата пустого множества и {4} | ОЖИДАЕТСЯ: {4}; пустое; после добавления 5 прежние результаты сохранены | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: выполнено");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие публичному ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения публичного вызова; проверьте решение или привязку.");System.exit(2);}}

 public static Observation observeNormal(){Set<Integer>a=new HashSet<>(Set.of(1,2)),b=new HashSet<>(Set.of(2,3));var u=TaskBinding.union(a,b);boolean first=a.equals(Set.of(1,2))&&b.equals(Set.of(2,3));var i=TaskBinding.intersection(a,b);boolean preserved=first&&a.equals(Set.of(1,2))&&b.equals(Set.of(2,3));return new Observation("union="+u+"; intersection="+i+"; входы сохранены="+preserved,preserved&&Set.of(1,2,3).equals(u)&&Set.of(2).equals(i));}
 public static Observation observeBoundary(){Set<Integer>a=new HashSet<>(),b=new HashSet<>(Set.of(4));var u=TaskBinding.union(a,b);boolean first=a.isEmpty()&&b.equals(Set.of(4));var i=TaskBinding.intersection(a,b);boolean preserved=first&&a.isEmpty()&&b.equals(Set.of(4));b.add(5);return new Observation("union="+u+"; intersection="+i+"; входы сохранены при вызове="+preserved+"; второй вход после изменения="+b,preserved&&Set.of(4).equals(u)&&Set.of().equals(i)&&a.isEmpty()&&b.equals(Set.of(4,5)));}

}
