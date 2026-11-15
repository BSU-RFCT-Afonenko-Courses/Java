public class Study {
    public static Object create(int k, String n) {
        return switch (k) {
            case 0 -> new Animal(n);
            case 1 -> new Dog(n);
            default -> new Puppy(n);
        };
    }

    public static String voice(Object a) {
        return ((Animal) a).voice();
    }

    public static String name(Object a) {
        return ((Animal) a).name();
    }

    public static String action(Object a, int k) {
        return switch (k) {
            case 0 -> ((Dog) a).run();
            case 1 -> ((Dog) a).bite();
            default -> ((Puppy) a).jump();
        };
    }
}

class Animal {
    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    protected String name() {
        return name;
    }

    public String voice() {
        return "Animal " + name;
    }

    public boolean equals(Object other) {
        return other != null
                && getClass() == other.getClass()
                && name.equals(((Animal) other).name);
    }

    public int hashCode() {
        return java.util.Objects.hash(getClass(), name);
    }

    public String toString() {
        return getClass().getSimpleName() + " " + name;
    }
}

class Dog extends Animal {
    public Dog(String n) {
        super(n);
    }

    public String voice() {
        return "Dog " + name();
    }

    public String run() {
        return "run " + name();
    }

    public String bite() {
        return "bite " + name();
    }
}

class Puppy extends Dog {
    public Puppy(String n) {
        super(n);
    }

    public String voice() {
        return "Puppy " + name();
    }

    public String jump() {
        return "jump " + name();
    }
}
