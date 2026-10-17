public class InheritanceApiDemo {
    abstract static class Item {
        private final String name;
        protected Item(String name) {
            this.name = java.util.Objects.requireNonNull(name);
        }
        protected String name() { return name; }
        public abstract String describe();
    }

    static final class PrintedItem extends Item {
        PrintedItem(String name) { super(name); }
        @Override public String describe() { return "Item: " + name(); }
    }

    static class Parent {
        Parent(int value) {}
    }

    static class Child extends Parent {
        private final int value;
        Child(int value) {
            if (value < 0) throw new IllegalArgumentException();
            this.value = value;
            super(value);
        }
        int value() { return value; }
    }

    sealed interface Result permits Ok, Failure {}
    record Ok(int value) implements Result {}
    record Failure(String reason) implements Result {}

    public static void main(String[] args) {
        Item item = new PrintedItem("Java");
        System.out.println(item.describe());
        Object value = "Java";
        if (value instanceof String text) {
            System.out.println(text.length());
        }
        try {
            Integer number = (Integer) value;
        } catch (ClassCastException error) {
            System.out.println(error.getClass().getSimpleName());
        }
        Object absent = null;
        System.out.println(absent instanceof String);
        System.out.println((String) absent);
        System.out.println(new Child(3).value());
        Result result = new Ok(7);
        System.out.println(result instanceof Ok);
    }
}
