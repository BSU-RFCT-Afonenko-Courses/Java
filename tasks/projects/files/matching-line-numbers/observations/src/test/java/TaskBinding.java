import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
class TaskBinding {
record Observation(List<String> lines,List<Integer> numbers){}static void run(Path a,Path b,String q)throws Exception{FileInvocation.run(a.toString(),b.toString(),q);}static Observation read(Path p)throws Exception{var a=Files.readAllLines(p);if(a.size()<3||!a.get(0).equals("# Строки")||!a.get(a.size()-2).equals("# Номера"))throw new IllegalArgumentException("Формат этой привязки");var values=a.get(a.size()-1);var n=new ArrayList<Integer>();if(!values.equals("[]"))for(String s:values.substring(1,values.length()-1).split(",\\s*"))n.add(Integer.parseInt(s));return new Observation(a.subList(1,a.size()-2),n);}
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}}
