public class LectureDetailsDemo {
    static int max(int a, int b) {
        return a >= b ? a : b;
    }
    static String describe(int value) { return "int"; }
    static String describe(String value) { return "String"; }
    static void show(String value) { }
    static void show(Integer value) { }
    public static void main(String[] args) {
        Reservation room = new Reservation(30);
        room.reserve(8);
        System.out.println(room.available()); // 22
        try {
            room.reserve(25);
        } catch (IllegalArgumentException ex) {
            System.out.println("refused");
        }
        System.out.println(room.available()); // 22
        new Trace(); // field, block: 7, constructor: 7
        System.out.println(max(3, 8)); // 8
        System.out.println(max(9, 2)); // 9
        System.out.println(describe(3)); // int
        System.out.println(describe("3")); // String
        show((String) null);
        System.out.println(Numbers.add(2, 3)); // 5
        final int[] values = {1, 2};
        values[0] = 9;
        System.out.println(values[0]); // 9
        System.out.println(new Child(3).value); // 3
        try { new Child(-1); }
        catch (IllegalArgumentException ex) { System.out.println("refused"); }
    }
}

final class Reservation {
    private final int capacity;
    private int occupied;
    Reservation(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("positive capacity required");
        }
        this.capacity = capacity;
    }
    int available() { return capacity - occupied; }
    void reserve(int count) {
        if (count <= 0 || count > available()) {
            throw new IllegalArgumentException("invalid size");
        }
        occupied += count;
    }
}

class Trace {
    private int value = init();
    { System.out.println("block: " + value); }
    private int init() {
        System.out.println("field");
        return 7;
    }
    Trace() { System.out.println("constructor: " + value); }
}

class Numbers {
    static int add(int a, int b) { return a + b; }
}

class Parent {
    final int value;
    Parent(int value) { this.value = value; }
}

class Child extends Parent {
    Child(int value) {
        if (value < 0) throw new IllegalArgumentException();
        super(value);
    }
}
