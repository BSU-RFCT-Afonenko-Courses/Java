/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Каждая цифра отдельно";
 private static final String NORMAL_WHAT="Сумма отдельных значений, включая соседние и0.";
 private static final String NORMAL_INPUT="Расчёт для a2b109.";
 private static final String NORMAL_EXPECTED="Точное целое12:2+1+0+9, буквы пропущены.";
 private static final String BOUNDARY_NAME="Нет цифр";
 private static final String BOUNDARY_WHAT="Нулевое значение суммы.";
 private static final String BOUNDARY_INPUT="Тот же расчёт для пустой строки.";
 private static final String BOUNDARY_EXPECTED="Точное целое0; числовой тип и формат не навязываются.";
 public static Observation observeNormal(){long r=TaskBinding.run("a2b109");return new Observation("результат="+r,r==12);}
 public static Observation observeBoundary(){long r=TaskBinding.run("");return new Observation("результат="+r,r==0);}
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
