import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.util.*;
/** Только два PUBLIC наблюдения выбранной реализации, не весь ручной договор. */
public class PublicChecks {
 private static final String NORMAL_NAME="Строки становятся столбцами";
 private static final String NORMAL_WHAT="Обе матрицы и обмен индексов.";
 private static final String NORMAL_INPUT="UTF-8 1 2 3 / 4 5 6 / 7 8 9, новый каталог и результат.";
 private static final String NORMAL_EXPECTED="Сохранены исходная и транспонированная матрицы 1 4 7 / 2 5 8 / 3 6 9 с различимыми подписями.";
 private static final String BOUNDARY_NAME="Матрица из одного элемента";
 private static final String BOUNDARY_WHAT="Минимальный допустимый размер.";
 private static final String BOUNDARY_INPUT="Одна строка -7, новый выход.";
 private static final String BOUNDARY_EXPECTED="Обе обозначенные матрицы равны [[-7]].";

 public record Observation(String actual,boolean passed){}
 public static Observation observeNormal()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"1 2 3\n4 5 6\n7 8 9\n",StandardCharsets.UTF_8);TaskBinding.run(in,out);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);if(Files.size(out)==0)return new Observation("Отчёт пуст: обязательные данные не сохранены.",false);if(!Files.isRegularFile(in))return new Observation("Вход удалён.",false);var r=TaskBinding.read(out);boolean passed=Arrays.deepEquals(new int[][]{{1,2,3},{4,5,6},{7,8,9}},r.source())&&Arrays.deepEquals(new int[][]{{1,4,7},{2,5,8},{3,6,9}},r.transposed())&&Files.readString(in,StandardCharsets.UTF_8).equals("1 2 3\n4 5 6\n7 8 9\n");return new Observation("исходная="+Arrays.deepToString(r.source())+"; транспонированная="+Arrays.deepToString(r.transposed())+"; файл="+CopySupport.contents(out),passed);}finally{CopySupport.cleanup(root);}}
 public static Observation observeBoundary()throws Exception{Path root=CopySupport.workspace();try{Path in=root.resolve("вход с пробелом.txt"),out=root.resolve("новый каталог/результат.txt");Files.writeString(in,"-7",StandardCharsets.UTF_8);TaskBinding.run(in,out);if(!Files.isRegularFile(out))return new Observation("Результат не создан.",false);if(Files.size(out)==0)return new Observation("Отчёт пуст: обязательные данные не сохранены.",false);var r=TaskBinding.read(out);boolean passed=Arrays.deepEquals(new int[][]{{-7}},r.source())&&Arrays.deepEquals(new int[][]{{-7}},r.transposed());return new Observation("исходная="+Arrays.deepToString(r.source())+"; транспонированная="+Arrays.deepToString(r.transposed()),passed);}finally{CopySupport.cleanup(root);}}
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
