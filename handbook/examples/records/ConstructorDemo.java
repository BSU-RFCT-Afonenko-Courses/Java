import java.util.Objects;

public class ConstructorDemo {
    record Interval(int start, int end) {
        Interval {
            if (start > end) throw new IllegalArgumentException("Начало превышает конец");
        }
        Interval(int point) { this(point, point); }
        boolean contains(int value) { return start <= value && value <= end; }
    }

    record BookCode(String value) {
        BookCode {
            value = Objects.requireNonNull(value).strip();
            if (value.isEmpty()) throw new IllegalArgumentException("Пустой код");
        }
    }

    public static void main(String[] args) {
        System.out.println(new Interval(2, 5).contains(4));
        System.out.println(new Interval(2).contains(2));
        try { new Interval(5, 2); }
        catch (IllegalArgumentException ex) { System.out.println("Обратные границы отвергнуты"); }
        System.out.println(new BookCode("  A-17  ").value());
        try { new BookCode("   "); }
        catch (IllegalArgumentException ex) { System.out.println("Пустой код отвергнут"); }
        try { new BookCode(null); }
        catch (NullPointerException ex) { System.out.println("Отсутствующий код отвергнут"); }
    }
}
