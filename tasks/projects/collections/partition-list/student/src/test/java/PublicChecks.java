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
static int example(boolean normal){
 System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));
 System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));
 System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));
 System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
 try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует; исходник, техника, собственные материалы и объяснение оценивает преподаватель.");return 0;}
 catch(coursesupport.JavaProcess.UncaughtStudentException e){System.out.println("Статус: необработанная ошибка выполнения программы.");return 1;}
 catch(coursesupport.JavaProcess.StudentTimeout e){System.out.println("Статус: превышено время выполнения программы.");return 1;}
 catch(coursesupport.JavaProcess.StudentOutputLimit e){System.out.println("Статус: превышен объём вывода программы.");return 1;}
 catch(UnsupportedOperationException e){System.out.println("Статус: заготовка или выбранная привязка ещё не подготовлена; предъявите реализацию преподавателю.");return 1;}
 catch(AssertionError e){System.out.println("Статус: наблюдаемый результат не соответствует; сопоставьте с опубликованным случаем.");return 1;}
 catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
 catch(Exception e){System.out.println("Статус: операция завершилась ошибкой; проверьте реализацию и выбранную привязку. Тип: "+e.getClass().getSimpleName());return 1;}
}
public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
