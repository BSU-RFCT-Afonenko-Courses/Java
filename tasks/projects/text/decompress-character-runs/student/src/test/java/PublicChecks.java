/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Счётчики, одиночная буква и регистр";
 private static final String NORMAL_WHAT="Несколько корректных серий.";
 private static final String NORMAL_INPUT="Распаковать a2B3c.";
 private static final String NORMAL_EXPECTED="СтрокаaaBBBc; B прописная, последняяc одна.";
 private static final String BOUNDARY_NAME="Многозначная длина и пустая запись";
 private static final String BOUNDARY_WHAT="Полный счётчик и отсутствие серий.";
 private static final String BOUNDARY_INPUT="Отдельно a12b и пустая строка.";
 private static final String BOUNDARY_EXPECTED="Двенадцатьa иb: aaaaaaaaaaaab; отдельно пустая строка. Повреждённых записей нет.";
 public static Observation observeNormal(){String r=TaskBinding.run("a2B3c");return new Observation("результат="+shown(r),java.util.Objects.equals("aaBBBc",r));}
 public static Observation observeBoundary(){String a=TaskBinding.run("a12b"),b=TaskBinding.run("");return new Observation("a12b: "+shown(a)+"; пустой вход: "+shown(b),java.util.Objects.equals("aaaaaaaaaaaab",a)&&java.util.Objects.equals("",b));}
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
