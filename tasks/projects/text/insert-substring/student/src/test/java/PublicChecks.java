/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Вставка между частями текста";
 private static final String NORMAL_WHAT="Вставка после указанного числа исходных char.";
 private static final String NORMAL_INPUT="Вызвать выбранный метод: Java, !, k=2.";
 private static final String NORMAL_EXPECTED="Возвращено Ja!va; исходная строка Java сохранена.";
 private static final String BOUNDARY_NAME="Края и пустой текст";
 private static final String BOUNDARY_WHAT="Включённые границы позиции.";
 private static final String BOUNDARY_INPUT="Отдельно Java+! при k0 и4, пустой текст+код при k0.";
 private static final String BOUNDARY_EXPECTED="!Java; Java!; код. Исходные значения сохранены; неверной позиции нет.";
 public static Observation observeNormal(){String text=new String("Java");String result=TaskBinding.run(text,"!",2);return new Observation("исходник="+shown(text)+"; результат="+shown(result),java.util.Objects.equals(result,"Ja!va")&&text.equals("Java"));}
 public static Observation observeBoundary(){String a=new String("Java"),b=new String("Java"),c=new String("");String x=TaskBinding.run(a,"!",0),y=TaskBinding.run(b,"!",4),z=TaskBinding.run(c,"код",0);return new Observation("k0="+shown(x)+"; k4="+shown(y)+"; пустой="+shown(z)+"; исходники="+shown(a)+", "+shown(b)+", "+shown(c),java.util.Objects.equals("!Java",x)&&java.util.Objects.equals("Java!",y)&&java.util.Objects.equals("код",z)&&a.equals("Java")&&b.equals("Java")&&c.isEmpty());}
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
