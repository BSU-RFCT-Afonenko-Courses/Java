import course.examples.objects.Book;

public class EffectsDemo {
    static String label(Book book) { return "Book: " + book.title(); }
    static void announceAndBorrow(Book book) {
        System.out.println(label(book));
        book.borrow();
    }
    public static void main(String[] args) {
        Book book = new Book("Java");
        String text = label(book);
        System.out.println(book.borrowed());
        announceAndBorrow(book);
        System.out.println(book.borrowed());
    }
}
