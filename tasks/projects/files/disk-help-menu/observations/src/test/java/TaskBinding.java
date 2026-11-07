import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
class TaskBinding {
record Lookup(boolean found,String text){}record Selections(List<String> values,boolean closed){}static Lookup lookup(Path file,String topic)throws Exception{var oldOut=System.out;var oldErr=System.err;var bytes=new ByteArrayOutputStream();try(var p=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(p);System.setErr(p);boolean found=Study.helpOn(Study.help(file),topic);return new Lookup(found,bytes.toString(StandardCharsets.UTF_8));}finally{System.setOut(oldOut);System.setErr(oldErr);}}static Selections selections(Path file)throws Exception{class Tracked extends ByteArrayInputStream {boolean closed;Tracked(){super("if\nfor\n".getBytes(StandardCharsets.UTF_8));}public void close(){closed=true;}}var in=new Tracked();var old=System.in;try{System.setIn(in);var help=Study.help(file);return new Selections(List.of(Study.selectedTopic(Study.select(help)),Study.selectedTopic(Study.select(help))),in.closed);}finally{System.setIn(old);}}
record Session(List<String> choices,List<Boolean> found,String text,boolean closed){}
static Session sequence(Path file)throws Exception{
 class Tracked extends ByteArrayInputStream {boolean closed;Tracked(){super("if\nfor\n".getBytes(StandardCharsets.UTF_8));}public void close(){closed=true;}}
 var input=new Tracked();var oldIn=System.in;var oldOut=System.out;var oldErr=System.err;var bytes=new ByteArrayOutputStream();
 try(var stream=new PrintStream(bytes,true,StandardCharsets.UTF_8)){
  System.setIn(input);System.setOut(stream);System.setErr(stream);var help=Study.help(file);String first=Study.selectedTopic(Study.select(help));boolean one=first!=null&&Study.helpOn(help,first);String second=Study.selectedTopic(Study.select(help));boolean two=second!=null&&Study.helpOn(help,second);
  return new Session(Arrays.asList(first,second),List.of(one,two),bytes.toString(StandardCharsets.UTF_8),input.closed);
 }finally{System.setIn(oldIn);System.setOut(oldOut);System.setErr(oldErr);}
}
static boolean absenceShown(String s){return s.contains("Тема не найдена:");}
static boolean fileFailureShown(String s){return s.contains("Ошибка чтения справки:");}
}
