import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
class TaskBinding {
 record Frequencies(LinkedHashMap<String,Integer> letters,LinkedHashMap<String,Integer> words){}
 static void run(Path a,Path b)throws Exception{FileInvocation.run(a.toString(),b.toString());}
 static void add(LinkedHashMap<String,Integer> map,String s,String separator){String[] pair=s.split(separator,-1);map.put(pair[0],Integer.parseInt(pair[1]));}
 static Frequencies read(Path file)throws Exception{var rows=Files.readAllLines(file,StandardCharsets.UTF_8);var letters=new LinkedHashMap<String,Integer>();var words=new LinkedHashMap<String,Integer>();int split=rows.indexOf("Слова");if(rows.isEmpty()||!rows.get(0).equals("Буквы")||split<1)throw new IllegalArgumentException("Подготовьте привязку к выбранному отчёту");for(String s:rows.subList(1,split))add(letters,s,"=");for(String s:rows.subList(split+1,rows.size()))add(words,s,"=");return new Frequencies(letters,words);}
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}}
