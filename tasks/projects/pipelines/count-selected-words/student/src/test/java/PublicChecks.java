import java.util.*;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{CheckFailure(String s){super(s);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure("Обычный открытый результат не соответствует условию");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure("Граничный открытый результат не соответствует условию");}
 public static void main(String[]args){try{var n=observeNormal();System.out.println("PUBLIC normal | Текст one, two two; One, запросы one/two/absent/one; ожидается [one-1, two-2, absent-0, one-1] | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");var b=observeBoundary();System.out.println("PUBLIC boundary | Пустой текст, запросы One/One; ожидается [One-0, One-0] | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие открытому ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения; проверьте решение и привязку.");System.exit(2);}}

 public static String report(String text){return text.endsWith("\r\n")?text.substring(0,text.length()-2):text.endsWith("\n")?text.substring(0,text.length()-1):text;}
 public static Observation observeNormal(){String text="one, two two; One";var words=new ArrayList<>(List.of("one","two","absent","one"));String out=TaskBinding.run(text,words);boolean same=text.equals("one, two two; One")&&words.equals(List.of("one","two","absent","one"));return new Observation("stdout="+PublicOutput.escaped(out)+"; входы сохранены="+same,same&&report(out).equals("[one-1, two-2, absent-0, one-1]"));}
 public static Observation observeBoundary(){String text="";var words=new ArrayList<>(List.of("One","One"));String out=TaskBinding.run(text,words);boolean same=text.isEmpty()&&words.equals(List.of("One","One"));return new Observation("stdout="+PublicOutput.escaped(out)+"; входы сохранены="+same,same&&report(out).equals("[One-0, One-0]"));}

}
