import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {
public static void process(Path in,Path out,String query)throws IOException{var all=Files.readAllLines(in,StandardCharsets.UTF_8);var matches=new ArrayList<Integer>();for(int i=0;i<all.size();i++)if(all.get(i).strip().equals(query.strip()))matches.add(i+1);var result=new ArrayList<String>();result.add("# Строки");result.addAll(all);result.add("# Номера");result.add(matches.toString());support.FileDestination.create(out);Files.write(out,result,StandardCharsets.UTF_8);}
}
