import java.lang.AssertionError;
import java.lang.String;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal(){return new Observation(MoneyProbe.check(new String[]{"100","0","100"},"0.20",false),true);}
 public static Observation observeBoundary(){return new Observation(MoneyProbe.check(new String[]{},"1",false),true);}
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Цена");}public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Пустой список");}
}
