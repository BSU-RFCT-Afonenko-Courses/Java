import java.io.IOException;

public class ResourceDemo {
    static final class Trace implements AutoCloseable {
        final String name;
        Trace(String name) {
            this.name = name;
            System.out.println("open " + name);
        }
        @Override public void close() {
            System.out.println("close " + name);
        }
    }

    static final class Probe implements AutoCloseable {
        final String name;
        final boolean failClose;
        Probe(String name, boolean failOpen, boolean failClose) throws IOException {
            if (failOpen) throw new IOException("open " + name);
            this.name = name;
            this.failClose = failClose;
            System.out.println("open " + name);
        }
        @Override public void close() throws IOException {
            System.out.println("close " + name);
            if (failClose) throw new IOException("close " + name);
        }
    }

    static final class Failing implements AutoCloseable {
        @Override public void close() throws IOException {
            throw new IOException("close");
        }
    }

    static final class Resource implements AutoCloseable {
        @Override public void close() throws IOException {
            System.out.println("close");
            throw new IOException("cleanup");
        }
    }

    static void report(Throwable failure) {
        System.out.println("primary: " + failure.getMessage());
        System.out.println("cause: "
            + (failure.getCause() == null ? "null" : failure.getCause().getMessage()));
        for (Throwable extra : failure.getSuppressed()) {
            System.out.println("suppressed: " + extra.getMessage());
        }
    }

    static void order() {
        try (var a = new Trace("A"); var b = new Trace("B")) {
            System.out.println("body");
        }
    }

    static void partial(boolean failFirstClose) {
        try (var a = new Probe("A", false, failFirstClose);
             var b = new Probe("B", true, false)) {
            System.out.println("body");
        } catch (IOException failure) {
            report(failure);
        }
    }

    static void closeFailures(boolean failBody) {
        try (var a = new Probe("A", false, true);
             var b = new Probe("B", false, true)) {
            System.out.println("body");
            if (failBody) throw new IllegalStateException("body");
        } catch (IOException | IllegalStateException failure) {
            report(failure);
        }
    }

    static void causeClose() {
        try (var resource = new Resource()) {
            throw new IOException("read", new IllegalArgumentException("bad bytes"));
        } catch (IOException failure) {
            report(failure);
        }
    }

    static ExceptionDemo.ScoreResult resultBeforeClose() throws IOException {
        try (var resource = new Failing()) {
            return new ExceptionDemo.ScoreFailure(ExceptionDemo.ScoreProblem.NOT_INTEGER);
        }
    }

    static ExceptionDemo.ScoreResult exceptionBeforeClose()
            throws IOException, ExceptionDemo.DataFormatException {
        try (var resource = new Failing()) {
            throw new ExceptionDemo.DataFormatException("неверный балл");
        }
    }

    static void returnClose() {
        try {
            System.out.println(resultBeforeClose());
        } catch (IOException failure) {
            report(failure);
            System.out.println("suppressed count: " + failure.getSuppressed().length);
        }
        try {
            System.out.println(exceptionBeforeClose());
        } catch (IOException | ExceptionDemo.DataFormatException failure) {
            report(failure);
        }
    }

    static void nullResource() {
        try (Trace absent = null; var present = new Trace("B")) {
            System.out.println("body");
        }
    }

    static void existing() {
        var resource = new Trace("A");
        try (resource) {
            System.out.println("body");
        }
        System.out.println("имя доступно: " + resource.name);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Укажите опыт: order, partial, partial-close, body-close, close-only, cause-close, return-close, null, existing");
            return;
        }
        switch (args[0]) {
            case "order" -> order();
            case "partial" -> partial(false);
            case "partial-close" -> partial(true);
            case "body-close" -> closeFailures(true);
            case "close-only" -> closeFailures(false);
            case "cause-close" -> causeClose();
            case "return-close" -> returnClose();
            case "null" -> nullResource();
            case "existing" -> existing();
            default -> throw new IllegalArgumentException("неизвестный опыт: " + args[0]);
        }
    }
}
