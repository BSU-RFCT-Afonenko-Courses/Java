import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
class TaskBinding {
static void run(Path a,Path b,String k,String j)throws Exception{FileInvocation.run("select",a.toString(),b.toString(),k,j);}
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}static void save(List<String> records,Path input)throws Exception{var args=new ArrayList<String>(List.of("save",input.toString()));args.addAll(records);Files.deleteIfExists(input);FileInvocation.run(args.toArray(String[]::new));if(!Files.isRegularFile(input)||!records.equals(Files.readAllLines(input,StandardCharsets.UTF_8)))throw new AssertionError("Исходные записи действительно сохраняются в UTF-8");}}
