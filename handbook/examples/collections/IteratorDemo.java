import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        var names = new ArrayList<>(List.of("Ada", "", "Lin", ""));
        Iterator<String> it = names.iterator();
        try { it.remove(); }
        catch (IllegalStateException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        while (it.hasNext()) {
            if (it.next().isEmpty()) it.remove();
        }
        System.out.println(names);
        try { it.next(); }
        catch (java.util.NoSuchElementException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
    }
}
