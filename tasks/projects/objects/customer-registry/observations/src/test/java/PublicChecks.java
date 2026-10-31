import java.lang.AssertionError;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
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
public static final String NORMAL_NAME="Стабильный порядок имён и обе границы карточки";
public static final String BOUNDARY_NAME="Пустой реестр";
public static final String NORMAL_INPUT="В/Б/А: id13/12/11; карточки30/20/10; интервал10..20 (16цифр)";
public static final String BOUNDARY_INPUT="Пустой Customer[]; тот же интервал";
public static final String NORMAL_EXPECTED="Б,В,А; карточки А и Б в любом порядке; вход сохранён";
public static final String BOUNDARY_EXPECTED="Обе подборки пусты";
static List<?> values(Object[]a){return a==null?null:Arrays.stream(a).map(TaskBinding::value).toList();}
static boolean kept(Object[]input,Object[]before,List<?>state){if(input.length!=before.length)return false;for(int i=0;i<input.length;i++)if(input[i]!=before[i])return false;return Objects.equals(values(input),state);}
static Map<Object,Integer> bag(List<?>rows){if(rows==null)return null;Map<Object,Integer>m=new HashMap<>();for(Object row:rows)m.merge(row,1,Integer::sum);return m;}
public static Observation observeNormal(){Object a=TaskBinding.customer(11,"Иванов","Антон","Ильич","0000000000000010"),b=TaskBinding.customer(12,"Белов","Борис","Петрович","0000000000000020"),c=TaskBinding.customer(13,"Иванов","Антон","Ильич","0000000000000030");Object[]in=TaskBinding.array(c,b,a),before=in.clone();List<?>state=values(in);Object[]sorted=TaskBinding.sorted(in);List<?>actualSorted=values(sorted);boolean keep1=kept(in,before,state);boolean model=state.equals(List.of(List.of(13L,"Иванов","Антон","Ильич","Учебный адрес","0000000000000030","Счёт13"),List.of(12L,"Белов","Борис","Петрович","Учебный адрес","0000000000000020","Счёт12"),List.of(11L,"Иванов","Антон","Ильич","Учебный адрес","0000000000000010","Счёт11")));if(!keep1)return new Observation("Алфавит="+actualSorted+"; исходный массив или объекты изменены.",false);Object[]range=TaskBinding.range(in,"0000000000000010","0000000000000020");List<?>actualRange=values(range);boolean keep2=kept(in,before,state);return new Observation("Алфавит="+actualSorted+"; карточки="+actualRange+"; вход сохранён="+(keep1&&keep2),Objects.equals(actualSorted,values(TaskBinding.array(b,c,a)))&&Objects.equals(bag(actualRange),bag(values(TaskBinding.array(a,b))))&&keep1&&keep2&&model);}
public static Observation observeBoundary(){Object[]a=TaskBinding.array();Object[]s=TaskBinding.sorted(a),r=TaskBinding.range(a,"0000000000000010","0000000000000020");return new Observation("Алфавит="+values(s)+"; карточки="+values(r),s!=null&&r!=null&&s.length==0&&r.length==0&&a.length==0);}
}
