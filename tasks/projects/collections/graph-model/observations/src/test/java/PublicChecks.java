import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Симметрия и сохранение вершин";
static final String NORMAL_WHAT="Наблюдение рёбер и соседей при изменениях.";
static final String NORMAL_INPUT="Graph(3); дважды добавить0—1, удалить, повторно удалить";
static final String NORMAL_EXPECTED="Соседи после добавления:[{1},{0},{}]; после удаления:[{},{},{}]";
static final String BOUNDARY_NAME="Ноль вершин";
static final String BOUNDARY_WHAT="Создание при n=0 без вызова операций с индексами.";
static final String BOUNDARY_INPUT="Graph(0)";
static final String BOUNDARY_EXPECTED="Конструктор завершён; пустое хранение также проверяет преподаватель по исходнику";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{Object g=TaskBinding.graph(3);TaskBinding.add(g,0,1);TaskBinding.add(g,0,1);var a=TaskBinding.neighbors(g,0);var b=TaskBinding.neighbors(g,1);var c=TaskBinding.neighbors(g,2);boolean ok=Set.of(1).equals(a)&&Set.of(0).equals(b)&&Set.of().equals(c)&&TaskBinding.edge(g,0,1)&&TaskBinding.edge(g,1,0);String before=List.of(a,b,c).toString();TaskBinding.remove(g,0,1);TaskBinding.remove(g,0,1);var after=new ArrayList<Set<Integer>>();for(int i=0;i<3;i++){var neighbors=TaskBinding.neighbors(g,i);after.add(neighbors);ok &= neighbors!=null&&neighbors.isEmpty();}ok &= !TaskBinding.edge(g,0,1)&&!TaskBinding.edge(g,1,0);return new Observation("Соседи до удаления="+before+"; после="+after,ok);}
public static Observation observeBoundary()throws Exception{Object g=TaskBinding.graph(0);return new Observation(g==null?"Объект не создан":"Конструктор Graph(0) завершён; отсутствие вершин и рёбер проверяется также по исходнику",g!=null);}

public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с обычным случаем условия.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}


}
