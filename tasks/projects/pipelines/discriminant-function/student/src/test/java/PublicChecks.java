import java.util.*;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{CheckFailure(String s){super(s);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure("Обычный открытый результат не соответствует условию");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure("Граничный открытый результат не соответствует условию");}
 public static void main(String[]args){try{var n=observeNormal();System.out.println("PUBLIC normal | (1,-3,2), ожидается конечный дискриминант 1 в допуске 1e-8 + 1e-12*abs(r) | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");var b=observeBoundary();System.out.println("PUBLIC boundary | (0,-1000,1000), ожидается 1000000 в том же смешанном допуске | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытый результат соответствует; исходник и объяснение проверяет преподаватель.");}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие открытому ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: сбой выполнения; проверьте решение и привязку.");System.exit(2);}}

 private static boolean close(double v,double r){return Double.isFinite(v)&&Math.abs(v-r)<=1e-8+1e-12*Math.abs(r);}
 public static Observation observeNormal(){double v=TaskBinding.run(1,-3,2);return new Observation("дискриминант="+v+"; эталон=1",close(v,1));}
 public static Observation observeBoundary(){double v=TaskBinding.run(0,-1000,1000);return new Observation("дискриминант="+v+"; эталон=1000000",close(v,1000000));}

}
