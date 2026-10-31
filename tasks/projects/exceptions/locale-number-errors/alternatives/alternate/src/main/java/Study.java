import java.io.*;import java.nio.file.*;import java.util.*;
public class Study {
 public static class Raw {private final long line;private final String text;private Raw(long line,String text){this.line=line;this.text=text;}public long line(){return line;}public String text(){return text;}}
 public static class Summary {private final long count;private final double sum;private final OptionalDouble average;private Summary(long count,double sum,OptionalDouble average){this.count=count;this.sum=sum;this.average=average;}public long count(){return count;}public double sum(){return sum;}public OptionalDouble average(){return average;}}
 public static class BadRecord extends IOException {private final long line;private BadRecord(long line,Throwable cause){super(cause);this.line=line;}public long line(){return line;}}
 public static List<Raw> read(Path path)throws IOException {var result=new ArrayList<Raw>();for(var entry:new variant.ReaderApp().load(path))result.add(new Raw(entry.physicalRow(),String.join("\t",entry.original())));return result;}
 public static Summary analyze(List<Raw> rows)throws BadRecord {var entries=new ArrayList<variant.ReaderApp.Entry>();for(Raw row:rows)entries.add(new variant.ReaderApp.Entry(row.line(),List.of(row.text().split("\t",-1))));try{var r=new variant.ReaderApp().calculate(entries);return new Summary(r.size(),r.total().doubleValue(),r.mean().isEmpty()?OptionalDouble.empty():OptionalDouble.of(r.mean().get().doubleValue()));}catch(variant.ReaderApp.RecordProblem e){throw new BadRecord(e.physicalRow(),e.getCause());}}
}
