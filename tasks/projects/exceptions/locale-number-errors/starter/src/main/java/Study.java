import java.io.*;import java.nio.file.*;import java.util.*;
public class Study {
 public record Raw(long line,String text) {} public record Summary(long count,double sum,OptionalDouble average) {}
 public static class BadRecord extends Exception { private final long line; public BadRecord(long line,Throwable cause){super(cause);this.line=line;}public long line(){return line;} }
 public static List<Raw> read(Path path)throws IOException {throw new UnsupportedOperationException("Самостоятельно прочитайте файл");}
 public static Summary analyze(List<Raw> rows)throws BadRecord {throw new UnsupportedOperationException("Реализуйте отдельный анализ");}
}
