import java.io.IOException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import support.FileDestination;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
public static void process(Path in,Path out)throws IOException{String text=Files.readString(in,StandardCharsets.UTF_8).strip();var numbers=new ArrayList<Integer>();if(!text.isEmpty())for(String token:text.split("\\s+"))numbers.add(Integer.parseInt(token));numbers.sort(Integer::compare);String result=numbers.stream().map(Object::toString).collect(Collectors.joining(" "));FileDestination.create(out);Files.writeString(out,result,StandardCharsets.UTF_8);}
}
