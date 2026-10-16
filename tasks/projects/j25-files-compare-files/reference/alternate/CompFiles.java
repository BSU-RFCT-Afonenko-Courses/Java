package variant;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class CompFiles {

public record Result(Boolean same,String problem){}public Result compare(Path a,Path b){try(var one=new BufferedInputStream(new FileInputStream(a.toFile()));var two=new BufferedInputStream(new FileInputStream(b.toFile()))){for(;;){byte[] x=one.readNBytes(257),y=two.readNBytes(257);if(!Arrays.equals(x,y))return new Result(false,null);if(x.length==0)return new Result(true,null);}}catch(IOException e){return new Result(null,e.getMessage());}}void main(String[] args){var r=compare(Path.of(args[0]),Path.of(args[1]));if(r.problem()!=null)System.err.println("Ошибка чтения: "+r.problem());else System.out.println(r.same()?"Файлы совпадают":"Файлы различаются");System.exit(7);}
}
