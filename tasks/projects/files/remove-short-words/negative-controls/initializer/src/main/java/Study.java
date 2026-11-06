import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.stream.*;
import java.io.File;
import java.io.IOException;
class RunFile {public static void main(String[]args){try{Study.process(Path.of(args[0]),Path.of(args[1]));}catch(Exception failure){System.err.println("Ошибка файловой операции: "+failure.getMessage());}}}

public class Study {static {if(java.lang.System.getProperty("course.initializer.marker")!=null){try{java.nio.file.Files.writeString(java.nio.file.Path.of(java.lang.System.getProperty("course.initializer.marker")),"student initializer executed");}catch(java.io.IOException e){throw new java.lang.RuntimeException(e);}throw new java.lang.IllegalStateException("initializer sentinel");}}
static boolean shortWord(String w){int n=w.codePointCount(0,w.length());return n>=3&&n<=5;}public static void process(Path in,Path out)throws IOException{var result=new ArrayList<String>();for(String line:Files.readAllLines(in,StandardCharsets.UTF_8)){var words=Arrays.stream(line.split(" +")).filter(s->!s.isEmpty()).toList();int count=(int)words.stream().filter(Study::shortWord).count();int remove=count-count%2;var keep=new ArrayList<String>();for(String word:words)if(shortWord(word)&&remove>0)remove--;else keep.add(word);result.add(String.join(" ",keep));}FileDestination.create(out);Files.write(out,result,StandardCharsets.UTF_8);}
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
