import java.util.*;import java.nio.file.*;
/** Только два опубликованных наблюдения; API заготовки заменяется обычной привязкой. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
static Observation inspect(String input,Map<String,Integer> expected,int length){Object r=TaskBinding.encode(input);var f=TaskBinding.frequencies(r);var c=TaskBinding.codes(r);var bits=TaskBinding.bits(r);boolean ok=f!=null&&c!=null&&bits!=null&&expected.equals(f)&&expected.keySet().equals(c.keySet());if(!ok)return new Observation("частоты="+f+";коды="+c+";биты="+bits,false);StringBuilder actualBits=new StringBuilder();int weighted=0;for(String word:expected.keySet()){String code=c.get(word);if(code==null||!code.matches("[01]+"))ok=false;else{weighted+=expected.get(word)*code.length();for(String other:expected.keySet())if(!word.equals(other)&&c.get(other)!=null&&c.get(other).startsWith(code))ok=false;}}if(!input.isEmpty())for(String word:input.split(" +"))actualBits.append(c.get(word));ok&=bits.matches("[01]*")&&bits.length()==length&&weighted==length&&bits.equals(actualBits.toString());return new Observation("частоты="+f+"; коды="+c+"; биты="+bits+"; длина="+bits.length(),ok);}

 public static Observation observeNormal()throws Exception{return inspect("one two two three three three",Map.of("one",1,"two",2,"three",3),9);}
 public static Observation observeBoundary()throws Exception{var empty=inspect("",Map.of(),0);var one=inspect("one one one",Map.of("one",3),3);var equal=inspect("red blue green",Map.of("red",1,"blue",1,"green",1),5);Object a=TaskBinding.encode("red blue green"),b=TaskBinding.encode("red blue green");Object single=TaskBinding.encode("one one one");boolean same=TaskBinding.codes(a).equals(TaskBinding.codes(b))&&TaskBinding.bits(a).equals(TaskBinding.bits(b));return new Observation(empty.actual()+"; "+one.actual()+"; "+equal.actual()+"; повтор="+same,empty.passed()&&one.passed()&&equal.passed()&&same&&TaskBinding.codes(single).equals(Map.of("one","0"))&&TaskBinding.bits(single).equals("000")); }
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычный результат с условием.");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте граничный результат с условием.");}
 static int example(boolean normal){
  System.out.println("Сценарий: "+(normal?"Частоты, коды и девять битов":"Пустой, однословный и равновесный вход"));
  System.out.println("Вход и действие: "+(normal?"one two two three three three;построить таблицу и биты.":"Пустой текст;one one one;red blue green и повтор последнего."));
  System.out.println("Ожидаемый результат: "+(normal?"Частоты1/2/3,двоичные префиксные коды,биты в исходном порядке,взвешенная длина9.":"Пустые таблица/биты;код0 и000;равныечастоты с оптимальной длиной5 и воспроизводимым выбранным правилом."));
  try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует; исходник, обязательные средства, политика и объяснение оценивает преподаватель.");return 0;}
  catch(coursesupport.JavaProcess.UncaughtStudentException e){System.out.println("Статус: необработанная ошибка программы.");return 1;}
  catch(coursesupport.JavaProcess.StudentTimeout e){System.out.println("Статус: время выполнения превышено.");return 1;}
  catch(coursesupport.JavaProcess.StudentOutputLimit e){System.out.println("Статус: превышен объём вывода.");return 1;}
  catch(UnsupportedOperationException e){System.out.println("Статус: выбранная заготовка ещё не завершена.");return 1;}
  catch(AssertionError e){System.out.println("Статус: наблюдаемый результат не соответствует опубликованному случаю.");return 1;}
  catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; ручная проверка, оценки нет.");return 2;}
  catch(Exception e){System.out.println("Статус: ошибка операции; проверьте реализацию, файлы и подготовленную привязку. Тип: "+e.getClass().getSimpleName());return 1;}
 }
 public static void main(String[]args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
