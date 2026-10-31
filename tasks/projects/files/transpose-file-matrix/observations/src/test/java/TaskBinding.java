import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
class TaskBinding {
record Matrices(int[][] source,int[][] transposed){}static void run(Path a,Path b)throws Exception{FileInvocation.run(a.toString(),b.toString());}static int[][] rows(List<String> a){return a.stream().map(s->Arrays.stream(s.strip().split("\\s+")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);}static Matrices read(Path p)throws Exception{var a=Files.readAllLines(p);int split=a.indexOf("# Транспонированная");if(a.isEmpty()||!a.get(0).equals("# Исходная")||split<1)throw new IllegalArgumentException("Формат этой привязки");return new Matrices(rows(a.subList(1,split)),rows(a.subList(split+1,a.size())));}
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}}
