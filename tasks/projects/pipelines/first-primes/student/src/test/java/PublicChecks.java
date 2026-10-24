import java.util.*;import java.math.BigDecimal;
public class PublicChecks {
static final String NORMAL_NAME="Первые пять простых";
static final String NORMAL_WHAT="Реальный вывод первых пяти простых.";
static final String NORMAL_INPUT="N=5";
static final String NORMAL_EXPECTED="2,3,5,7,11; пробелы или переводы строк";
static final String BOUNDARY_NAME="Ноль результатов";
static final String BOUNDARY_WHAT="Реальный пустой вывод последовательности.";
static final String BOUNDARY_INPUT="N=0";
static final String BOUNDARY_EXPECTED="Пустой вывод последовательности";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String a=TaskBinding.run(5);return primeObservation(a,new int[]{2,3,5,7,11});}
public static Observation observeBoundary()throws Exception{String a=TaskBinding.run(0);return primeObservation(a,new int[0]);}
static Observation primeObservation(String actual,int[] expected){boolean passed=false;if(actual!=null){try{passed=Arrays.equals(expected,numbers(actual));}catch(AssertionError invalidObservedText){passed=false;}}return new Observation(actual==null?"null":PublicOutput.escaped(actual),passed);}public static int[] numbers(String s){double[] values=coursesupport.OutputNumbers.finiteNumbers(s);int[] numbers=new int[values.length];for(int i=0;i<values.length;i++){if(values[i]!=(int)values[i])throw new AssertionError("Ожидалось целое простое число");numbers[i]=(int)values[i];}return numbers;}
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
