import java.lang.AssertionError;
import java.lang.String;
import java.util.*;import java.math.BigInteger;
/** Только два опубликованных наблюдения; клиент TaskBinding адаптируется к выбранному API. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 public static void checkNormal(Observation r){if(!r.passed())throw new AssertionError("Обычный открытый сценарий требует исправления.");}
 public static void checkBoundary(Observation r){if(!r.passed())throw new AssertionError("Граничный открытый сценарий требует исправления.");}
public static final String NORMAL_NAME="Пробел и знак препинания";
public static final String BOUNDARY_NAME="Пустая строка";
public static final String NORMAL_INPUT="Java 25!";
public static final String BOUNDARY_INPUT="Пустая строка";
public static final String NORMAL_EXPECTED="!52 avaJ, необязательный один LF/CRLF";
public static final String BOUNDARY_EXPECTED="Пустой текст, необязательный один LF/CRLF";
static boolean exact(String got,String expected){return got.equals(expected)||got.equals(expected+"\n")||got.equals(expected+"\r\n");}
static String display(String s){return "\""+s.replace("\\","\\\\").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t")+"\"";}
public static Observation observeNormal(){String got=TaskBinding.printed("Java 25!");return new Observation(display(got),exact(got,"!52 avaJ"));}
public static Observation observeBoundary(){String got=TaskBinding.printed("");return new Observation(got.isEmpty()?"Пустой стандартный вывод.":display(got),exact(got,""));}
}
