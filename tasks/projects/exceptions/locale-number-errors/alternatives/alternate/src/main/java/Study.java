import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.math.*;
public class Study {
 public static class Raw {private final long line;private final String text;private Raw(long line,String text){this.line=line;this.text=text;}public long line(){return line;}public String text(){return text;}}
 public static class Summary {private final long count;private final double sum;private final OptionalDouble average;private Summary(long count,double sum,OptionalDouble average){this.count=count;this.sum=sum;this.average=average;}public long count(){return count;}public double sum(){return sum;}public OptionalDouble average(){return average;}}
 public static class BadRecord extends IOException {private final long line;private BadRecord(long line,Throwable cause){super(cause);this.line=line;}public long line(){return line;}}
 public static List<Raw> read(Path path)throws IOException {var result=new ArrayList<Raw>();for(var entry:new ReaderApp().load(path))result.add(new Raw(entry.physicalRow(),String.join("\t",entry.original())));return result;}
 public static Summary analyze(List<Raw> rows)throws BadRecord {var entries=new ArrayList<ReaderApp.Entry>();for(Raw row:rows)entries.add(new ReaderApp.Entry(row.line(),List.of(row.text().split("\t",-1))));try{var r=new ReaderApp().calculate(entries);return new Summary(r.size(),r.total().doubleValue(),r.mean().isEmpty()?OptionalDouble.empty():OptionalDouble.of(r.mean().get().doubleValue()));}catch(ReaderApp.RecordProblem e){throw new BadRecord(e.physicalRow(),e.getCause());}}
}

class ReaderApp {
    public record Entry(long physicalRow, List<String> original) {}
    public record Outcome(long size, BigDecimal total, Optional<BigDecimal> mean) {}
    public static class RecordProblem extends IOException {
        private final long line;
        public RecordProblem(long line, Throwable cause) {super("Ошибка записи в строке " + line, cause);this.line=line;}
        public long physicalRow() {return line;}
    }
    // Чтение сохраняет исходные поля; формат и число здесь не проверяются.
    public List<Entry> load(Path path) throws IOException {
        var rows=new ArrayList<Entry>();
        try(var input=Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String text;long line=0;
            while((text=input.readLine())!=null)rows.add(new Entry(++line,List.of(text.split("\t",-1))));
        }
        return rows;
    }
    static BigDecimal number(Entry row) throws RecordProblem {
        try {
            if(row.original().size()!=2||row.original().get(0).isEmpty()||row.original().get(1).isEmpty())
                throw new IllegalArgumentException("Ожидаются два непустых поля");
            String text=row.original().get(0);
            Locale locale=new Locale.Builder().setLanguageTag(row.original().get(1)).build();
            // Фабрика NumberFormat сохраняет расширения и документированное разрешение локали.
            NumberFormat format=NumberFormat.getInstance(locale);
            if(format instanceof DecimalFormat decimal)decimal.setParseBigDecimal(true);
            ParsePosition position=new ParsePosition(0);
            Number value=format.parse(text,position);
            if(value==null||position.getIndex()!=text.length())throw new ParseException("Число должно разбираться целиком",Math.max(position.getIndex(),position.getErrorIndex()));
            if(!Double.isFinite(value.doubleValue()))throw new ArithmeticException("Число вне конечного double");
            return value instanceof BigDecimal decimal?decimal:new BigDecimal(value.toString());
        } catch(RuntimeException|ParseException cause) {throw new RecordProblem(row.physicalRow(),cause);}
    }
    public Outcome calculate(List<Entry> rows) throws RecordProblem {
        BigDecimal total=BigDecimal.ZERO;
        for(Entry row:rows)total=total.add(number(row));
        if(rows.isEmpty())return new Outcome(0,BigDecimal.ZERO,Optional.empty());
        double sum=total.doubleValue();
        if(!Double.isFinite(sum))throw new RecordProblem(rows.getLast().physicalRow(),new ArithmeticException("Сумма вне конечного double"));
        BigDecimal average=total.divide(BigDecimal.valueOf(rows.size()),MathContext.DECIMAL128);
        return new Outcome(rows.size(),total,Optional.of(average));
    }
    void main(String[] args) {
        try {var result=calculate(load(Path.of(args[0])));if(result.mean().isEmpty())System.out.println("Нет записей");else System.out.println("Итог "+result.total().doubleValue()+" / "+result.mean().get().doubleValue());}
        catch(RecordProblem e){System.err.println("Строка "+e.physicalRow()+": "+e.getCause());}
        catch(IOException e){System.err.println("Ошибка файла: "+e.getMessage());}
    }
}
