import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.regex.*;
/** Выбор: непрерывные Unicode-буквы, нижний регистр, устойчивый порядок списка. */
public class Study {static {if(java.lang.System.getProperty("course.initializer.marker")!=null){try{java.nio.file.Files.writeString(java.nio.file.Path.of(java.lang.System.getProperty("course.initializer.marker")),"student initializer executed");}catch(java.io.IOException e){throw new java.lang.RuntimeException(e);}throw new java.lang.IllegalStateException("initializer sentinel");}}
 public static void process(Path input,Path output,List<String> words)throws IOException{
  var report=new ArrayList<String>();
  for(String line:Files.readAllLines(input,StandardCharsets.UTF_8)){
   var counts=new LinkedHashMap<String,Integer>();for(String word:words)counts.put(word,0);
   var tokens=Pattern.compile("\\p{L}+").matcher(line.toLowerCase(Locale.ROOT));
   while(tokens.find())counts.computeIfPresent(tokens.group(),(k,v)->v+1);
   var sorted=new ArrayList<>(counts.entrySet());sorted.sort(Map.Entry.comparingByValue());
   report.add(sorted.stream().map(e->e.getKey()+":"+e.getValue()).collect(java.util.stream.Collectors.joining(" | ")));
  }
  Files.write(output,report,StandardCharsets.UTF_8);
 }
}
