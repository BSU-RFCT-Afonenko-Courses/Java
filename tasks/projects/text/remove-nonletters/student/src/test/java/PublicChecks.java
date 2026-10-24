/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Удаление без слияния слов";
 private static final String NORMAL_WHAT="Буквы, исходные U0020 и свобода добавленных пробелов.";
 private static final String NORMAL_INPUT="Удалить небуквенные из кот,дом  2я!; перед2 два U0020.";
 private static final String NORMAL_EXPECTED="Слова кот, дом, я в прежнем регистре/порядке; исходные два пробела сохранены; между кот и дом ≥1. Допустимы кот дом  я и кот   дом  я.";
 private static final String BOUNDARY_NAME="Пустой текст";
 private static final String BOUNDARY_WHAT="Нет символов и слов для преобразования.";
 private static final String BOUNDARY_INPUT="Та же операция на пустой строке.";
 private static final String BOUNDARY_EXPECTED="Пустая строка; без нового протокола ввода.";
 static java.util.List<String> words(String s){var r=new java.util.ArrayList<String>();var m=java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(s);while(m.find())r.add(m.group());return r;}
 static java.util.List<Integer> spaces(String s){var r=new java.util.ArrayList<Integer>();var m=java.util.regex.Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(s);int end=0;while(m.find()){int count=0;for(int i=end;i<m.start();i++)if(s.charAt(i)==' ')count++;r.add(count);end=m.end();}int count=0;for(int i=end;i<s.length();i++)if(s.charAt(i)==' ')count++;r.add(count);return r;}
 static boolean valid(String source,String result){if(result==null)return false;if(source.isEmpty())return result.isEmpty();if(!result.matches("[A-Za-zА-Яа-яЁё ]*"))return false;if(!words(source).equals(words(result)))return false;var a=spaces(source);var b=spaces(result);if(a.size()!=b.size())return false;for(int i=0;i<a.size();i++)if(b.get(i)<a.get(i))return false;return true;}
 public static Observation observeNormal(){String text="кот,дом  2я!",r=TaskBinding.run(text);return new Observation("результат="+shown(r),valid(text,r));}
 public static Observation observeBoundary(){String r=TaskBinding.run("");return new Observation("результат="+shown(r),valid("",r));}
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
