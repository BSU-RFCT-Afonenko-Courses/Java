public class Book {
    public Book(int id, String title, String[] authors, String publisher, int year, int pages, double price, String binding) {
        // TODO: спроектируйте и сохраните состояние книги.
    }

    public Book(int id, String title, String author, String publisher, int year, int pages, double price, String binding) {
        // TODO: спроектируйте и сохраните состояние книги.
    }

    public int id() { throw new UnsupportedOperationException("Реализуйте чтение: id"); }
    public void id(int value) { throw new UnsupportedOperationException("Реализуйте изменение: id"); }

    public String title() { throw new UnsupportedOperationException("Реализуйте чтение: title"); }
    public void title(String value) { throw new UnsupportedOperationException("Реализуйте изменение: title"); }

    public String[] authors() { throw new UnsupportedOperationException("Реализуйте чтение: authors"); }
    public void authors(String[] value) { throw new UnsupportedOperationException("Реализуйте изменение: authors"); }

    public String publisher() { throw new UnsupportedOperationException("Реализуйте чтение: publisher"); }
    public void publisher(String value) { throw new UnsupportedOperationException("Реализуйте изменение: publisher"); }

    public int year() { throw new UnsupportedOperationException("Реализуйте чтение: year"); }
    public void year(int value) { throw new UnsupportedOperationException("Реализуйте изменение: year"); }

    public int pages() { throw new UnsupportedOperationException("Реализуйте чтение: pages"); }
    public void pages(int value) { throw new UnsupportedOperationException("Реализуйте изменение: pages"); }

    public double price() { throw new UnsupportedOperationException("Реализуйте чтение: price"); }
    public void price(double value) { throw new UnsupportedOperationException("Реализуйте изменение: price"); }

    public String binding() { throw new UnsupportedOperationException("Реализуйте чтение: binding"); }
    public void binding(String value) { throw new UnsupportedOperationException("Реализуйте изменение: binding"); }

    @Override
    public String toString() { throw new UnsupportedOperationException("Реализуйте представление книги"); }

    public static Book[] author(Book[] books, String value) {
        throw new UnsupportedOperationException("Реализуйте отбор: author");
    }

    public static Book[] publisher(Book[] books, String value) {
        throw new UnsupportedOperationException("Реализуйте отбор: publisher");
    }

    public static Book[] after(Book[] books, int value) {
        throw new UnsupportedOperationException("Реализуйте отбор: after");
    }

}
