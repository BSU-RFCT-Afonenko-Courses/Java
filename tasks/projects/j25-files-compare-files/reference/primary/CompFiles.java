import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class CompFiles {

public static boolean equal(Path a,Path b)throws IOException{try(FileInputStream one=new FileInputStream(a.toFile());FileInputStream two=new FileInputStream(b.toFile())){for(;;){int x=one.read(),y=two.read();if(x!=y)return false;if(x==-1)return true;}}}public static void main(String[] args){try{System.out.println(equal(Path.of(args[0]),Path.of(args[1]))?"Файлы совпадают":"Файлы различаются");}catch(IOException e){System.err.println("Ошибка чтения: "+e.getMessage());}}
}
