import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.util.*;
import java.math.*;
public class Study {
    public record Raw(long line, List<String> fields) {}
    public record Stats(long count, double sum, OptionalDouble average) {}
    public static class BadRecord extends Exception {
        private final long line;
        public BadRecord(long line, Throwable cause) {super("Ошибка записи в строке " + line, cause);this.line=line;}
        public long line() {return line;}
    }
    // Чтение сохраняет исходные поля; формат и число здесь не проверяются.
    public static List<Raw> read(Path path) throws IOException {
        var rows=new ArrayList<Raw>();
        try(var input=Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String text;long line=0;
            while((text=input.readLine())!=null)rows.add(new Raw(++line,List.of(text.split("\t",-1))));
        }
        return rows;
    }
    static BigDecimal number(Raw row) throws BadRecord {
        try {
            if(row.fields().size()!=2||row.fields().get(0).isEmpty()||row.fields().get(1).isEmpty())
                throw new IllegalArgumentException("Ожидаются два непустых поля");
            String text=row.fields().get(0);
            Locale locale=new Locale.Builder().setLanguageTag(row.fields().get(1)).build();
            // Фабрика NumberFormat сохраняет расширения и документированное разрешение локали.
            NumberFormat format=NumberFormat.getInstance(locale);
            if(format instanceof DecimalFormat decimal)decimal.setParseBigDecimal(true);
            ParsePosition position=new ParsePosition(0);
            Number value=format.parse(text,position);
            if(value==null||position.getIndex()!=text.length())throw new ParseException("Число должно разбираться целиком",Math.max(position.getIndex(),position.getErrorIndex()));
            if(!Double.isFinite(value.doubleValue()))throw new ArithmeticException("Число вне конечного double");
            return value instanceof BigDecimal decimal?decimal:new BigDecimal(value.toString());
        } catch(RuntimeException|ParseException cause) {throw new BadRecord(row.line(),cause);}
    }
    public static Stats analyze(List<Raw> rows) throws BadRecord {
        BigDecimal total=BigDecimal.ZERO;
        for(Raw row:rows)total=total.add(number(row));
        if(rows.isEmpty())return new Stats(0,0,OptionalDouble.empty());
        double sum=total.doubleValue();
        
        double average=total.divide(BigDecimal.valueOf(rows.size()),MathContext.DECIMAL128).doubleValue();
        return new Stats(rows.size(),sum,OptionalDouble.of(average));
    }
    public static void main(String[] args) {
        try {
            Stats stats=analyze(read(Path.of(args[0])));
            if(stats.average().isEmpty())System.out.println("Нет данных");
            else System.out.println("Сумма " + stats.sum() + ", среднее " + stats.average().getAsDouble());
        } catch(BadRecord e) {System.err.println(e.getMessage()+": "+e.getCause());}
          catch(IOException e) {System.err.println("Ошибка чтения: "+e.getMessage());}
    }
}
