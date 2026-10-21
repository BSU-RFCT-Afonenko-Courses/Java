import java.util.*;
public class PublicChecks {
 public record Observation(String actual, boolean ok) {}
 private static final class CheckFailure extends AssertionError { CheckFailure(String message){super(message);} }
 public static void checkNormal(Observation o){if(!o.ok())throw new CheckFailure("Публичное обычное требование не выполнено");}
 public static void checkBoundary(Observation o){if(!o.ok())throw new CheckFailure("Публичное граничное требование не выполнено");}
 public static void main(String[] args){try{var n=observeNormal();System.out.println("PUBLIC normal | ТРЕБОВАНИЕ: Сумма {0:1,2:3} и {1:2,2:-3}, сохранение входов | ОЖИДАЕТСЯ: коэффициенты 1; 2; 0, самостоятельная HashMap | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: выполнено");var b=observeBoundary();System.out.println("PUBLIC boundary | ТРЕБОВАНИЕ: Сумма двух пустых HashMap | ОЖИДАЕТСЯ: самостоятельный нулевой многочлен; явные нули допустимы | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: выполнено");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие публичному ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения публичного вызова; проверьте решение или привязку.");System.exit(2);}}

 private static boolean close(double a,double r){return Double.isFinite(a)&&Math.abs(a-r)<=1e-9+1e-12*Math.abs(r);}
 private static boolean result(HashMap<Integer,Double> a,Map<Integer,Double> expected){if(a==null)return false;Set<Integer>keys=new HashSet<>(a.keySet());keys.addAll(expected.keySet());for(Integer k:keys){if(k==null||k<0)return false;Double v=a.getOrDefault(k,0.0);if(v==null||!close(v,expected.getOrDefault(k,0.0)))return false;}return true;}
 public static Observation observeNormal(){var a=new HashMap<>(Map.of(0,1.0,2,3.0));var b=new HashMap<>(Map.of(1,2.0,2,-3.0));var out=TaskBinding.add(a,b);boolean preserved=a.equals(Map.of(0,1.0,2,3.0))&&b.equals(Map.of(1,2.0,2,-3.0));boolean ok=preserved&&out!=a&&out!=b&&result(out,Map.of(0,1.0,1,2.0));return new Observation("результат="+out+"; входы сохранены="+preserved+"; отдельная HashMap="+(out!=null&&out!=a&&out!=b),ok);}
 public static Observation observeBoundary(){var a=new HashMap<Integer,Double>();var b=new HashMap<Integer,Double>();var out=TaskBinding.add(a,b);return new Observation("результат="+out+"; входы="+a+" / "+b+"; отдельная HashMap="+(out!=null&&out!=a&&out!=b),a.isEmpty()&&b.isEmpty()&&out!=a&&out!=b&&result(out,Map.of()));}

}
