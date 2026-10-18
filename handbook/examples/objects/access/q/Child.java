package q;

public class Child extends p.Base {
    void inspect(Child child, p.Base base) {
        child.value = 1;
        System.out.println(child.value);
        // base.value = 1; // Доступ через ссылку базового типа запрещён.
    }
}
