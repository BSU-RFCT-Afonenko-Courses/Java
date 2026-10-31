import java.lang.String;
import java.util.List;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookSelectionTest {
    private record State(int id, String title, List<String> authors, String publisher,
                         int year, int pages, double price, String binding) {
        static State of(Book b) {
            return new State(b.id(), b.title(), List.copyOf(Arrays.asList(b.authors())),
                    b.publisher(), b.year(), b.pages(), b.price(), b.binding());
        }
    }

    private Book sample() {
        return new Book(17, "Массивы", new String[] {"Анна", "Борис"}, "Свет", 2024, 312, 19.75, "Твёрдый");
    }

    private State baseline() {
        return new State(17, "Массивы", List.of("Анна", "Борис"), "Свет", 2024, 312, 19.75, "Твёрдый");
    }

    private void change(Consumer<Book> update, State expected) {
        Book book = sample();
        update.accept(book);
        assertEquals(expected, State.of(book), "Setter меняет только выбранное свойство; остальные сохраняются");
    }

    private Book[] catalog() {
        Book first = new Book(31, "Третья", new String[] {"Борис", "Анна", "Анна"}, "Свет", 2025, 301, 11.25, "Мягкий");
        Book second = new Book(7, "Первая", "Анна", "Свет", 2024, 152, 0, "Твёрдый");
        Book lower = new Book(19, "Вторая", "анна", "свет", 2023, 99, 23.5, "Мягкий");
        Book substring = new Book(5, "Четвёртая", "Анна-Мария", "Светлый", 2026, 211, 9, "Твёрдый");
        return new Book[] {first, second, first, lower, substring};
    }

    private void selection(Book[] input, Function<Book[], Book[]> select, int... indices) {
        Book[] original = input.clone();
        State[] before = Arrays.stream(input).map(State::of).toArray(State[]::new);
        Book[] actual = select.apply(input);
        assertNotNull(actual, "Пустая подборка представляется массивом, а не null");
        State[] expected = Arrays.stream(indices).mapToObj(i -> before[i]).toArray(State[]::new);
        assertArrayEquals(expected, Arrays.stream(actual).map(State::of).toArray(State[]::new),
                "Подборка сохраняет все свойства, порядок и каждое подходящее вхождение");
        for (int i = 0; i < input.length; i++) {
            assertSame(original[i], input[i], "Ссылки исходного массива сохраняются на своих местах");
            assertEquals(before[i], State.of(input[i]), "Отбор сохраняет свойства исходной книги");
        }
    }

    @Test @DisplayName("Полная книга с несколькими авторами")
    void fullConstructor() { assertEquals(baseline(), State.of(sample()), "Конструктор сохраняет все восемь свойств"); }

    @Test @DisplayName("Книга с одним автором")
    void singleAuthorConstructor() {
        Book book = new Book(1, "Начало", "Автор", "Исток", 1, 1, 0, "Мягкий");
        assertEquals(new State(1, "Начало", List.of("Автор"), "Исток", 1, 1, 0, "Мягкий"), State.of(book));
    }

    @Test @DisplayName("Изменение id")
    void changeId() { change(b -> b.id(88), new State(88, "Массивы", List.of("Анна", "Борис"), "Свет", 2024, 312, 19.75, "Твёрдый")); }

    @Test @DisplayName("Изменение названия")
    void changeTitle() { change(b -> b.title("Объекты"), new State(17, "Объекты", List.of("Анна", "Борис"), "Свет", 2024, 312, 19.75, "Твёрдый")); }

    @Test @DisplayName("Изменение авторов")
    void changeAuthors() { change(b -> b.authors(new String[] {"Вера", "Глеб"}), new State(17, "Массивы", List.of("Вера", "Глеб"), "Свет", 2024, 312, 19.75, "Твёрдый")); }

    @Test @DisplayName("Изменение издательства")
    void changePublisher() { change(b -> b.publisher("Мир"), new State(17, "Массивы", List.of("Анна", "Борис"), "Мир", 2024, 312, 19.75, "Твёрдый")); }

    @Test @DisplayName("Изменение года")
    void changeYear() { change(b -> b.year(9999), new State(17, "Массивы", List.of("Анна", "Борис"), "Свет", 9999, 312, 19.75, "Твёрдый")); }

    @Test @DisplayName("Изменение страниц")
    void changePages() { change(b -> b.pages(1), new State(17, "Массивы", List.of("Анна", "Борис"), "Свет", 2024, 1, 19.75, "Твёрдый")); }

    @Test @DisplayName("Изменение цены")
    void changePrice() { change(b -> b.price(0), new State(17, "Массивы", List.of("Анна", "Борис"), "Свет", 2024, 312, 0, "Твёрдый")); }

    @Test @DisplayName("Изменение переплёта")
    void changeBinding() { change(b -> b.binding("Мягкий"), new State(17, "Массивы", List.of("Анна", "Борис"), "Свет", 2024, 312, 19.75, "Мягкий")); }

    @Test @DisplayName("Отбор по автору: порядок и повторное вхождение")
    void authorOrderAndMultiplicity(TestReporter publicReporter) {
        { Book b1=new Book(1,"Первая",new String[]{"Ann","Bob"},"Север",2001,100,10,"Твёрдый"); Book b2=new Book(2,"Вторая","Ann","Юг",2020,100,10,"Твёрдый"); Book b3=new Book(3,"Третья","Bob","Север",2020,100,10,"Твёрдый"); Book[] publicBooks = new Book[]{b1,b2,b1,b3};
          selection(publicBooks, a -> { Book[] selected = Book.author(a, "Ann");
            publicReporter.publishEntry(Map.of("public-case", "normal-author", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          }, 0, 1, 2);
        }
 selection(catalog(), a -> Book.author(a, "Анна"), 0, 1, 2); }

    @Test @DisplayName("Автор не первый в списке")
    void nonFirstAuthor(TestReporter publicReporter) {
        { Book first = new Book(1, "Массивы", new String[]{"Анна", "Борис"}, "Свет", 2024, 120, 10, "Мягкий"); Book second = new Book(2, "Объекты", "Вера", "Мир", 2025, 200, 15, "Твёрдый"); Book[] publicBooks = new Book[]{first,second};
          selection(publicBooks, a -> { Book[] selected = Book.author(a, "Борис");
            publicReporter.publishEntry(Map.of("public-case", "demo-example", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          }, 0);
        }

        Book[] books = {new Book(2, "Двое", new String[] {"Первый", "Искомый"}, "Печать", 2001, 20, 3, "Мягкий")};
        selection(books, a -> Book.author(a, "Искомый"), 0);
    }

    @Test @DisplayName("Автор: точное совпадение с учётом регистра")
    void authorExactCase() { selection(catalog(), a -> Book.author(a, "анна"), 3); }

    @Test @DisplayName("Неизвестный автор")
    void unknownAuthor(TestReporter publicReporter) {
        { Book b1=new Book(1,"Первая",new String[]{"Ann","Bob"},"Север",2001,100,10,"Твёрдый"); Book b2=new Book(2,"Вторая","Ann","Юг",2020,100,10,"Твёрдый"); Book b3=new Book(3,"Третья","Bob","Север",2020,100,10,"Твёрдый"); Book[] publicBooks = new Book[]{b3};
          selection(publicBooks, a -> { Book[] selected = Book.author(a, "Ann");
            publicReporter.publishEntry(Map.of("public-case", "boundary-author", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          });
        }
 selection(catalog(), a -> Book.author(a, "Ан")); }

    @Test @DisplayName("Автор: пустой массив")
    void authorEmptyArray() { selection(new Book[0], a -> Book.author(a, "Анна")); }

    @Test @DisplayName("Отбор по издательству: порядок и повторное вхождение")
    void publisherOrderAndMultiplicity(TestReporter publicReporter) {
        { Book b1=new Book(1,"Первая",new String[]{"Ann","Bob"},"Север",2001,100,10,"Твёрдый"); Book b2=new Book(2,"Вторая","Ann","Юг",2020,100,10,"Твёрдый"); Book b3=new Book(3,"Третья","Bob","Север",2020,100,10,"Твёрдый"); Book[] publicBooks = new Book[]{b1,b2,b1,b3};
          selection(publicBooks, a -> { Book[] selected = Book.publisher(a, "Север");
            publicReporter.publishEntry(Map.of("public-case", "normal-publisher", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          }, 0, 2, 3);
        }
 selection(catalog(), a -> Book.publisher(a, "Свет"), 0, 1, 2); }

    @Test @DisplayName("Издательство: точное совпадение с учётом регистра")
    void publisherExactCase() { selection(catalog(), a -> Book.publisher(a, "свет"), 3); }

    @Test @DisplayName("Неизвестное издательство")
    void unknownPublisher(TestReporter publicReporter) {
        { Book b1=new Book(1,"Первая",new String[]{"Ann","Bob"},"Север",2001,100,10,"Твёрдый"); Book b2=new Book(2,"Вторая","Ann","Юг",2020,100,10,"Твёрдый"); Book b3=new Book(3,"Третья","Bob","Север",2020,100,10,"Твёрдый"); Book[] publicBooks = new Book[]{b3};
          selection(publicBooks, a -> { Book[] selected = Book.publisher(a, "север");
            publicReporter.publishEntry(Map.of("public-case", "boundary-publisher", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          });
        }
 selection(catalog(), a -> Book.publisher(a, "Св")); }

    @Test @DisplayName("Издательство: пустой массив")
    void publisherEmptyArray() { selection(new Book[0], a -> Book.publisher(a, "Свет")); }

    @Test @DisplayName("Отбор после года: порядок и повторное вхождение")
    void yearOrderAndMultiplicity(TestReporter publicReporter) {
        { Book b1=new Book(1,"Первая",new String[]{"Ann","Bob"},"Север",2001,100,10,"Твёрдый"); Book b2=new Book(2,"Вторая","Ann","Юг",2020,100,10,"Твёрдый"); Book b3=new Book(3,"Третья","Bob","Север",2020,100,10,"Твёрдый"); Book[] publicBooks = new Book[]{b1,b2,b1,b3};
          selection(publicBooks, a -> { Book[] selected = Book.after(a, 2010);
            publicReporter.publishEntry(Map.of("public-case", "normal-after", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          }, 1, 3);
        }
 selection(catalog(), a -> Book.after(a, 2024), 0, 2, 4); }

    @Test @DisplayName("Год равен границе")
    void yearEqualBoundary(TestReporter publicReporter) {
        { Book b1=new Book(1,"Первая",new String[]{"Ann","Bob"},"Север",2001,100,10,"Твёрдый"); Book b2=new Book(2,"Вторая","Ann","Юг",2020,100,10,"Твёрдый"); Book b3=new Book(3,"Третья","Bob","Север",2020,100,10,"Твёрдый"); Book[] publicBooks = new Book[]{b3};
          selection(publicBooks, a -> { Book[] selected = Book.after(a, 2020);
            publicReporter.publishEntry(Map.of("public-case", "boundary-after", "actual", selected == null ? "null" : Arrays.toString(Arrays.stream(selected).map(b -> b == null ? null : b.id()).toArray())));
            return selected;
          });
        }

        Book[] books = {new Book(1, "Граница", "Автор", "Печать", 2024, 1, 0, "Мягкий")};
        selection(books, a -> Book.after(a, 2024));
    }

    @Test @DisplayName("После границы нет книг")
    void noLaterBooks() { selection(catalog(), a -> Book.after(a, 9999)); }

    @Test @DisplayName("Год: пустой массив")
    void yearEmptyArray() { selection(new Book[0], a -> Book.after(a, 1)); }
}
