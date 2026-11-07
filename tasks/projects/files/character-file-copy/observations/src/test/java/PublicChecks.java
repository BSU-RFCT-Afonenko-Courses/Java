import java.lang.AssertionError;
import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import coursesupport.JavaProcess;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.time.Duration;import java.util.*;
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 private static final class CheckFailure extends AssertionError{
  @java.io.Serial
  private static final long serialVersionUID = 1L;
  CheckFailure(String expected,String actual){super("Ожидается: "+expected+"\nПолучено: "+actual);}}
 public static void checkNormal(Observation o){if(!o.passed())throw new CheckFailure("Выход: "+CopySupport.escaped("A-B\tC\u00A0D\r\nE-F".getBytes(StandardCharsets.UTF_8))+"; вход прежний",o.actual());}
 public static void checkBoundary(Observation o){if(!o.passed())throw new CheckFailure("Прежний файл A B; сообщена причина отказа",o.actual());}
 public static Observation observeNormal()throws Exception{return normal();}
 public static Observation observeBoundary()throws Exception{return boundary();}
 public static void main(String[]args){try{var n=observeNormal();System.out.println("PUBLIC normal | Пробел, TAB, NBSP, CRLF без последнего LF; ожидается только замена U+0020 | ВХОД: A B\\tC\\u00A0D\\r\\nE F; выход отсутствует, после последнего слова нет перевода строки | ДЕЙСТВИЕ: запустить с путями входа и выхода | ОЖИДАЕТСЯ: A-B\\tC\\u00A0D\\r\\nE-F; вход прежний | ФАКТИЧЕСКИ: "+n.actual());checkNormal(n);System.out.println("СТАТУС: открытое состояние подтверждено; техника, диагностика и объяснение предъявляются преподавателю.");var b=observeBoundary();System.out.println("PUBLIC boundary | text.txt и ./text.txt; ожидается объяснённый отказ до усечения и прежнее содержимое A B | ДЕЙСТВИЕ: передать два опубликованных пути программе; отказ до усечения, причина сообщена, успех не заявлен | ФАКТИЧЕСКИ: "+b.actual());checkBoundary(b);System.out.println("СТАТУС: открытое состояние подтверждено; смысл свободной диагностики и отсутствие преждевременного успеха проверяет преподаватель.");}catch(JavaProcess.StudentTimeout e){System.err.println("СТАТУС: программа не завершилась вовремя.");System.exit(2);}catch(JavaProcess.StudentOutputLimit e){System.err.println("СТАТУС: превышен объём вывода программы.");System.exit(2);}catch(JavaProcess.UncaughtStudentException e){System.err.println("СТАТУС: необработанное исключение при ожидаемом успешном копировании.");System.exit(2);}catch(CheckFailure e){System.err.println("СТАТУС: предметное несоответствие открытому состоянию файлов.");System.exit(1);}catch(Throwable e){System.err.println("СТАТУС: проверка не завершена; нужна проверка запуска, файлов и привязки.");System.exit(2);}}
 private static String diagnostic(JavaProcess.Result r){return "код завершения="+r.exitCode()+"; stdout="+r.stdout().getBytes(StandardCharsets.UTF_8).length+" байт; stderr="+r.stderr().getBytes(StandardCharsets.UTF_8).length+" байт; смысл диагностики проверяет преподаватель";}

 private static Observation normal()throws Exception{Path dir=CopySupport.workspace();try{byte[]input="A B\tC\u00A0D\r\nE F".getBytes(StandardCharsets.UTF_8),expected="A-B\tC\u00A0D\r\nE-F".getBytes(StandardCharsets.UTF_8);Path from=dir.resolve("вход с пробелом.txt"),to=dir.resolve("результат.txt");Files.write(from,input);var r=JavaProcess.runCheckingUncaught(TaskBinding.entry(),"",from.toString(),to.toString());boolean same=Files.isRegularFile(from)&&Arrays.equals(input,Files.readAllBytes(from)),ok=Files.isRegularFile(to)&&Arrays.equals(expected,Files.readAllBytes(to));return new Observation("получено: "+CopySupport.contents(to)+"; вход сохранён="+same+"; "+diagnostic(r),same&&ok);}finally{CopySupport.cleanup(dir);}}

 private static Observation boundary()throws Exception{Path dir=CopySupport.workspace();try{Path same=dir.resolve("text.txt");byte[]old="A B".getBytes(StandardCharsets.UTF_8);Files.write(same,old);Class<?>entry=TaskBinding.entry();Path classes=Path.of(entry.getProtectionDomain().getCodeSource().getLocation().toURI());var r=JavaProcess.run(classes,entry.getName(),"",List.of("text.txt","./text.txt"),dir,Duration.ofSeconds(10),65536);boolean preserved=Files.isRegularFile(same)&&Arrays.equals(old,Files.readAllBytes(same)),reported=!r.stdout().isBlank()||!r.stderr().isBlank();return new Observation("тот же файл: "+CopySupport.contents(same)+"; содержимое сохранено="+preserved+"; диагностика получена="+reported+"; "+diagnostic(r),preserved&&reported);}finally{CopySupport.cleanup(dir);}}

}
