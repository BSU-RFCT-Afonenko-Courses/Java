import java.util.Arrays;
import java.util.function.Predicate;

public class Book {
    private int id;
    private String title;
    private String[] authors;
    private String publisher;
    private int year;
    private int pages;
    private double price;
    private String binding;

    public Book(int id, String title, String[] authors, String publisher, int year, int pages,
                double price, String binding) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.price = price;
        this.binding = binding;
    }

    public Book(int id, String title, String author, String publisher, int year, int pages,
                double price, String binding) {
        this(id, title, new String[] {author}, publisher, year, pages, price, binding);
    }

    public int id() { return id; }
    public void id(int value) { id = value; }

    public String title() { return title; }
    public void title(String value) { title = value; }

    public String[] authors() { return authors; }
    public void authors(String[] value) { authors = value; }

    public String publisher() { return publisher; }
    public void publisher(String value) { publisher = value; }

    public int year() { return year; }
    public void year(int value) { year = value; }

    public int pages() { return pages; }
    public void pages(int value) { pages = value; }

    public double price() { return price; }
    public void price(double value) { price = value; }

    public String binding() { return binding; }
    public void binding(String value) { binding = value; }

    @Override
    public String toString() {
        return id + ": " + title + ", " + Arrays.toString(authors) + ", " + publisher + ", "
                + year + ", " + pages + ", " + price + ", " + binding;
    }

    public static Book[] author(Book[] books, String author) {
        return select(books, book -> Arrays.asList(book.authors).contains(author));
    }

    public static Book[] publisher(Book[] books, String publisher) {
        return select(books, book -> book.publisher.contains(publisher));
    }

    public static Book[] after(Book[] books, int year) {
        return select(books, book -> book.year > year);
    }

    private static Book[] select(Book[] books, Predicate<Book> matches) {
        int count = 0;
        for (Book book : books) if (matches.test(book)) count++;
        Book[] result = new Book[count];
        int next = 0;
        for (Book book : books) if (matches.test(book)) result[next++] = book;
        return result;
    }
}
