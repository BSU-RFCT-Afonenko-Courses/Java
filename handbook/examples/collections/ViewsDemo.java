import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewsDemo {
    public static void main(String[] args) {
        var source = new ArrayList<>(List.of("a", "b", "c"));
        var middle = source.subList(1, 3);
        middle.set(0, "B");
        System.out.println(source);
        middle.clear();
        System.out.println(source);
        source.reversed().add("z");
        System.out.println(source);
        String[] array = {"Ada", "Lin"};
        var fixed = Arrays.asList(array);
        fixed.set(0, "Ann");
        System.out.println(Arrays.toString(array));
        array[1] = "Eva";
        System.out.println(fixed);
        try { fixed.add("Bob"); }
        catch (UnsupportedOperationException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
    }
}
