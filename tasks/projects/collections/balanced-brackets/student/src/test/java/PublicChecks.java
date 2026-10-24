import java.util.*;
public class PublicChecks {
 public record Observation(String actual, boolean ok) {}
 private static final class CheckFailure extends AssertionError { CheckFailure(String message){super(message);} }
 public static void checkNormal(Observation o){if(!o.ok())throw new CheckFailure("Публичное обычное требование не выполнено");}
 public static void checkBoundary(Observation o){if(!o.ok())throw new CheckFailure("Публичное граничное требование не выполнено");}
 public static void main(String[] args){try{var n=observeNormal();System.out.println("PUBLIC normal | ТРЕБОВАНИЕ: Правильная вложенность строки ([]{}). | ОЖИДАЕТСЯ: true | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: выполнено");var b=observeBoundary();System.out.println("PUBLIC boundary | ТРЕБОВАНИЕ: Пустая строка и лишняя закрывающая скобка ) | ОЖИДАЕТСЯ: true; false | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: выполнено");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие публичному ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения публичного вызова; проверьте решение или привязку.");System.exit(2);}}

 public static Observation observeNormal(){String input="([]{})";boolean r=TaskBinding.valid(input);return new Observation("вход="+input+"; результат="+r,r&&input.equals("([]{})"));}
 public static Observation observeBoundary(){boolean empty=TaskBinding.valid(""),close=TaskBinding.valid(")");return new Observation("пустая строка="+empty+"; закрывающая скобка="+close,empty&&!close);}

}
