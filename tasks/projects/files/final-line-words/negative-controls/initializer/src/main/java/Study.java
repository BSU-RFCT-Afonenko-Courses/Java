import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.stream.*;
import java.io.File;
import java.io.IOException;
class RunFile {public static void main(String[]args){try{Study.process(Path.of(args[0]),Path.of(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));}catch(Exception failure){System.err.println("Ошибка файловой операции: "+failure.getMessage());}}}

public class Study {static {if(true)throw new java.lang.IllegalStateException("initializer sentinel");}
public static void process(Path in,Path out,int m,int n)throws IOException{var all=Files.readAllLines(in,StandardCharsets.UTF_8);var result=new ArrayList<String>();for(String line:all.subList(Math.max(0,all.size()-n),all.size())){var words=Arrays.stream(line.split(" +")).filter(s->!s.isEmpty()).toList();result.add(words.stream().skip(Math.max(0,words.size()-m)).collect(Collectors.joining(" ")));}FileDestination.create(out);Files.write(out,result,StandardCharsets.UTF_8);}
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
