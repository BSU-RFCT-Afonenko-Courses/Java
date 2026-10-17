import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
private static void create(Path out)throws IOException {File parent=out.toAbsolutePath().getParent().toFile();if(!parent.exists()&&!parent.mkdirs())throw new IOException("Не создан каталог "+parent);File f=out.toFile();if(!f.exists()&&!f.createNewFile())throw new IOException("Не создан файл "+f);}
public static void process(Path in,Path out,int m,int n)throws IOException{var all=Files.readAllLines(in,StandardCharsets.UTF_8).stream().filter(s->!s.isEmpty()).toList();var result=new ArrayList<String>();for(String line:all.subList(Math.max(0,all.size()-n),all.size())){var words=Arrays.stream(line.split(" +")).filter(s->!s.isEmpty()).toList();result.add(words.stream().skip(Math.max(0,words.size()-m)).collect(Collectors.joining(" ")));}create(out);Files.write(out,result,StandardCharsets.UTF_8);}
}
