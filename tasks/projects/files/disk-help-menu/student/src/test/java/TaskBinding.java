class TaskBinding {
record Lookup(boolean found,String text){}record Selections(java.util.List<String> values,boolean closed){}static Lookup lookup(java.nio.file.Path file,String topic)throws Exception{var oldOut=System.out;var oldErr=System.err;var bytes=new java.io.ByteArrayOutputStream();try(var p=new java.io.PrintStream(bytes,true,java.nio.charset.StandardCharsets.UTF_8)){System.setOut(p);System.setErr(p);boolean found=new Help(file.toString()).helpOn(topic);return new Lookup(found,bytes.toString(java.nio.charset.StandardCharsets.UTF_8));}finally{System.setOut(oldOut);System.setErr(oldErr);}}static Selections selections(java.nio.file.Path file)throws Exception{class Tracked extends java.io.ByteArrayInputStream {boolean closed;Tracked(){super("if\nfor\n".getBytes(java.nio.charset.StandardCharsets.UTF_8));}public void close(){closed=true;}}var in=new Tracked();var old=System.in;try{System.setIn(in);var help=new Help(file.toString());return new Selections(java.util.List.of(help.getSelection(),help.getSelection()),in.closed);}finally{System.setIn(old);}}
record Session(java.util.List<String> choices,java.util.List<Boolean> found,String text,boolean closed){}
static Session sequence(java.nio.file.Path file)throws Exception{
 class Tracked extends java.io.ByteArrayInputStream {boolean closed;Tracked(){super("if\nfor\n".getBytes(java.nio.charset.StandardCharsets.UTF_8));}public void close(){closed=true;}}
 var input=new Tracked();var oldIn=System.in;var oldOut=System.out;var oldErr=System.err;var bytes=new java.io.ByteArrayOutputStream();
 try(var stream=new java.io.PrintStream(bytes,true,java.nio.charset.StandardCharsets.UTF_8)){
  System.setIn(input);System.setOut(stream);System.setErr(stream);var help=new Help(file.toString());String first=help.getSelection();boolean one=first!=null&&help.helpOn(first);String second=help.getSelection();boolean two=second!=null&&help.helpOn(second);
  return new Session(java.util.Arrays.asList(first,second),java.util.List.of(one,two),bytes.toString(java.nio.charset.StandardCharsets.UTF_8),input.closed);
 }finally{System.setIn(oldIn);System.setOut(oldOut);System.setErr(oldErr);}
}
static boolean absenceShown(String s){return s.contains("Тема не найдена:");}
static boolean fileFailureShown(String s){return s.contains("Ошибка чтения справки:");}
}
