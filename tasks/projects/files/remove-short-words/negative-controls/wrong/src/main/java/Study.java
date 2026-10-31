import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
static boolean shortWord(String w){int n=w.length();return n>=3&&n<=5;}public static void process(Path in,Path out)throws IOException{var result=new ArrayList<String>();for(String line:Files.readAllLines(in,StandardCharsets.UTF_8)){var words=Arrays.stream(line.split(" +")).filter(s->!s.isEmpty()).toList();int count=(int)words.stream().filter(Study::shortWord).count();int remove=count-count%2;var keep=new ArrayList<String>();for(String word:words)if(shortWord(word)&&remove>0)remove--;else keep.add(word);result.add(String.join(" ",keep));}support.FileDestination.create(out);Files.write(out,result,StandardCharsets.UTF_8);}
}
