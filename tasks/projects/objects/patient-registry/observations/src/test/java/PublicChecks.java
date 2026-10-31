import java.lang.AssertionError;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.*;import java.math.BigInteger;
/** Только два опубликованных наблюдения; клиент TaskBinding адаптируется к выбранному API. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 public static void checkNormal(Observation r){if(!r.passed())throw new AssertionError("Обычный открытый сценарий требует исправления.");}
 public static void checkBoundary(Observation r){if(!r.passed())throw new AssertionError("Граничный открытый сценарий требует исправления.");}
public static final String NORMAL_NAME="Точный диагноз, порядок и повторное вхождение";
public static final String BOUNDARY_NAME="Пустой массив пациентов";
public static final String NORMAL_INPUT="[В43,А41,Б42,А41]; Грипп/грипп; карты103/101/102/101";
public static final String BOUNDARY_INPUT="Пустой Patient[], Грипп, интервал101..102";
public static final String NORMAL_EXPECTED="Диагноз В/А/А; карты А/Б/А; вход сохранён";
public static final String BOUNDARY_EXPECTED="Обе подборки пусты";
// Снимок скалярных свойств принадлежит наблюдателю; student values вправе быть live view.
static List<?> value(Object object){return new ArrayList<>(TaskBinding.value(object));}
static List<?> values(Object[]a){return a==null?null:Arrays.stream(a).map(PublicChecks::value).toList();}
static boolean kept(Object[]input,Object[]before,List<?>state){if(input.length!=before.length)return false;for(int i=0;i<input.length;i++)if(input[i]!=before[i])return false;return Objects.equals(values(input),state);}
static Map<Object,Integer> bag(List<?>rows){if(rows==null)return null;Map<Object,Integer>m=new HashMap<>();for(Object row:rows)m.merge(row,1,Integer::sum);return m;}
public static Observation observeNormal(){Object a=TaskBinding.patient(41,101,"Грипп"),b=TaskBinding.patient(42,102,"грипп"),c=TaskBinding.patient(43,103,"Грипп");Object[]in=TaskBinding.array(c,a,b,a),before=in.clone();List<?>state=values(in),expectedD=values(new Object[]{c,a,a});Object[]d=TaskBinding.diagnosis(in,"Грипп");List<?>actualD=values(d);boolean k1=kept(in,before,state);boolean model=state.equals(List.of(List.of(43L,"Учебный","Иван","Иванович","Учебный адрес","00123",103L,"Грипп"),List.of(41L,"Учебный","Иван","Иванович","Учебный адрес","00123",101L,"Грипп"),List.of(42L,"Учебный","Иван","Иванович","Учебный адрес","00123",102L,"грипп"),List.of(41L,"Учебный","Иван","Иванович","Учебный адрес","00123",101L,"Грипп")));if(!k1)return new Observation("Диагноз="+actualD+"; исходный массив или объекты изменены.",false);Object[]beforeRange=in.clone();List<?>rangeState=values(in),expectedRange=values(new Object[]{a,b,a});Object[]r=TaskBinding.range(in,101,102);List<?>actualR=values(r);boolean k2=kept(in,beforeRange,rangeState);return new Observation("Диагноз="+actualD+"; номера="+actualR+"; вход сохранён="+(k1&&k2),Objects.equals(actualD,expectedD)&&Objects.equals(actualR,expectedRange)&&k1&&k2&&model);}
public static Observation observeBoundary(){Object[]a=TaskBinding.array();Object[]d=TaskBinding.diagnosis(a,"Грипп");List<?>actualD=values(d);Object[]r=TaskBinding.range(a,101,102);return new Observation("Диагноз="+actualD+"; номера="+values(r),d!=null&&r!=null&&d.length==0&&r.length==0&&a.length==0);}
}
