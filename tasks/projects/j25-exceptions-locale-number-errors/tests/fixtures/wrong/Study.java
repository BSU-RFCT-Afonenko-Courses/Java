public class Study {
public record Raw(long line,java.util.List<String> fields){}public record Stats(long count,double sum,java.util.OptionalDouble average){}public static class BadRecord extends Exception{private final long line;public BadRecord(long line,Throwable cause){super("строка "+line,cause);this.line=line;}public long line(){return line;}}
public static java.util.List<Raw> read(java.nio.file.Path path)throws java.io.IOException{return java.util.List.of();}public static Stats analyze(java.util.List<Raw> rows)throws BadRecord{return new Stats(0,0,java.util.OptionalDouble.empty());}
}
