class TaskBinding {
 record Frequencies(java.util.LinkedHashMap<String,Integer> letters,java.util.LinkedHashMap<String,Integer> words){}
 static void run(java.nio.file.Path a,java.nio.file.Path b)throws Exception{Study.process(a,b);}
 static void add(java.util.LinkedHashMap<String,Integer> map,String s,String separator){String[] pair=s.split(separator,-1);map.put(pair[0],Integer.parseInt(pair[1]));}
 static Frequencies read(java.nio.file.Path file)throws Exception{var rows=java.nio.file.Files.readAllLines(file,java.nio.charset.StandardCharsets.UTF_8);var letters=new java.util.LinkedHashMap<String,Integer>();var words=new java.util.LinkedHashMap<String,Integer>();int split=rows.indexOf("Слова");if(rows.isEmpty()||!rows.get(0).equals("Буквы")||split<1)throw new IllegalArgumentException("Подготовьте привязку к выбранному отчёту");for(String s:rows.subList(1,split))add(letters,s,"=");for(String s:rows.subList(split+1,rows.size()))add(words,s,"=");return new Frequencies(letters,words);}
}
