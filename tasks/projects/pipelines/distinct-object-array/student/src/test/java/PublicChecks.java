import java.util.*;import java.math.BigDecimal;
public class PublicChecks {
static final String NORMAL_NAME="Отдельные равные объекты";
static final String NORMAL_WHAT="Первые реальные ссылки, новый массив и сохранность входа.";
static final String NORMAL_INPUT="A=(1,A),B=(2,B),C=(1,A); отдельные объекты[A,B,C]";
static final String NORMAL_EXPECTED="Новый массив[A,B] с исходными ссылками";
static final String BOUNDARY_NAME="Пустой массив объектов";
static final String BOUNDARY_WHAT="Другой массив для пустого результата.";
static final String BOUNDARY_INPUT="Пустой массив модели";
static final String BOUNDARY_EXPECTED="Новый пустой массив";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{Object a=TaskBinding.make(1,"A"),b=TaskBinding.make(2,"B"),c=TaskBinding.make(1,"A");Object[] input=TaskBinding.array(a,b,c);Object[] before=input.clone();var out=TaskBinding.run(input);boolean ok=out!=null&&out!=input&&out.length==2&&out[0]==a&&out[1]==b;for(int i=0;i<input.length;i++)ok &= input[i]==before[i];ok &= TaskBinding.id(a)==1&&"A".equals(TaskBinding.name(a))&&TaskBinding.id(b)==2&&"B".equals(TaskBinding.name(b))&&TaskBinding.id(c)==1&&"A".equals(TaskBinding.name(c));return new Observation("Длина="+(out==null?"null":out.length)+"; сохранены первые ссылки="+(out!=null&&out.length==2&&out[0]==a&&out[1]==b)+"; новый массив="+(out!=input),ok);}
public static Observation observeBoundary()throws Exception{Object[] input=TaskBinding.array();Object[] out=TaskBinding.run(input);return new Observation("Входная длина="+input.length+"; выходная="+(out==null?"null":out.length)+"; другой массив="+(out!=input),out!=null&&out!=input&&out.length==0&&input.length==0);}

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
