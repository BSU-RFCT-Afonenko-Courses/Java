public class CollectionsCopyDemo {
    public static void main(String[] args) {
        TaggedBook first = new TaggedBook();
        TaggedBook copy = new TaggedBook();
        first.tags.add("java");
        copy.tags = first.tags;
        copy.tags.add("oop");
        System.out.println(first.tags); // [java, oop]

        var guests = new GuestList(java.util.List.of("Ann"));
        var snapshot = guests.snapshot();
        var view = guests.view();
        guests.add("Bob");
        System.out.println(snapshot); // [Ann]
        System.out.println(view); // [Ann, Bob]
    }
}

class TaggedBook {
    java.util.List<String> tags = new java.util.ArrayList<>();
}

final class GuestList {
    private final java.util.List<String> names;
    GuestList(java.util.List<String> initial) {
        names = new java.util.ArrayList<>(initial);
    }
    void add(String name) { names.add(name); }
    java.util.List<String> snapshot() {
        return java.util.List.copyOf(names);
    }
    java.util.List<String> view() {
        return java.util.Collections.unmodifiableList(names);
    }
}
