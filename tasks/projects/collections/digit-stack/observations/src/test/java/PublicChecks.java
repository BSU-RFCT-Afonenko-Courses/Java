import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Ноль в начале результата";
static final String NORMAL_WHAT="Обратный порядок всех цифр без потери нуля.";
static final String NORMAL_INPUT="120";
static final String NORMAL_EXPECTED="021";
static final String BOUNDARY_NAME="Входной ноль";
static final String BOUNDARY_WHAT="Одна цифра вместо пустого результата.";
static final String BOUNDARY_INPUT="0";
static final String BOUNDARY_EXPECTED="0";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String a=TaskBinding.digits(120);return new Observation(String.valueOf(a),"021".equals(a));}
public static Observation observeBoundary()throws Exception{String a=TaskBinding.digits(0);return new Observation(String.valueOf(a),"0".equals(a));}

public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
