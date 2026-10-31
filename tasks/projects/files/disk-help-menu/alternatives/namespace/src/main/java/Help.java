import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import java.lang.System;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
public class Help {

private final Path file;private final BufferedReader input=new BufferedReader(new InputStreamReader(System.in,StandardCharsets.UTF_8));public Help(String filename){file=Path.of(filename);}public boolean helpOn(String what){try(BufferedReader reader=Files.newBufferedReader(file,StandardCharsets.UTF_8)){String line;while((line=reader.readLine())!=null)if(line.equals("#"+what)){while((line=reader.readLine())!=null&&!line.isEmpty())System.out.println(line);return true;}System.out.println("Тема не найдена: "+what);return false;}catch(IOException e){System.err.println("Ошибка чтения справки: "+e.getMessage());return false;}}public String getSelection(){try{return input.readLine();}catch(IOException e){System.err.println("Ошибка ввода: "+e.getMessage());return null;}}
}
