import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Общий подсчёт двух строк";
 private static final String NORMAL_WHAT="Общие наборы букв и слов, повтор me в разных строках.";
 private static final String NORMAL_INPUT="Polały się łzy me czyste, rzęsiste, / Na me dzieciństwo sielskie, anielskie,; Unicode-буквы, нижний регистр, первое появление.";
 private static final String NORMAL_EXPECTED="11 вхождений слов, me=2; 60 букв; точные публичные пары и порядок из PUBLIC.md.";
 private static final String BOUNDARY_NAME="Пустой файл";
 private static final String BOUNDARY_WHAT="Отсутствие данных без выдуманных частот.";
 private static final String BOUNDARY_INPUT="Пустой UTF-8-файл; те же объявленные правила.";
 private static final String BOUNDARY_EXPECTED="Два пустых набора частот; оформление свободно.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("результат.txt");Files.writeString(in,"Polały się łzy me czyste, rzęsiste,\nNa me dzieciństwo sielskie, anielskie,\n",StandardCharsets.UTF_8);TaskBinding.run(in,out);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var r=TaskBinding.read(out);boolean passed=r.letters().entrySet().stream().map(e->e.getKey()+"="+e.getValue()).toList().equals(List.of("p"+"="+1,"o"+"="+2,"l"+"="+3,"a"+"="+3,"ł"+"="+2,"y"+"="+3,"s"+"="+8,"i"+"="+8,"ę"+"="+2,"z"+"="+4,"m"+"="+2,"e"+"="+9,"c"+"="+2,"t"+"="+3,"r"+"="+1,"n"+"="+2,"d"+"="+1,"ń"+"="+1,"w"+"="+1,"k"+"="+2))&&r.words().entrySet().stream().map(e->e.getKey()+"="+e.getValue()).toList().equals(List.of("polały"+"="+1,"się"+"="+1,"łzy"+"="+1,"me"+"="+2,"czyste"+"="+1,"rzęsiste"+"="+1,"na"+"="+1,"dzieciństwo"+"="+1,"sielskie"+"="+1,"anielskie"+"="+1));return new Observation("буквы="+r.letters()+"; слова="+r.words(),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("результат.txt");Files.writeString(in,"",StandardCharsets.UTF_8);TaskBinding.run(in,out);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var r=TaskBinding.read(out);boolean passed=r.letters().isEmpty()&&r.words().isEmpty();return new Observation("буквы="+r.letters()+"; слова="+r.words()+"; отчёт="+CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
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
