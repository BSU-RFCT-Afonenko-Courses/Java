/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Единственный самый длинный фрагмент";
 private static final String NORMAL_WHAT="Непрерывный фрагмент между буквами.";
 private static final String NORMAL_INPUT="Найти фрагмент в a12!б3.";
 private static final String NORMAL_EXPECTED="Строка12!, длина3; поздняя3 не склеивается через букву.";
 private static final String BOUNDARY_NAME="Равные длины";
 private static final String BOUNDARY_WHAT="Первый фрагмент при равенстве.";
 private static final String BOUNDARY_INPUT="Найти фрагмент в a12!б34?.";
 private static final String BOUNDARY_EXPECTED="Строка12!, а не поздняя34?.";
 public static Observation observeNormal(){String r=TaskBinding.run("a12!б3");return new Observation("результат="+shown(r),java.util.Objects.equals("12!",r));}
 public static Observation observeBoundary(){String r=TaskBinding.run("a12!б34?");return new Observation("результат="+shown(r),java.util.Objects.equals("12!",r));}
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
