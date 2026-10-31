import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.util.Map;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Повторы и значимый регистр";
static final String NORMAL_WHAT="Состав слов и их частоты.";
static final String NORMAL_INPUT="Java java, code! Code code";
static final String NORMAL_EXPECTED="Java=1, java=1, code=2, Code=1; порядок свободен";
static final String BOUNDARY_NAME="Нет английских букв";
static final String BOUNDARY_WHAT="Пустой результат без пустого ключа.";
static final String BOUNDARY_INPUT="123 — абв!";
static final String BOUNDARY_EXPECTED="Пустая карта";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String s="Java java, code! Code code";var a=TaskBinding.words(s);return new Observation(String.valueOf(a),Map.of("Java",1,"java",1,"code",2,"Code",1).equals(a)&&s.equals("Java java, code! Code code"));}
public static Observation observeBoundary()throws Exception{String s="123 — абв!";var a=TaskBinding.words(s);return new Observation(String.valueOf(a),a!=null&&a.isEmpty()&&s.equals("123 — абв!"));}

public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
