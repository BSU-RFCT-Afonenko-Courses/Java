public class ObjectsEntryDemo {
    public static void main(String[] args) {
        {
            Book first = new Book("Java");
            Book second = new Book("Algorithms");
            System.out.println(first.title);
            System.out.println(second.title);
            second.borrowed = true;
            System.out.println(first.borrowed);
            System.out.println(second.borrowed);
        }
        {
            Book pending;
            pending = new Book("Networks");
            System.out.println(pending.title);
            System.out.println(pending.borrowed);
        }
        {
            Book first = new Book("Java");
            Book same = first;
            same.borrowed = true;
            System.out.println(first.borrowed);
            System.out.println(first == same);
            Book other = new Book("Java");
            other.borrowed = true;
            System.out.println(first == other);
            same = new Book("Databases");
            System.out.println(first.title);
            System.out.println(first.borrowed);
            System.out.println(first == same);
        }
    }
}
