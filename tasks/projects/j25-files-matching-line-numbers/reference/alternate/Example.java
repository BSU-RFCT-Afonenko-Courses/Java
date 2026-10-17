package variant;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Example {
private static void create(Path out)throws IOException {File parent=out.toAbsolutePath().getParent().toFile();if(!parent.exists()&&!parent.mkdirs())throw new IOException("Не создан каталог "+parent);File f=out.toFile();if(!f.exists()&&!f.createNewFile())throw new IOException("Не создан файл "+f);}
public void process(Path in,Path out,String query)throws IOException{var all=Files.readAllLines(in,StandardCharsets.UTF_8);var matches=new ArrayList<Integer>();for(int i=0;i<all.size();i++)if(all.get(i).equals(query))matches.add(i+1);var lines=new ArrayList<String>();lines.add("LINES "+all.size());lines.addAll(all);lines.add("MATCHES "+matches.stream().map(Object::toString).collect(Collectors.joining(" ")));create(out);Files.write(out,lines,StandardCharsets.UTF_8);}
}
