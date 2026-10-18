public class InterfaceApiDemo {
    public static void main(String[] args) {
        Storage storage = new MemoryStorage();
        Writable writer = storage;
        Readable reader = storage;
        writer.write("Java");
        System.out.println(reader.read());
        System.out.println(Limits.MAX_BATCH);
        SharedData.VALUES[0] = 9;
        System.out.println(SharedData.VALUES[0]);
        Source<String> source = () -> "Java";
        System.out.println(source.get());
    }
}

interface Readable { String read(); }
interface Writable { void write(String value); }
interface Storage extends Readable, Writable {}
final class MemoryStorage implements Storage {
    private String value = "";
    @Override public String read() { return value; }
    @Override public void write(String value) {
        this.value = java.util.Objects.requireNonNull(value, "value");
    }
}
interface Limits { int MAX_BATCH = 100; }
interface SharedData { int[] VALUES = {1, 2}; }
@FunctionalInterface
interface Source<T> { T get(); }
