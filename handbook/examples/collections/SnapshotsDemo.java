import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import course.examples.objects.Book;

public class SnapshotsDemo {
    public static void main(String[] args) {
        Book first = new Book("Java");
        var source = new ArrayList<>(List.of(first));
        var alias = source;
        var view = Collections.unmodifiableList(source);
        var copy = new ArrayList<>(source);
        var snapshot = List.copyOf(source);
        source.add(new Book("Алгоритмы"));
        System.out.println(source.size() + "/" + alias.size() + "/"
                + view.size() + "/" + copy.size() + "/" + snapshot.size());
        first.borrow();
        System.out.println(source.get(0).borrowed());
        System.out.println(alias.get(0).borrowed());
        System.out.println(view.get(0).borrowed());
        System.out.println(copy.get(0).borrowed());
        System.out.println(snapshot.get(0).borrowed());
        copy.clear();
        System.out.println(source.size() + "/" + copy.size());
        try { view.add(new Book("Сети")); }
        catch (UnsupportedOperationException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        try { snapshot.set(0, new Book("Сети")); }
        catch (UnsupportedOperationException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        var withNull = new ArrayList<Book>();
        withNull.add(null);
        try { List.copyOf(withNull); }
        catch (NullPointerException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        var client = new ArrayList<>(List.of(first));
        var owned = new ArrayList<>(client);
        client.clear();
        System.out.println(client.size() + "/" + owned.size());
    }
}
