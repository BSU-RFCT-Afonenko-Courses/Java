import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.stream.*;
import java.io.File;
import java.io.IOException;
class RunFile {public static void main(String[]args){try{Study.select(Path.of(args[0]),Path.of(args[1]),args[2],args[3]);}catch(Exception failure){System.err.println("Ошибка файловой операции: "+failure.getMessage());}}}

class SaveRecords {void main(String[] args)throws Exception{FileDestination.create(java.nio.file.Path.of(args[0]));java.nio.file.Files.write(java.nio.file.Path.of(args[0]),java.util.Arrays.asList("291234567;Анна","331234567;Борис","441234567;Вера"),java.nio.charset.StandardCharsets.UTF_8);Study.process(java.nio.file.Path.of(args[0]),java.nio.file.Path.of(args[1]),"29","33");}}

public class Study {static {if(java.lang.System.getProperty("course.initializer.marker")!=null){try{java.nio.file.Files.writeString(java.nio.file.Path.of(java.lang.System.getProperty("course.initializer.marker")),"student initializer executed");}catch(java.io.IOException e){throw new java.lang.RuntimeException(e);}throw new java.lang.IllegalStateException("initializer sentinel");}}public static void save(List<String>records,Path input)throws IOException{FileDestination.create(input);Files.write(input,records,StandardCharsets.UTF_8);}public static void select(Path input,Path output,String k,String j)throws IOException{process(input,output,k,j);}
public static void process(Path in,Path out,String k,String j)throws IOException{var selected=new ArrayList<String>();for(String line:Files.readAllLines(in,StandardCharsets.UTF_8)){String phone=line.substring(0,line.indexOf(';'));if(phone.startsWith(k)||phone.startsWith(j))selected.add(line);}FileDestination.create(out);Files.write(out,selected,StandardCharsets.UTF_8);}
}

/** Создание результата средствами File для преподавательских реализаций. */
final class FileDestination {
 private FileDestination(){}
 public static void create(Path output)throws IOException {
  File parent=output.toAbsolutePath().getParent().toFile();
  if(!parent.exists()&&!parent.mkdirs())throw new IOException("Не создан каталог "+parent);
  File file=output.toFile();if(!file.exists()&&!file.createNewFile())throw new IOException("Не создан файл "+file);
 }
}
