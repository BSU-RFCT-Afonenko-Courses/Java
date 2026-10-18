class BoxClient {
    static String read(Box<String> box) {
        return box.get();
    }
    public static void main(String[] args) {
        System.out.println(read(new Box<>("Java")));
    }
}

// Самостоятельный опыт со ссылками и проверкой содержимого коробки.
class ParameterDemo {
    static final class ObjectBox {
        private final Object value;
        ObjectBox(Object value) { this.value = value; }
        Object get() { return value; }
    }

    static final class Book { boolean borrowed; }

    public static void main(String[] args) {
        ObjectBox text = new ObjectBox("Java");
        System.out.println(((String) text.get()).length());
        try {
            ObjectBox number = new ObjectBox(25);
            String wrong = (String) number.get();
            System.out.println(wrong);
        } catch (ClassCastException failure) {
            System.out.println("ClassCastException");
        }

        Book book = new Book();
        Box<Book> stored = new Box<>(book);
        book.borrowed = true;
        System.out.println(stored.get().borrowed);
        System.out.println(stored.get() == book);
        Box<String> missing = new Box<>(null);
        System.out.println(missing.get() == null);
    }
}
