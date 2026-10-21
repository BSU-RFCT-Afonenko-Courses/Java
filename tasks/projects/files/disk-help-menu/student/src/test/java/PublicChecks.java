import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Два последовательных запроса";
 private static final String NORMAL_WHAT="Поиск тем, порядок текста, доступность общего ввода.";
 private static final String NORMAL_INPUT="Открытый helpfile.txt; выбор if, затем for через getSelection().";
 private static final String NORMAL_EXPECTED="Оба helpOn дают true, напечатаны соответствующие разделы; System.in не закрыт.";
 private static final String BOUNDARY_NAME="Неизвестная тема и ошибка чтения";
 private static final String BOUNDARY_WHAT="Разные причины false.";
 private static final String BOUNDARY_INPUT="Существующий файл: If; отсутствующий файл: if.";
 private static final String BOUNDARY_EXPECTED="Оба false; сообщения различают отсутствие темы и файловый отказ.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path file=root.resolve("helpfile.txt");Files.write(file,PublicFiles.help());var r=TaskBinding.sequence(file);String one="if (условие) оператор;",two="for (инициализация; условие; шаг) оператор;";boolean passed=r.choices().equals(List.of("if","for"))&&r.found().equals(List.of(true,true))&&!r.closed()&&r.text().contains(one)&&r.text().contains(two)&&r.text().indexOf(one)<r.text().indexOf(two);return new Observation("выборы="+r.choices()+"; признаки="+r.found()+"; ввод закрыт="+r.closed()+"; напечатано="+CopySupport.quote(r.text()),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path file=root.resolve("helpfile.txt");Files.write(file,PublicFiles.help());var unknown=TaskBinding.lookup(file,"If");var missing=TaskBinding.lookup(root.resolve("отсутствует.txt"),"if");boolean passed=!unknown.found()&&!missing.found()&&TaskBinding.absenceShown(unknown.text())&&TaskBinding.fileFailureShown(missing.text());return new Observation("неизвестная тема: найдено="+unknown.found()+", сообщение="+CopySupport.quote(unknown.text())+"; отсутствующий файл: найдено="+missing.found()+", сообщение="+CopySupport.quote(missing.text()),passed);}finally{CopySupport.cleanup(root);}}
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
