public class EarlyCheckedDemo {
    static class EmptyException extends Exception {
        EmptyException(String message) { super(message); }
    }

    static int take(boolean empty) throws EmptyException {
        if (empty) throw new EmptyException("empty");
        return 7;
    }

    static int forward(boolean empty) throws EmptyException {
        return take(empty);
    }

    public static void main(String[] args) throws EmptyException {
        System.out.println(take(false));
        try {
            System.out.println(forward(true));
            System.out.println("после успешного вызова");
        } catch (EmptyException failure) {
            System.out.println(failure.getMessage());
        }
        System.out.println("дальше");
    }
}
