import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.util.Locale;
import java.util.Set;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Одно слово в разных регистрах";
static final String NORMAL_WHAT="Устранение повторов после преобразования регистра.";
static final String NORMAL_INPUT="one, ONE! two";
static final String NORMAL_EXPECTED="Состав {one,two}, порядок свободен";
static final String BOUNDARY_NAME="Локаль с иным правилом буквы I";
static final String BOUNDARY_WHAT="Независимость состава от языка среды.";
static final String BOUNDARY_INPUT="I I, i; tr-TR";
static final String BOUNDARY_EXPECTED="Единственный элемент i";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String s="one, ONE! two";var a=TaskBinding.words(s);return new Observation(String.valueOf(a),Set.of("one","two").equals(a));}
public static Observation observeBoundary()throws Exception{Locale old=Locale.getDefault();try{Locale.setDefault(Locale.forLanguageTag("tr-TR"));String s="I I, i";var a=TaskBinding.words(s);return new Observation("tr-TR: "+a,Set.of("i").equals(a));}finally{Locale.setDefault(old);}}

public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
