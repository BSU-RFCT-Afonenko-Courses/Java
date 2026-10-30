import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

public class ApiContract {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        Class<?> type = api.type("Book");
        api.expect(Modifier.isPublic(type.getModifiers()), "публичный класс Book без пакета", type.toGenericString());
        for (Class<?> authors : new Class<?>[] {String[].class, String.class}) {
            boolean found;
            try {
                type.getConstructor(int.class, String.class, authors, String.class, int.class, int.class,
                        double.class, String.class);
                found = true;
            } catch (NoSuchMethodException ex) { found = false; }
            api.expect(found, "публичный полный конструктор Book с " + authors.getTypeName(),
                    java.util.Arrays.toString(type.getConstructors()));
        }
        String[] names = {"id", "title", "authors", "publisher", "year", "pages", "price", "binding"};
        Class<?>[] types = {int.class, String.class, String[].class, String.class, int.class, int.class,
                double.class, String.class};
        for (int i = 0; i < names.length; i++) {
            api.method("Book", names[i], types[i], Modifier.PUBLIC, Modifier.STATIC);
            api.method("Book", names[i], void.class, Modifier.PUBLIC, Modifier.STATIC, types[i]);
        }
        api.method("Book", "toString", String.class, Modifier.PUBLIC, Modifier.STATIC);
        api.method("Book", "author", type.arrayType(), Modifier.PUBLIC | Modifier.STATIC, 0, type.arrayType(), String.class);
        api.method("Book", "publisher", type.arrayType(), Modifier.PUBLIC | Modifier.STATIC, 0, type.arrayType(), String.class);
        api.method("Book", "after", type.arrayType(), Modifier.PUBLIC | Modifier.STATIC, 0, type.arrayType(), int.class);
        api.compiles("BookApiClient", """
                class BookApiClient {
                    void use() {
                        Book book = new Book(1, "Название", new String[] {"Автор"}, "Издательство", 1, 1, 0, "Переплёт");
                        Book single = new Book(2, "Другое", "Автор", "Издательство", 9999, 2, 1, "Переплёт");
                        book.id(book.id()); book.title(book.title()); book.authors(book.authors());
                        book.publisher(book.publisher()); book.year(book.year()); book.pages(book.pages());
                        book.price(book.price()); book.binding(book.binding());
                        String text = book.toString();
                        Book[] books = {book, single};
                        Book[] byAuthor = Book.author(books, "Автор");
                        Book[] byPublisher = Book.publisher(books, "Издательство");
                        Book[] afterYear = Book.after(books, 1);
                    }
                }
                """, true);
    }
}
