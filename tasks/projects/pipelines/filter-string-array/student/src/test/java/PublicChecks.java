import java.util.*;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{CheckFailure(String s){super(s);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure("Обычный открытый результат не соответствует условию");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure("Граничный открытый результат не соответствует условию");}
 public static void main(String[]args){try{var n=observeNormal();System.out.println("PUBLIC normal | [a,bb,bb,ccc], порог 1; ожидается новый String[] [bb,bb,ccc], прежний вход | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");var b=observeBoundary();System.out.println("PUBLIC boundary | Пустой String[], порог 0; ожидается другой пустой String[] | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие открытому ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения; проверьте решение и привязку.");System.exit(2);}}

 public static Observation observeNormal(){String[]a={"a","bb","bb","ccc"};String[]before=a.clone();String[]out=TaskBinding.run(a,1);boolean unchanged=Arrays.equals(before,a);return new Observation("результат="+Arrays.toString(out)+"; новый массив="+(out!=null&&out!=a)+"; вход сохранён="+unchanged,unchanged&&out!=a&&Arrays.equals(new String[]{"bb","bb","ccc"},out));}
 public static Observation observeBoundary(){String[]a={};String[]out=TaskBinding.run(a,0);return new Observation("результат="+Arrays.toString(out)+"; другой массив="+(out!=null&&out!=a)+"; вход="+Arrays.toString(a),out!=null&&out!=a&&out.length==0&&a.length==0);}

}
