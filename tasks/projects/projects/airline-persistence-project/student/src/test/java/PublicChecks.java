import java.util.*;import java.nio.file.*;
/** Только два опубликованных наблюдения; API заготовки заменяется обычной привязкой. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
static Map<String,TaskBinding.Plane> byId(List<TaskBinding.Plane> xs){var out=new HashMap<String,TaskBinding.Plane>();for(var x:xs)if(out.put(x.id(),x)!=null)throw new AssertionError("Идентификаторы повторены");return out;}
 static boolean qualifying(Object a,int lo,int hi){var result=TaskBinding.find(a,lo,hi);var all=byId(TaskBinding.planes(a));boolean exists=all.values().stream().anyMatch(p->p.fuel()>=lo&&p.fuel()<=hi);return (!exists&&result.isEmpty())||(exists&&!result.isEmpty()&&result.stream().allMatch(p->p.equals(all.get(p.id()))&&p.fuel()>=lo&&p.fuel()<=hi));}

 public static Observation observeNormal()throws Exception{Object a=TaskBinding.fixture();var before=byId(TaskBinding.planes(a));var sorted=TaskBinding.sorted(a);boolean order=true;for(int j=1;j<sorted.size();j++){order&=TaskBinding.sortDirection().equals("ascending")?sorted.get(j-1).range()<=sorted.get(j).range():sorted.get(j-1).range()>=sorted.get(j).range();}boolean initial=TaskBinding.capacity(a)==232&&TaskBinding.payload(a)==38&&before.size()==3&&before.equals(byId(sorted))&&order&&qualifying(a,1500,2400);Path dir=CopySupport.workspace();try{Path file=dir.resolve("сохранённый парк/model.bin");TaskBinding.save(a,file);if(!Files.isRegularFile(file)||Files.size(file)==0)return new Observation("Сохранённая модель не создана.",false);Object loaded=TaskBinding.load(file);boolean restored=before.equals(byId(TaskBinding.planes(loaded)));TaskBinding.update(file,100);Object updated=TaskBinding.load(file);var after=byId(TaskBinding.planes(updated));boolean changed=after.size()==3&&Objects.equals(before.get("1"),after.get("1"))&&Objects.equals(before.get("2"),after.get("2"))&&after.get("3")!=null&&after.get("3").seats()==100;var original=before.get("3");var value=after.get("3");changed&=value!=null&&value.kind().equals(original.kind())&&value.payload()==original.payload()&&value.range()==original.range()&&value.fuel()==original.fuel();return new Observation("места="+TaskBinding.capacity(a)+"; тонны="+TaskBinding.payload(a)+"; порядок="+sorted+"; загружено="+TaskBinding.planes(loaded)+"; после обновления="+after,initial&&restored&&changed);}finally{CopySupport.cleanup(dir);}}
 public static Observation observeBoundary()throws Exception{Object a=TaskBinding.fixture();boolean ok=qualifying(a,1500,1500)&&qualifying(a,2400,2400)&&qualifying(a,4000,4100);return new Observation("[1500,1500]="+TaskBinding.find(a,1500,1500)+"; [2400,2400]="+TaskBinding.find(a,2400,2400)+"; [4000,4100]="+TaskBinding.find(a,4000,4100),ok); }
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычный результат с условием.");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте граничный результат с условием.");}
 static int example(boolean normal){
  System.out.println("Сценарий: "+(normal?"Парк до и после сохранения":"Включённые границы поиска"));
  System.out.println("Вход и действие: "+(normal?"Три опубликованных самолёта; суммы, порядок, поиск, запись, загрузка, обновление мест третьего до100 и новая загрузка.":"Исходный парк независимо: диапазоны [1500,1500], [2400,2400], [4000,4100]."));
  System.out.println("Ожидаемый результат: "+(normal?"232 места,38т; выбранный порядок, сохранённые виды/параметры и обновление.":"На концах есть подходящий самолёт; в последнем диапазоне совпадений нет."));
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
