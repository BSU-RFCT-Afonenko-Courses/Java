import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Две группы с повторяющимся значением";
static final String NORMAL_WHAT="Сохранение объекта, размера, кратностей и границы <=X.";
static final String NORMAL_INPUT="[3,1,4,2,3]; X=2";
static final String NORMAL_EXPECTED="Сначала {1,2}, затем {3,3,4}; порядок внутри групп свободен";
static final String BOUNDARY_NAME="Пустой список";
static final String BOUNDARY_WHAT="Сохранение допустимого пустого состояния.";
static final String BOUNDARY_INPUT="[]; X=0";
static final String BOUNDARY_EXPECTED="Тот же пустой список";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{return partition(new ArrayList<>(List.of(3,1,4,2,3)),2);}
public static Observation observeBoundary()throws Exception{return partition(new ArrayList<>(),0);}
static Observation partition(List<Integer> list,int x){var before=new ArrayList<>(list);var identity=list;TaskBinding.partition(list,x);boolean upper=false,ok=list==identity&&list.size()==before.size()&&frequency(list).equals(frequency(before));for(int v:list){if(v>x)upper=true;else if(upper)ok=false;}return new Observation("До="+before+"; после="+list+"; тот же объект="+(identity==list),ok);}static Map<Integer,Integer> frequency(List<Integer> list){var f=new HashMap<Integer,Integer>();for(int n:list)f.merge(n,1,Integer::sum);return f;}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
