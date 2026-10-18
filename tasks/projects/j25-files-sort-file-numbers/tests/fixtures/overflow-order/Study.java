import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
private static void create(Path out)throws IOException {File parent=out.toAbsolutePath().getParent().toFile();if(!parent.exists()&&!parent.mkdirs())throw new IOException("Не создан каталог "+parent);File f=out.toFile();if(!f.exists()&&!f.createNewFile())throw new IOException("Не создан файл "+f);}
public static void process(Path in,Path out)throws IOException{String text=Files.readString(in,StandardCharsets.UTF_8).strip();var numbers=new ArrayList<Integer>();if(!text.isEmpty())for(String token:text.split("\\s+"))numbers.add(Integer.parseInt(token));numbers.sort((a,b)->a-b);String result=numbers.stream().map(Object::toString).collect(Collectors.joining(" "));create(out);Files.writeString(out,result,StandardCharsets.UTF_8);}
}
