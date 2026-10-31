import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
/** Обычная привязка выбранных правил и отчёта; не обязательный API. */
class TaskBinding {
 record WordCount(String word,int count){}
 static void run(Path a,Path b,List<String> words)throws Exception{var args=new ArrayList<String>(List.of(a.toString(),b.toString()));args.addAll(words);FileInvocation.run(args.toArray(String[]::new));}
 static List<List<WordCount>> read(Path file)throws Exception{
  var rows=new ArrayList<List<WordCount>>();
  for(String line:Files.readAllLines(file,StandardCharsets.UTF_8)){
   var values=new ArrayList<WordCount>();if(!line.isEmpty())for(String part:line.split("\\|")){String[] pair=part.strip().split(":",-1);values.add(new WordCount(pair[0],Integer.parseInt(pair[1])));}rows.add(values);
  }return rows;
 }
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}}
