class TaskBinding {
record Observation(java.util.List<String> lines,java.util.List<Integer> numbers){}static void run(java.nio.file.Path a,java.nio.file.Path b,String q)throws Exception{new variant.Example().process(a,b,q);}static Observation read(java.nio.file.Path p)throws Exception{var a=java.nio.file.Files.readAllLines(p);int n=Integer.parseInt(a.get(0).substring(6));String s=a.get(n+1).substring(8);var values=new java.util.ArrayList<Integer>();if(!s.isEmpty())for(String x:s.split(" "))values.add(Integer.parseInt(x));return new Observation(a.subList(1,n+1),values);}
}
