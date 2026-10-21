/** Обычная привязка выбранных правил и отчёта; не обязательный API. */
class TaskBinding {
 record WordCount(String word,int count){}
 static void run(java.nio.file.Path a,java.nio.file.Path b,java.util.List<String> words)throws Exception{Study.process(a,b,words);}
 static java.util.List<java.util.List<WordCount>> read(java.nio.file.Path file)throws Exception{
  var rows=new java.util.ArrayList<java.util.List<WordCount>>();
  for(String line:java.nio.file.Files.readAllLines(file,java.nio.charset.StandardCharsets.UTF_8)){
   var values=new java.util.ArrayList<WordCount>();if(!line.isEmpty())for(String part:line.split("\\|")){String[] pair=part.strip().split(":",-1);values.add(new WordCount(pair[0],Integer.parseInt(pair[1])));}rows.add(values);
  }return rows;
 }
}
