import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
class NestedProgram {
private static class Help {


private final Path file;private final BufferedReader input=new BufferedReader(new InputStreamReader(System.in,StandardCharsets.UTF_8));private Help(String filename){file=Path.of(filename);}private boolean helpOn(String what){try(BufferedReader reader=Files.newBufferedReader(file,StandardCharsets.UTF_8)){String line;while((line=reader.readLine())!=null)if(line.equals("#"+what)){while((line=reader.readLine())!=null&&!line.isEmpty())System.out.println(line);return true;}System.out.println("Тема не найдена: "+what);return false;}catch(IOException e){System.err.println("Ошибка чтения справки: "+e.getMessage());return false;}}private String getSelection(){try{return input.readLine();}catch(IOException e){System.err.println("Ошибка ввода: "+e.getMessage());return null;}}

}
void main(String[] args){var help=new Help(args[0]);System.out.println("Пустая строка или EOF — выход.");for(;;){var topic=help.getSelection();if(topic==null||topic.isEmpty())break;help.helpOn(topic);}}
}
