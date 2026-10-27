import coursesupport.JavaProcess;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.time.Duration;import java.util.*;
/** Наблюдения опубликованных запусков JAR из другого рабочего каталога. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure();}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure();}
 @SuppressWarnings("unchecked")private static <E extends Throwable,R>R forward(Throwable e)throws E{throw(E)e;}
 public static Observation observeNormal(){try{return observe(false);}catch(Throwable e){return PublicChecks.<RuntimeException,Observation>forward(e);}}
 public static Observation observeBoundary(){try{return observe(true);}catch(Throwable e){return PublicChecks.<RuntimeException,Observation>forward(e);}}
 private static String quote(String s){return "\""+s.replace("\\","\\\\").replace("\"","\\\"").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t")+"\"";}
 private static Observation observe(boolean boundary)throws Exception{
  Path d=Files.createDirectory(Path.of(System.getProperty("java.io.tmpdir"),"gradeapp-public-"+ProcessHandle.current().pid()+"-"+Long.toUnsignedString(System.nanoTime())));
  try{String input=boundary?"s1;Ada;90\n   \n s1 ;Other;80\n":"s2;Lin;70\ns3;Ann;90\ns10;Ada;90\n";Path file=d.resolve("students.txt");Files.writeString(file,input,StandardCharsets.UTF_8);Path jar=TaskBinding.jar().toAbsolutePath();
   var r=JavaProcess.run(jar,"-jar","",List.of(jar.toString(),file.toString(),"80"),d,Duration.ofSeconds(10),65536);
   String expectedOut=boundary?"":"s10;Ada;90"+System.lineSeparator()+"s3;Ann;90"+System.lineSeparator();
   String expectedErr=boundary?"ошибка данных: строка 3: повтор кода s1"+System.lineSeparator():"";
   boolean same=Files.isRegularFile(file)&&Files.readString(file,StandardCharsets.UTF_8).equals(input),ok=r.exitCode()==(boundary?3:0)&&r.stdout().equals(expectedOut)&&r.stderr().equals(expectedErr)&&same;
   return new Observation("код="+r.exitCode()+"; stdout="+(r.stdout().isEmpty()?"Пустой стандартный вывод.":quote(r.stdout()))+"; stderr="+(r.stderr().equals(expectedErr)?quote(r.stderr()):"получена другая диагностика ("+r.stderr().getBytes(StandardCharsets.UTF_8).length+" байт); текст не раскрывается")+"; вход сохранён="+same+"; рабочий каталог отличается от каталога JAR="+!d.equals(jar.getParent()),ok);
  }finally{try(var files=Files.walk(d)){for(Path p:files.sorted(Comparator.reverseOrder()).toList())Files.deleteIfExists(p);}}
 }
 public static void main(String[]args){try{var a=observeNormal();System.out.println("PUBLIC normal | Отбор и строковая сортировка кодов | ВХОД: s2/Lin/70, s3/Ann/90, s10/Ada/90; порог 80 | ОЖИДАЕТСЯ: код 0, строки s10, s3 с последним переводом строки, пустой stderr | ФАКТИЧЕСКИ: "+a.actual());checkNormal(a);System.out.println("СТАТУС: открытое наблюдение подтверждено.");var b=observeBoundary();System.out.println("PUBLIC boundary | Поздний повтор после пустой строки | ВХОД: s1;Ada;90, пустая строка, s1;Other;80; порог 80 | ОЖИДАЕТСЯ: код 3, пустой stdout, строка 3: повтор кода s1 | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытое наблюдение подтверждено; техника, сборка и объяснение предъявляются преподавателю.");}catch(JavaProcess.StudentTimeout e){System.err.println("СТАТУС: JAR не завершился вовремя.");System.exit(2);}catch(JavaProcess.StudentOutputLimit e){System.err.println("СТАТУС: превышен объём вывода JAR.");System.exit(2);}catch(CheckFailure e){System.err.println("СТАТУС: результат JAR не соответствует открытому ожиданию.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: наблюдение не завершено; проверьте JAR, запуск и привязку.");System.exit(2);}}
}
