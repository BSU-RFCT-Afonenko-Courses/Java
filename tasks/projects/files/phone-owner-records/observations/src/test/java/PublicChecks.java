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
 private static final String NORMAL_NAME="Объединение двух префиксов";
 private static final String NORMAL_WHAT="Отбор и исходный порядок.";
 private static final String NORMAL_INPUT="291234567;Анна / 331234567;Борис / 441234567;Вера; k=29,j=33.";
 private static final String NORMAL_EXPECTED="Первые две записи в исходном формате и порядке.";
 private static final String BOUNDARY_NAME="Одинаковые префиксы и повтор записи";
 private static final String BOUNDARY_WHAT="Повтор входа отличается от двойного совпадения.";
 private static final String BOUNDARY_INPUT="291234567;Анна дважды / 441234567;Вера; k=29,j=29.";
 private static final String BOUNDARY_EXPECTED="Ровно два вхождения 291234567;Анна.";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");TaskBinding.save(List.of("291234567;Анна","331234567;Борис","441234567;Вера"),in);TaskBinding.run(in,out,"29","33");if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var actual=Files.readAllLines(out,StandardCharsets.UTF_8);boolean passed=actual.equals(List.of("291234567;Анна","331234567;Борис"));return new Observation(CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"291234567;Анна\n291234567;Анна\n441234567;Вера\n",StandardCharsets.UTF_8);TaskBinding.run(in,out,"29","29");if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);var actual=Files.readAllLines(out,StandardCharsets.UTF_8);boolean passed=actual.equals(List.of("291234567;Анна","291234567;Анна"));return new Observation(CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
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
