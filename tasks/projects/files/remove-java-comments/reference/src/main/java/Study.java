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

public class Study {
public static String clean(String text){var out=new StringBuilder();int state=0;boolean escaped=false;for(int i=0;i<text.length();i++){char c=text.charAt(i),next=i+1<text.length()?text.charAt(i+1):0;if(state==0){if(c=='"'){state=1;out.append(c);}else if(c=='\''){state=2;out.append(c);}else if(c=='/'&&next=='/'){state=3;i++;}else if(c=='/'&&next=='*'){state=4;i++;out.append(' ');}else out.append(c);}else if(state==1||state==2){out.append(c);if(escaped)escaped=false;else if(c=='\\')escaped=true;else if(state==1&&c=='"'||state==2&&c=='\'')state=0;}else if(state==3){if(c=='\r'||c=='\n'){out.append(c);state=0;}}else{if(c=='*'&&next=='/'){state=0;i++;}else if(c=='\r'||c=='\n')out.append(c);}}return out.toString();}public static void process(Path in,Path out)throws IOException{String result=clean(Files.readString(in,StandardCharsets.UTF_8));FileDestination.create(out);Files.writeString(out,result,StandardCharsets.UTF_8);}
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
