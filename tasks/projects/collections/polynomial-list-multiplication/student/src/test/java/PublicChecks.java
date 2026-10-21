import java.util.*;
/** Два PUBLIC наблюдения через выбранную привязку. Критерии исходника оценивает преподаватель. */
public class PublicChecks {
static final String NORMAL_NAME="Совпадающие суммы степеней";
static final String NORMAL_WHAT="Коэффициенты произведения и сохранение входов.";
static final String NORMAL_INPUT="[1,2] и [3,4]";
static final String NORMAL_EXPECTED="Самостоятельный список[3,10,8], длина3, входы прежние; допуск из условия";
static final String BOUNDARY_NAME="Один пустой множитель";
static final String BOUNDARY_WHAT="Пустое представление нулевого многочлена.";
static final String BOUNDARY_INPUT="[1,0] и []";
static final String BOUNDARY_EXPECTED="Пустой результат, входы прежние";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{return product(new ArrayList<>(List.of(1.0,2.0)),new ArrayList<>(List.of(3.0,4.0)),List.of(3.0,10.0,8.0));}
public static Observation observeBoundary()throws Exception{return product(new ArrayList<>(List.of(1.0,0.0)),new ArrayList<>(),List.of());}
static Observation product(List<Double>a,List<Double>b,List<Double>expected){var aa=new ArrayList<>(a);var bb=new ArrayList<>(b);var r=TaskBinding.multiply(a,b);boolean ok=r!=null&&r!=a&&r!=b&&a.equals(aa)&&b.equals(bb)&&r.size()==expected.size();if(ok)for(int i=0;i<r.size();i++)ok &= r.get(i)!=null&&Double.isFinite(r.get(i))&&Math.abs(r.get(i)-expected.get(i))<=1e-6+1e-12*Math.abs(expected.get(i));return new Observation("Результат="+r+"; входы после="+a+" и "+b,ok);}
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
