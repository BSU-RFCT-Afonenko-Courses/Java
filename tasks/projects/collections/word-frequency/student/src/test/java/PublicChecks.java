import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Повторы и значимый регистр";
static final String NORMAL_WHAT="Состав слов и их частоты.";
static final String NORMAL_INPUT="Java java, code! Code code";
static final String NORMAL_EXPECTED="Java=1, java=1, code=2, Code=1; порядок свободен";
static final String BOUNDARY_NAME="Нет английских букв";
static final String BOUNDARY_WHAT="Пустой результат без пустого ключа.";
static final String BOUNDARY_INPUT="123 — абв!";
static final String BOUNDARY_EXPECTED="Пустая карта";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String s="Java java, code! Code code";var a=TaskBinding.words(s);return new Observation(String.valueOf(a),Map.of("Java",1,"java",1,"code",2,"Code",1).equals(a)&&s.equals("Java java, code! Code code"));}
public static Observation observeBoundary()throws Exception{String s="123 — абв!";var a=TaskBinding.words(s);return new Observation(String.valueOf(a),a!=null&&a.isEmpty()&&s.equals("123 — абв!"));}

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
