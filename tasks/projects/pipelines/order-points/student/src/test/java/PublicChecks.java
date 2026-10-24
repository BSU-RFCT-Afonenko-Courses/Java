import java.util.*;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{CheckFailure(String s){super(s);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure("Обычный открытый результат не соответствует условию");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure("Граничный открытый результат не соответствует условию");}
 public static void main(String[]args){try{var n=observeNormal();System.out.println("PUBLIC normal | (1:10),(2:0); ожидается печать (2:0) (1:10), вход сохранён | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");var b=observeBoundary();System.out.println("PUBLIC boundary | (3:4),(0:5),(3:4); ожидается тот же взаимный порядок и сохранный вход | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие открытому ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения; проверьте решение и привязку.");System.exit(2);}}

 public static List<String> tokens(String text){return text.isBlank()?List.of():List.of(text.trim().split("\\s+"));}
 public static Observation observeNormal(){int[][]a={{1,10},{2,0}};String out=TaskBinding.run(a);boolean same=Arrays.deepEquals(a,new int[][]{{1,10},{2,0}});return new Observation("stdout="+PublicOutput.escaped(out)+"; вход сохранён="+same,same&&tokens(out).equals(List.of("(2:0)","(1:10)")));}
 public static Observation observeBoundary(){int[][]a={{3,4},{0,5},{3,4}};String out=TaskBinding.run(a);boolean same=Arrays.deepEquals(a,new int[][]{{3,4},{0,5},{3,4}});return new Observation("stdout="+PublicOutput.escaped(out)+"; вход сохранён="+same,same&&tokens(out).equals(List.of("(3:4)","(0:5)","(3:4)")));}

}
