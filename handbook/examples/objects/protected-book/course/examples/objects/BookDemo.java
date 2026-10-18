package course.examples.objects;

public class BookDemo {
    public static void main(String[] args) {
        Book book = new Book("Java");
        Book alias = book;
        alias.borrow();
        System.out.println(book.borrowed());
        System.out.println(alias == book);
        try {
            book.borrow();
        } catch (IllegalStateException ex) {
            System.out.println("refused");
        }
        System.out.println(book.borrowed());
    }
}
