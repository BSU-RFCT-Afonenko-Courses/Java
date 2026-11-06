import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.stream.*;
import java.io.File;
import java.io.IOException;
class RunFile {public static void main(String[]args){try{Study.process(Path.of(args[0]),Path.of(args[2]));}catch(Exception failure){System.err.println("Ошибка файловой операции: "+failure.getMessage());}}}

public class Study {static {if(java.lang.System.getProperty("course.initializer.marker")!=null){try{java.nio.file.Files.writeString(java.nio.file.Path.of(java.lang.System.getProperty("course.initializer.marker")),"student initializer executed");}catch(java.io.IOException e){throw new java.lang.RuntimeException(e);}throw new java.lang.IllegalStateException("initializer sentinel");}}
public static void process(Path in,Path out)throws IOException{if(Files.exists(out)&&Files.isSameFile(in,out))throw new IOException("Вход и выход — один файл");var lines=Files.readAllLines(in,StandardCharsets.UTF_8);int n=lines.size();int[][] matrix=new int[n][n],transposed=new int[n][n];for(int i=0;i<n;i++){String[] values=lines.get(i).strip().split("\\s+");if(values.length!=n)throw new IOException("Не квадратная матрица");for(int j=0;j<n;j++)matrix[i][j]=Integer.parseInt(values[j]);}for(int i=0;i<n;i++)for(int j=0;j<n;j++)transposed[j][i]=matrix[i][j];var result=new ArrayList<String>();result.add("# Исходная");for(int[] row:matrix)result.add(Arrays.stream(row).mapToObj(Integer::toString).collect(Collectors.joining(" ")));result.add("# Транспонированная");for(int[] row:transposed)result.add(Arrays.stream(row).mapToObj(Integer::toString).collect(Collectors.joining(" ")));FileDestination.create(out);Files.write(out,result,StandardCharsets.UTF_8);}
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
