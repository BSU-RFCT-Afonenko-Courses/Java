import java.io.IOException;
import coursesupport.JavaProcess;
import coursesupport.JavaProcess.StudentOutputLimit;
import coursesupport.JavaProcess.StudentTimeout;
import coursesupport.JavaProcess.UncaughtStudentException;
import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.LinkageError;
import java.lang.Math;
import java.lang.String;
import java.lang.System;
import java.lang.UnsupportedOperationException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Суффиксы строк и слов";
 private static final String NORMAL_WHAT="Последние строки, затем последние слова.";
 private static final String NORMAL_INPUT="one two three / four five / six seven eight nine; m=2,n=2.";
 private static final String NORMAL_EXPECTED="Две строки four five / eight nine.";
 private static final String BOUNDARY_NAME="Ноль слов в двух выбранных строках";
 private static final String BOUNDARY_WHAT="Пустые физические строки отличаются от пустого файла.";
 private static final String BOUNDARY_INPUT="Тот же вход; m=0,n=2.";
 private static final String BOUNDARY_EXPECTED="Ровно две пустые физические строки.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"one two three\nfour five\nsix seven eight nine\n",StandardCharsets.UTF_8);TaskBinding.run(in,out,2,2);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var actual=Files.readAllLines(out,StandardCharsets.UTF_8);boolean passed=actual.equals(List.of("four five","eight nine"));return new Observation(CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"one two three\nfour five\nsix seven eight nine\n",StandardCharsets.UTF_8);TaskBinding.run(in,out,0,2);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var actual=Files.readAllLines(out,StandardCharsets.UTF_8);boolean passed=actual.equals(List.of("",""));return new Observation(CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Ожидается: "+NORMAL_EXPECTED+"\nПолучено: "+o.actual());}
 public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Ожидается: "+BOUNDARY_EXPECTED+"\nПолучено: "+o.actual());}
 static int example(boolean normal){
  System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
  try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует; средства, исходник, собственные данные и объяснение оценивает преподаватель.");return 0;}
  catch(UncaughtStudentException e){System.out.println("Статус: необработанная ошибка выполнения программы; проверьте её запуск.");return 1;}
  catch(StudentTimeout e){System.out.println("Статус: время выполнения превышено; проверьте завершение операции.");return 1;}
  catch(StudentOutputLimit e){System.out.println("Статус: превышен объём вывода; проверьте цикл печати.");return 1;}
  catch(UnsupportedOperationException e){System.out.println("Статус: выбранная заготовка ещё не завершена; проверьте реализацию и привязку.");return 1;}
  catch(AssertionError e){System.out.println("Статус: неверный наблюдаемый результат; сопоставьте с опубликованным случаем.");return 1;}
  catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
  catch(IOException e){System.out.println("Статус: наблюдение не завершено из-за ошибки запуска или файлов проверки; требуется ручная проверка, оценки нет. Тип: "+e.getClass().getSimpleName());return 2;}
  catch(Exception e){System.out.println("Статус: операция завершилась ошибкой; проверьте выбранные пути, реализацию и подготовленную привязку. Тип: "+e.getClass().getSimpleName());return 1;}
 }
 public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
