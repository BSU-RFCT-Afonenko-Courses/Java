public class CauseCloseDemo {
    static final class Resource implements AutoCloseable {
        @Override public void close() throws java.io.IOException {
            System.out.println("close");
            throw new java.io.IOException("cleanup");
        }
    }

    public static void main(String[] args) {
        try (var resource = new Resource()) {
            throw new java.io.IOException("read",
                new IllegalArgumentException("bad bytes"));
        } catch (java.io.IOException failure) {
            System.out.println("primary: " + failure.getMessage());
            System.out.println("cause: " + failure.getCause().getMessage());
            System.out.println("suppressed: "
                + failure.getSuppressed()[0].getMessage());
        }
    }
}
