import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Два полных совпадения";
 private static final String NORMAL_WHAT="Все строки и номера совпадений.";
 private static final String NORMAL_INPUT="alpha / beta / alpha; поиск alpha одним аргументом.";
 private static final String NORMAL_EXPECTED="Сохранены все три строки и отдельный список 1,3.";
 private static final String BOUNDARY_NAME="Пустая строка поиска";
 private static final String BOUNDARY_WHAT="Пустая физическая строка и краевой пробел.";
 private static final String BOUNDARY_INPUT="alpha\\n\\n alpha\\n; пустой поиск одним аргументом.";
 private static final String BOUNDARY_EXPECTED="Все три строки сохранены; совпал только номер 2.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"alpha\nbeta\nalpha\n",StandardCharsets.UTF_8);TaskBinding.run(in,out,"alpha");if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);if(Files.size(out)==0)return new Observation("Отчёт пуст: обязательные данные не сохранены.",false);var r=TaskBinding.read(out);boolean passed=r.lines().equals(List.of("alpha","beta","alpha"))&&r.numbers().equals(List.of(1,3));return new Observation("строки="+r.lines().stream().map(CopySupport::quote).toList()+"; номера="+r.numbers(),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"alpha\n\n alpha\n",StandardCharsets.UTF_8);TaskBinding.run(in,out,"");if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);if(Files.size(out)==0)return new Observation("Отчёт пуст: обязательные данные не сохранены.",false);var r=TaskBinding.read(out);boolean passed=r.lines().equals(List.of("alpha",""," alpha"))&&r.numbers().equals(List.of(2));return new Observation("строки="+r.lines().stream().map(CopySupport::quote).toList()+"; номера="+r.numbers(),passed);}finally{CopySupport.cleanup(root);}}
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
