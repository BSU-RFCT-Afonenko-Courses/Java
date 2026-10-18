public class ObjectsTailDemo {
    static void borrow(Book book) {
        book.borrowed = true;
    }
    static void replace(Book book) {
        book = new Book("Replacement");
    }
    static Book replacement() {
        Book book = new Book("Replacement");
        return book;
    }
    static Book same(Book book) { return book; }
    static Book copy(Book source) {
        Book result = new Book(source.title);
        result.borrowed = source.borrowed;
        return result;
    }
    static String titleOf(Book book) {
        java.util.Objects.requireNonNull(book, "book");
        return java.util.Objects.requireNonNull(book.title, "title");
    }
    static Book create() {
        Book local = new Book("Java");
        return local;
    }
    public static void main(String[] args) {
        Book original = new Book("Java");
        borrow(original);
        System.out.println(original.borrowed); // true
        replace(original);
        System.out.println(original.title); // Java
        original = replacement();
        System.out.println(original.title); // Replacement
        System.out.println(original.borrowed); // false
        System.out.println(same(original) == original); // true

        try { titleOf(null); }
        catch (NullPointerException ex) {
            System.out.println(ex.getMessage()); // book
        }
        try { titleOf(new Book(null)); }
        catch (NullPointerException ex) {
            System.out.println(ex.getMessage()); // title
        }
        System.out.println(titleOf(new Book("Java"))); // Java

        Book source = new Book("Java");
        source.borrowed = true;
        Book copied = copy(source);
        copied.borrowed = false;
        System.out.println(source == copied); // false
        System.out.println(source.borrowed); // true
        System.out.println(copied.borrowed); // false

        TaggedBook first = new TaggedBook(new String[]{"java", "oop"});
        TaggedBook copy = new TaggedBook(first.tags);
        copy.tags[0] = "changed";
        System.out.println(first.tags[0]); // changed
        System.out.println(first == copy); // false
        System.out.println(first.tags == copy.tags); // true
        TaggedBook independent = new TaggedBook(first.tags.clone());
        independent.tags[0] = "independent";
        System.out.println(first.tags[0]); // changed
        System.out.println(independent.tags[0]); // independent
        System.out.println(first.tags == independent.tags); // false

        Book kept = create();
        Book alias = kept;
        kept = null;
        System.out.println(alias.title); // Java
    }
}

class TaggedBook {
    String[] tags;
    TaggedBook(String[] tags) { this.tags = tags; }
}
