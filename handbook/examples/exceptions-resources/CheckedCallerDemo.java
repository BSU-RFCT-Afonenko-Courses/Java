public class CheckedCallerDemo {
    static class EmptyException extends Exception {}

    static int take(boolean empty) throws EmptyException {
        if (empty) throw new EmptyException();
        return 7;
    }

    static int forward() throws EmptyException {
        return take(false);
    }

    public static void main(String[] args) throws EmptyException {
        System.out.println(forward());
        try {
            System.out.println(take(true));
        } catch (EmptyException ex) {
            System.out.println("хранилище пусто");
        }
    }
}
