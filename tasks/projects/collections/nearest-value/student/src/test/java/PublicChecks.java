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
