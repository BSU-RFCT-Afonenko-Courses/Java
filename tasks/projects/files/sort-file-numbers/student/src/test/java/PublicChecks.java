import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Отрицательные числа и повтор";
 private static final String NORMAL_WHAT="Порядок и кратность int.";
 private static final String NORMAL_INPUT="Файл 7 -2 7 0 3 -5; новый каталог и результат.";
 private static final String NORMAL_EXPECTED="-5 -2 0 3 7 7, ровно один пробел, необязательный конечный перевод строки.";
 private static final String BOUNDARY_NAME="Границы int";
 private static final String BOUNDARY_WHAT="Порядок крайних допустимых значений.";
 private static final String BOUNDARY_INPUT="2147483647\\t-2147483648\\n2147483647; новый выход.";
 private static final String BOUNDARY_EXPECTED="-2147483648 2147483647 2147483647; конечный перевод строки необязателен.";
static boolean numericResult(String actual,String expected){return actual.equals(expected)||actual.equals(expected+"\n")||actual.equals(expected+"\r\n")||actual.equals(expected+"\r");}

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"7 -2 7 0 3 -5",StandardCharsets.UTF_8);TaskBinding.run(in,out);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);String actual=Files.readString(out,StandardCharsets.UTF_8);boolean passed=numericResult(actual,"-5 -2 0 3 7 7");return new Observation(CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"2147483647\t-2147483648\n2147483647",StandardCharsets.UTF_8);TaskBinding.run(in,out);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);String actual=Files.readString(out,StandardCharsets.UTF_8);boolean passed=numericResult(actual,"-2147483648 2147483647 2147483647");return new Observation(CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычный результат с опубликованным договором.");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте результат с опубликованной границей.");}
 static int example(boolean normal){
  System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
  try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует; средства, исходник, собственные данные и объяснение оценивает преподаватель.");return 0;}
  catch(coursesupport.JavaProcess.UncaughtStudentException e){System.out.println("Статус: необработанная ошибка выполнения программы; проверьте её запуск.");return 1;}
  catch(coursesupport.JavaProcess.StudentTimeout e){System.out.println("Статус: время выполнения превышено; проверьте завершение операции.");return 1;}
  catch(coursesupport.JavaProcess.StudentOutputLimit e){System.out.println("Статус: превышен объём вывода; проверьте цикл печати.");return 1;}
  catch(UnsupportedOperationException e){System.out.println("Статус: выбранная заготовка ещё не завершена; проверьте реализацию и привязку.");return 1;}
  catch(AssertionError e){System.out.println("Статус: неверный наблюдаемый результат; сопоставьте с опубликованным случаем.");return 1;}
  catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
  catch(Exception e){System.out.println("Статус: операция завершилась ошибкой; проверьте выбранные пути, реализацию и подготовленную привязку. Тип: "+e.getClass().getSimpleName());return 1;}
 }
 public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
