import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Одинаковые файлы";
 private static final String NORMAL_WHAT="Соответствующие байты и сохранность входов.";
 private static final String NORMAL_INPUT="Два UTF-8-файла Java text без LF; CompFiles получает два пути.";
 private static final String NORMAL_EXPECTED="Сообщено совпадение; входы неизменны.";
 private static final String BOUNDARY_NAME="Одинаковое начало, разная длина";
 private static final String BOUNDARY_WHAT="EOF одного файла не заменяет равенство.";
 private static final String BOUNDARY_INPUT="Java и Java text без LF; два пути.";
 private static final String BOUNDARY_EXPECTED="Сообщено различие, не файловый отказ; входы неизменны.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path one=root.resolve("первый.txt"),two=root.resolve("второй.txt");byte[] a="Java text".getBytes(StandardCharsets.UTF_8),b="Java text".getBytes(StandardCharsets.UTF_8);Files.write(one,a);Files.write(two,b);var r=coursesupport.JavaProcess.runCheckingUncaught(TaskBinding.entry(),"",one.toString(),two.toString());boolean kept=Files.isRegularFile(one)&&Files.isRegularFile(two)&&Arrays.equals(a,Files.readAllBytes(one))&&Arrays.equals(b,Files.readAllBytes(two));return new Observation("stdout="+CopySupport.quote(r.stdout())+"; stderr="+CopySupport.quote(r.stderr())+"; exit="+r.exitCode()+"; входы сохранены="+kept,kept&&TaskBinding.equalityPrinted(r,true));}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path one=root.resolve("первый.txt"),two=root.resolve("второй.txt");byte[] a="Java".getBytes(StandardCharsets.UTF_8),b="Java text".getBytes(StandardCharsets.UTF_8);Files.write(one,a);Files.write(two,b);var r=coursesupport.JavaProcess.runCheckingUncaught(TaskBinding.entry(),"",one.toString(),two.toString());boolean kept=Files.isRegularFile(one)&&Files.isRegularFile(two)&&Arrays.equals(a,Files.readAllBytes(one))&&Arrays.equals(b,Files.readAllBytes(two));return new Observation("stdout="+CopySupport.quote(r.stdout())+"; stderr="+CopySupport.quote(r.stderr())+"; exit="+r.exitCode()+"; входы сохранены="+kept,kept&&TaskBinding.equalityPrinted(r,false));}finally{CopySupport.cleanup(root);}}
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
