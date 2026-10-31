import java.io.IOException;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import support.FileDestination;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Study {public static void save(List<String>records,Path input)throws IOException{FileDestination.create(input);Files.write(input,records,StandardCharsets.UTF_8);}public static void select(Path input,Path output,String k,String j)throws IOException{process(input,output,k,j);}
public static void process(Path in,Path out,String k,String j)throws IOException{var selected=new ArrayList<String>();for(String line:Files.readAllLines(in,StandardCharsets.UTF_8)){String phone=line.substring(0,line.indexOf(';'));if(phone.startsWith(k)||phone.startsWith(j))selected.add(line);}FileDestination.create(out);Files.write(out,selected,StandardCharsets.UTF_8);}
}
