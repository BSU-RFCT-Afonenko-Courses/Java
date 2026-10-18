public class FinallyDemo {
    static int value() {
        try { return 10; }
        finally { System.out.println("выход из метода"); }
    }

    static int replacedReturn() {
        try { return 10; }
        finally { return 20; }
    }

    static int swallowedFailure() {
        try { throw new IllegalStateException("body"); }
        finally { return 20; }
    }

    static void replacedFailure() {
        try { throw new IllegalStateException("body"); }
        finally { throw new IllegalArgumentException("cleanup"); }
    }

    public static void main(String[] args) {
        System.out.println(value());
        System.out.println(replacedReturn());
        System.out.println(swallowedFailure());
        try {
            replacedFailure();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getSuppressed().length);
        }
    }
}
