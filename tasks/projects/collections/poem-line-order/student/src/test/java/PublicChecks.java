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
