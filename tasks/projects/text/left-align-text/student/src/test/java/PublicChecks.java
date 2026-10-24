/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Заполнение строки до ширины";
 private static final String NORMAL_WHAT="Порядок слов и перенос целиком.";
 private static final String NORMAL_INPUT="one   two с LF передthree; width7.";
 private static final String NORMAL_EXPECTED="Строки one two и three; без краевых пробелов, первая длины7.";
 private static final String BOUNDARY_NAME="Только разделители";
 private static final String BOUNDARY_WHAT="Отсутствие слов при положительной ширине.";
 private static final String BOUNDARY_INPUT="Пробел, TAB, LF, пробел; width1.";
 private static final String BOUNDARY_EXPECTED="Пустой текст без пустых строк результата; другой формат не навязывается.";
 static String lines(java.util.List<String> values){if(values==null)return "null";if(values.isEmpty())return "Пустой список строк.";var r=new StringBuilder();for(String s:values)r.append(shown(s)).append("; ");return r.toString();}
 public static Observation observeNormal(){var r=TaskBinding.run("one   two\nthree",7);return new Observation("строки="+lines(r),java.util.Objects.equals(java.util.List.of("one two","three"),r));}
 public static Observation observeBoundary(){var r=TaskBinding.run(" \t\n ",1);return new Observation("строки="+lines(r),java.util.Objects.equals(java.util.List.of(),r));}
 public record Observation(String actual,boolean passed) {}
 static String shown(String s){return s==null?"null":s.isEmpty()?"Пустая строка.":PublicOutput.escaped(s);}
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте значение с опубликованным обычным случаем.");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте состояние с опубликованной границей.");}
 static int example(boolean normal){
  System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));
  System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));
  System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));
  System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
  try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует; исходник, приём и объяснение оценивает преподаватель.");return 0;}
  catch(UnsupportedOperationException e){System.out.println("Статус: реализация примера ещё не завершена; проверьте исходник и привязку.");return 1;}
  catch(AssertionError e){System.out.println("Статус: неверный наблюдаемый результат; сопоставьте с опубликованным случаем.");return 1;}
  catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
  catch(RuntimeException e){System.out.println("Статус: ошибка выполнения выбранного примера; требуется разбор реализации и привязки.");return 1;}
 }
 public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
