import java.util.*;import java.math.BigDecimal;
public class PublicChecks {
static final String NORMAL_NAME="Повторы нечётного значения";
static final String NORMAL_WHAT="Порядок, повторы и нулевой хвост в том же массиве.";
static final String NORMAL_INPUT="[2,3,4,3]";
static final String NORMAL_EXPECTED="[3,3,0,0]";
static final String BOUNDARY_NAME="Отрицательные нечётные";
static final String BOUNDARY_WHAT="Нечётность отрицательных значений.";
static final String BOUNDARY_INPUT="[-3,0,-2,-1]";
static final String BOUNDARY_EXPECTED="[-3,-1,0,0]";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{return compact(new int[]{2,3,4,3},new int[]{3,3,0,0});}
public static Observation observeBoundary()throws Exception{return compact(new int[]{-3,0,-2,-1},new int[]{-3,-1,0,0});}
static Observation compact(int[] a,int[] expected){var identity=a;var before=a.clone();TaskBinding.run(a);return new Observation("До="+Arrays.toString(before)+"; после="+Arrays.toString(a)+"; тот же массив="+(identity==a),identity==a&&Arrays.equals(expected,a));}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычное наблюдение с опубликованным требованием.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}
static int example(boolean normal){System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(!o.passed()){System.out.println("Статус: наблюдаемый результат не соответствует опубликованному случаю.");return 1;}System.out.println("Статус: наблюдаемое свойство соответствует; исходник, обязательные средства, выбранные правила и объяснение оценивает преподаватель.");return 0;}
catch(coursesupport.JavaProcess.UncaughtStudentException e){System.out.println("Статус: необработанная ошибка выполнения программы.");return 1;}
catch(coursesupport.JavaProcess.StudentTimeout e){System.out.println("Статус: время выполнения превышено.");return 1;}
catch(coursesupport.JavaProcess.StudentOutputLimit e){System.out.println("Статус: превышен объём вывода.");return 1;}
catch(UnsupportedOperationException e){System.out.println("Статус: заготовка или выбранная привязка ещё не подготовлена; предъявите реализацию преподавателю.");return 1;}
catch(AssertionError e){System.out.println("Статус: операция завершилась ошибкой выполнения AssertionError; проверьте выбранный вызов.");return 1;}
catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
catch(Error e){System.out.println("Статус: операция завершилась ошибкой выполнения. Тип: "+e.getClass().getSimpleName());return 1;}
catch(Exception e){System.out.println("Статус: операция завершилась ошибкой; проверьте реализацию и выбранную привязку. Тип: "+e.getClass().getSimpleName());return 1;}}
public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
