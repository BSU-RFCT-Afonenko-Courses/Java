/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Все требования выполнены";
 private static final String NORMAL_WHAT="Совместное выполнение учебного формата.";
 private static final String NORMAL_INPUT="Проверить Abcdefghi1 выбранным способом.";
 private static final String NORMAL_EXPECTED="Булево true:10char, оба латинских регистра, цифра, допустимый состав.";
 private static final String BOUNDARY_NAME="На один символ короче";
 private static final String BOUNDARY_WHAT="Девять символов при остальных выполненных условиях.";
 private static final String BOUNDARY_INPUT="Проверить Abcdefgh1.";
 private static final String BOUNDARY_EXPECTED="Булево false из-за длины. Остальные ветви проверяются отдельно.";
 public static Observation observeNormal(){boolean r=TaskBinding.run("Abcdefghi1");return new Observation("результат="+r,r);}
 public static Observation observeBoundary(){boolean r=TaskBinding.run("Abcdefgh1");return new Observation("результат="+r,!r);}
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
