import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
private static void create(Path out)throws IOException {File parent=out.toAbsolutePath().getParent().toFile();if(!parent.exists()&&!parent.mkdirs())throw new IOException("Не создан каталог "+parent);File f=out.toFile();if(!f.exists()&&!f.createNewFile())throw new IOException("Не создан файл "+f);}
public static void process(Path in,Path out,String k,String j)throws IOException{var selected=new ArrayList<String>();for(String line:Files.readAllLines(in,StandardCharsets.UTF_8)){String phone=line.substring(0,line.indexOf(';'));if(phone.startsWith(k)&&phone.startsWith(j))selected.add(line);}create(out);Files.write(out,selected,StandardCharsets.UTF_8);}
}
