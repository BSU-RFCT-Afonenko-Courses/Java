import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
public static void process(Path in,Path out)throws IOException{String text=Files.readString(in,StandardCharsets.UTF_8).strip();var numbers=new ArrayList<Integer>();if(!text.isEmpty())for(String token:text.split("\\s+"))numbers.add(Integer.parseInt(token));numbers.sort(Integer::compare);String result=numbers.stream().map(Object::toString).collect(Collectors.joining(" "));support.FileDestination.create(out);Files.writeString(out,result,StandardCharsets.UTF_8);}
}
