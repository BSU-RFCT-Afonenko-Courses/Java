import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Нулевая частота и равные частоты";
 private static final String NORMAL_WHAT="Все слова списка, устойчивый порядок по частоте.";
 private static final String NORMAL_INPUT="Зорка Вэнэра ўзыйшла над зямлёю; зорка, няма, зямлёю. Unicode-буквы, нижний регистр.";
 private static final String NORMAL_EXPECTED="няма:0; зорка:1; зямлёю:1.";
 private static final String BOUNDARY_NAME="Строка без совпадений";
 private static final String BOUNDARY_WHAT="Нули и порядок списка при равенстве.";
 private static final String BOUNDARY_INPUT="Та же строка; светлыя, згадкі; те же объявленные правила.";
 private static final String BOUNDARY_EXPECTED="светлыя:0; згадкі:0; строка не пропущена.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("результат.txt");Files.writeString(in,"Зорка Вэнэра ўзыйшла над зямлёю",StandardCharsets.UTF_8);TaskBinding.run(in,out,List.of("зорка","няма","зямлёю"));if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var rows=TaskBinding.read(out);boolean passed=rows.equals(List.of(List.of(new TaskBinding.WordCount("няма",0),new TaskBinding.WordCount("зорка",1),new TaskBinding.WordCount("зямлёю",1))));return new Observation("частоты по строкам="+rows+"; отчёт="+CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("результат.txt");Files.writeString(in,"Зорка Вэнэра ўзыйшла над зямлёю",StandardCharsets.UTF_8);TaskBinding.run(in,out,List.of("светлыя","згадкі"));if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var rows=TaskBinding.read(out);boolean passed=rows.equals(List.of(List.of(new TaskBinding.WordCount("светлыя",0),new TaskBinding.WordCount("згадкі",0))));return new Observation("частоты по строкам="+rows,passed);}finally{CopySupport.cleanup(root);}}
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
