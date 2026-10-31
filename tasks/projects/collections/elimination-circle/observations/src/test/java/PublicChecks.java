import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.List;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Переход через начало круга";
static final String NORMAL_WHAT="Одинаковый порядок двух реализаций.";
static final String NORMAL_INPUT="N=5";
static final String NORMAL_EXPECTED="Для обеих:2,4,1,5; остаётся3";
static final String BOUNDARY_NAME="Один участник";
static final String BOUNDARY_WHAT="Завершение без удаления последнего.";
static final String BOUNDARY_INPUT="N=1";
static final String BOUNDARY_EXPECTED="Для обеих:удалений нет, остаётся1";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{return circle(5,List.of(2,4,1,5),3);}
public static Observation observeBoundary()throws Exception{return circle(1,List.of(),1);}
static Observation circle(int n,List<Integer> expected,int last){boolean ok=true;var actual=new StringBuilder();for(int kind=0;kind<2;kind++){var removed=TaskBinding.removed(n,kind);int survivor=TaskBinding.survivor(n,kind);actual.append(kind==0?"ArrayList: ":"; LinkedList: ").append(removed).append("; остался=").append(survivor);ok &= expected.equals(removed)&&survivor==last;}return new Observation(actual.toString(),ok);}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
