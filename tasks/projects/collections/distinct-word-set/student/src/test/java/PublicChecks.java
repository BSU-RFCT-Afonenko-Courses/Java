import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Одно слово в разных регистрах";
static final String NORMAL_WHAT="Устранение повторов после преобразования регистра.";
static final String NORMAL_INPUT="one, ONE! two";
static final String NORMAL_EXPECTED="Состав {one,two}, порядок свободен";
static final String BOUNDARY_NAME="Локаль с иным правилом буквы I";
static final String BOUNDARY_WHAT="Независимость состава от языка среды.";
static final String BOUNDARY_INPUT="I I, i; tr-TR";
static final String BOUNDARY_EXPECTED="Единственный элемент i";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String s="one, ONE! two";var a=TaskBinding.words(s);return new Observation(String.valueOf(a),Set.of("one","two").equals(a));}
public static Observation observeBoundary()throws Exception{Locale old=Locale.getDefault();try{Locale.setDefault(Locale.forLanguageTag("tr-TR"));String s="I I, i";var a=TaskBinding.words(s);return new Observation("tr-TR: "+a,Set.of("i").equals(a));}finally{Locale.setDefault(old);}}

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
