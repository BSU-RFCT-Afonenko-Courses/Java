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
