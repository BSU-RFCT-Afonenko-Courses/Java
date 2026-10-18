import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        var values = new ArrayList<>(List.of(10, 20, 10));
        System.out.println(values.remove(1));
        System.out.println(values.remove(Integer.valueOf(10)));
        System.out.println(values);
        System.out.println(values.set(0, 7));
        values.add(0, 5);
        System.out.println(values);
        System.out.println(new ArrayList<String>(100).size());
        List<Number> numbers = new ArrayList<>();
        List<Integer> whole = List.of(2, 3);
        numbers.addAll(whole);
        numbers.add(0.5);
        System.out.println(numbers);
        try { values.get(values.size()); }
        catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
    }
}
