import java.io.IOException;
import java.lang.Math;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import support.FileDestination;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
public static void process(Path in,Path out,int m,int n)throws IOException{var all=Files.readAllLines(in,StandardCharsets.UTF_8);var result=new ArrayList<String>();for(String line:all.subList(Math.max(0,all.size()-n),all.size())){var words=Arrays.stream(line.split(" +")).filter(s->!s.isEmpty()).toList();result.add(words.stream().skip(Math.max(0,words.size()-m)).collect(Collectors.joining(" ")));}FileDestination.create(out);Files.write(out,result,StandardCharsets.UTF_8);}
}
