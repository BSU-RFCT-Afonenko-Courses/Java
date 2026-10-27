import java.util.*;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{CheckFailure(String s){super(s);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure("Обычный открытый результат не соответствует условию");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure("Граничный открытый результат не соответствует условию");}
 public static void main(String[]args){try{var n=observeNormal();System.out.println("PUBLIC normal | Строка ab a; ожидаются 97:2, 98:1, 32:1 | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");var b=observeBoundary();System.out.println("PUBLIC boundary | Одна суррогатная пара; ожидаются отдельные 55357:1 и 56832:1 | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие открытому ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения; проверьте решение и привязку.");System.exit(2);}}

 public static Observation observeNormal(){String text="ab a";var out=TaskBinding.run(text);return new Observation("частоты="+out+"; вход="+text,Map.of(97,2L,98,1L,32,1L).equals(out)&&text.equals("ab a"));}
 public static Observation observeBoundary(){String text="\uD83D\uDE00";var out=TaskBinding.run(text);return new Observation("кодовые единицы 55357/56832; частоты="+out,Map.of(55357,1L,56832,1L).equals(out)&&text.length()==2&&text.charAt(0)==55357&&text.charAt(1)==56832);}

}
