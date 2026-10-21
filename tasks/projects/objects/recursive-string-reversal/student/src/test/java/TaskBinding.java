import java.io.*;import java.nio.charset.StandardCharsets;
class TaskBinding {static String printed(String s){PrintStream old=System.out;ByteArrayOutputStream bytes=new ByteArrayOutputStream();try(PrintStream out=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(out);Reverse.print(s);}finally{System.setOut(old);}return bytes.toString(StandardCharsets.UTF_8);}}
