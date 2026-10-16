package variant;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.util.*;
import java.math.*;
public class ReaderApp {
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
