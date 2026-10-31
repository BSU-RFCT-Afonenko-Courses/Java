import java.lang.AssertionError;
import java.lang.String;
import java.util.*;
public class PublicChecks {
 public record Observation(String actual, boolean ok) {}
 private static final class CheckFailure extends AssertionError { CheckFailure(String message){super(message);} }
 public static void checkNormal(Observation o){if(!o.ok())throw new CheckFailure("Публичное обычное требование не выполнено");}
 public static void checkBoundary(Observation o){if(!o.ok())throw new CheckFailure("Публичное граничное требование не выполнено");}


 public static Observation observeNormal(){String input="([]{})";boolean r=TaskBinding.valid(input);return new Observation("вход="+input+"; результат="+r,r&&input.equals("([]{})"));}
 public static Observation observeBoundary(){boolean empty=TaskBinding.valid(""),close=TaskBinding.valid(")");return new Observation("пустая строка="+empty+"; закрывающая скобка="+close,empty&&!close);}

}
