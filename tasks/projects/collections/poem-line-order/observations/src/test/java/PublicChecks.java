import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Равные длины и устойчивый порядок";
static final String NORMAL_WHAT="Копия по char-длине и сохранение исходного списка.";
static final String NORMAL_INPUT="Четыре опубликованные строки Пушкина в порядке1,2,3,4";
static final String NORMAL_EXPECTED="Позиции2,3,1,4; исходник прежний";
static final String BOUNDARY_NAME="Пустой список строк";
static final String BOUNDARY_WHAT="Допустимый пустой результат.";
static final String BOUNDARY_INPUT="Пустой список";
static final String BOUNDARY_EXPECTED="Пустая последовательность; исходник прежний";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{var s=new ArrayList<>(List.of("Всё ходит по цепи кругом;","Златая цепь на дубе том:","У лукоморья дуб зелёный;","И днём и ночью кот учёный"));var before=new ArrayList<>(s);var a=TaskBinding.ordered(s);var expected=List.of(before.get(1),before.get(2),before.get(0),before.get(3));return new Observation("Исходный="+s+"; результат="+a,expected.equals(a)&&s.equals(before)&&a!=s);}
public static Observation observeBoundary()throws Exception{var s=new ArrayList<String>();var a=TaskBinding.ordered(s);return new Observation("Исходный="+s+"; результат="+a,a!=null&&a.isEmpty()&&s.isEmpty());}

public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
