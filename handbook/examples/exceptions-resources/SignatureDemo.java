import java.io.IOException;

public class SignatureDemo {
    interface TextSource {
        String read() throws IOException;
    }

    static final class ConstantSource implements TextSource {
        @Override public String read() { return "90"; }
    }

    static final class Quiet implements AutoCloseable {
        @Override public void close() {}
    }

    static void concrete() {
        try (var resource = new Quiet()) {}
    }

    static void throughInterface() throws Exception {
        AutoCloseable resource = new Quiet();
        try (resource) {}
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new ConstantSource().read());
        TextSource source = new ConstantSource();
        System.out.println(source.read());
        concrete();
        throughInterface();
        System.out.println("оба ресурса закрыты");
    }
}
