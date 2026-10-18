public class ObjectsTailDemo {
    static void borrow(Book book) {
        book.borrowed = true;
    }
    static void replace(Book book) {
        book = new Book();
        book.title = "Replacement";
    }
    static Book create(String title) {
        Book result = new Book();
        result.title = title;
        return result;
    }
    public static void main(String[] args) {
        Book first = create("Java");
        borrow(first);
        replace(first);
        System.out.println(first.title); // Java
        Book book = create("Java");
        System.out.println(book.title); // Java

        Book copy = new Book();
        copy.title = first.title;
        copy.borrowed = first.borrowed;
        copy.borrowed = false;
        System.out.println(first.borrowed); // true
        System.out.println(copy.borrowed); // false
        System.out.println(first == copy); // false

        Book kept = create("Java");
        Book alias = kept;
        kept = null;
        System.out.println(alias.title); // Java

        Book a = create("Java");
        Book b = a;
        borrow(b);
        b = create("Algorithms");
        System.out.println(a.title + ": " + a.borrowed); // Java: true
    }
}
