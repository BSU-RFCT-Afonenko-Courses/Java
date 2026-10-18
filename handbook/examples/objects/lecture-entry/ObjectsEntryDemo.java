public class ObjectsEntryDemo {
    public static void main(String[] args) {
        Book first = new Book();
        System.out.println(first.title);
        System.out.println(first.borrowed);

        Book pending;
        pending = new Book();
        System.out.println(pending.title);

        first.title = "Java";
        Book second = new Book();
        second.title = "Algorithms";
        System.out.println(first.title);

        Book same = first;
        same.borrowed = true;
        System.out.println(first.borrowed);

        Book other = new Book();
        other.title = first.title;
        other.borrowed = first.borrowed;
        System.out.println(first == same);
        System.out.println(first == other);

        same = new Book();
        same.title = "Databases";
        System.out.println(first.title);
        System.out.println(first == same);
    }
}
