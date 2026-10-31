import java.lang.AssertionError;
import java.lang.Object;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.*;import java.math.BigInteger;
/** Только два опубликованных наблюдения; клиент TaskBinding адаптируется к выбранному API. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 public static void checkNormal(Observation r){if(!r.passed())throw new AssertionError("Обычный открытый сценарий требует исправления.");}
 public static void checkBoundary(Observation r){if(!r.passed())throw new AssertionError("Граничный открытый сценарий требует исправления.");}
public static final String NORMAL_NAME="Четыре операции и непарный последний элемент";
public static final String BOUNDARY_NAME="Одна дробь в четырёх позициях";
public static final String NORMAL_INPUT="1/2 и1/3 для каждой операции; массив[1/2,1/3,2/5]";
public static final String BOUNDARY_INPUT="Один x=1/2; [x,x,x,x]";
public static final String NORMAL_EXPECTED="5/6;1/6;1/6;3/2 и массив[5/6,1/3,2/5]";
public static final String BOUNDARY_EXPECTED="[1,1/2,1,1/2] по значениям";
static boolean fraction(long m,long n,Object x){long[]p=TaskBinding.parts(x);return p[1]!=0&&BigInteger.valueOf(m).multiply(BigInteger.valueOf(p[1])).equals(BigInteger.valueOf(n).multiply(BigInteger.valueOf(p[0])));}
static String fractions(Object[]a){return Arrays.toString(Arrays.stream(a).map(x->Arrays.toString(TaskBinding.parts(x))).toArray());}
public static Observation observeNormal(){long[][]expected={{5,6},{1,6},{1,6},{3,2}};boolean ok=true;StringBuilder actual=new StringBuilder("Операции=");for(int op=0;op<4;op++){Object x=TaskBinding.operation(TaskBinding.value(1,2),TaskBinding.value(1,3),op);actual.append(Arrays.toString(TaskBinding.parts(x))).append("; ");ok &= fraction(expected[op][0],expected[op][1],x);}Object[]a=TaskBinding.array(TaskBinding.value(1,2),TaskBinding.value(1,3),TaskBinding.value(2,5));TaskBinding.transform(a);return new Observation(actual+"Массив="+fractions(a),ok&&fraction(5,6,a[0])&&fraction(1,3,a[1])&&fraction(2,5,a[2]));}
public static Observation observeBoundary(){Object x=TaskBinding.value(1,2);Object[]a=TaskBinding.array(x,x,x,x);TaskBinding.transform(a);return new Observation("После общей ссылки="+fractions(a),fraction(1,1,a[0])&&fraction(1,2,a[1])&&fraction(1,1,a[2])&&fraction(1,2,a[3]));}
}
