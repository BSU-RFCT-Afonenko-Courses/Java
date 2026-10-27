import java.util.*;import java.math.BigDecimal;
public class PublicChecks {
static final String NORMAL_NAME="Разные длины";
static final String NORMAL_WHAT="Значение более длинной строки.";
static final String NORMAL_INPUT="ab и XYZ";
static final String NORMAL_EXPECTED="XYZ";
static final String BOUNDARY_NAME="Равные длины";
static final String BOUNDARY_WHAT="Значение первой строки при ничьей; идентичность не требуется.";
static final String BOUNDARY_INPUT="ab и cd";
static final String BOUNDARY_EXPECTED="ab";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{String a=TaskBinding.run("ab","XYZ");return new Observation(a==null?"null":PublicOutput.escaped(a),"XYZ".equals(a));}
public static Observation observeBoundary()throws Exception{String a=TaskBinding.run("ab","cd");return new Observation(a==null?"null":PublicOutput.escaped(a),"ab".equals(a));}

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
