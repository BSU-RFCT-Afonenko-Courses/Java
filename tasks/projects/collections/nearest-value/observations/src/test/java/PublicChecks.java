import java.lang.AssertionError;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.OptionalDouble;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="По обе стороны и на равном расстоянии";
static final String NORMAL_WHAT="Выбор ближайшего и обычная ничья.";
static final String NORMAL_INPUT="Добавить2,8; запросы3,7,5";
static final String NORMAL_EXPECTED="OptionalDouble2,8,2";
static final String BOUNDARY_NAME="Равенство расстояний после округления";
static final String BOUNDARY_WHAT="Ничья среди всех значений по вычисленному Math.abs.";
static final String BOUNDARY_INPUT="-100,0,100; запрос1e150";
static final String BOUNDARY_EXPECTED="OptionalDouble[-100]";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{Object s=TaskBinding.create();TaskBinding.add(s,2);TaskBinding.add(s,8);var a=TaskBinding.nearest(s,3);var b=TaskBinding.nearest(s,7);var c=TaskBinding.nearest(s,5);return new Observation(List.of(a,b,c).toString(),equal(a,2)&&equal(b,8)&&equal(c,2));}
public static Observation observeBoundary()throws Exception{Object s=TaskBinding.create();for(double v:new double[]{-100,0,100})TaskBinding.add(s,v);var a=TaskBinding.nearest(s,1e150);return new Observation(String.valueOf(a),equal(a,-100));}
static boolean equal(OptionalDouble a,double b){return a!=null&&a.isPresent()&&Double.compare(a.getAsDouble(),b)==0;}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
