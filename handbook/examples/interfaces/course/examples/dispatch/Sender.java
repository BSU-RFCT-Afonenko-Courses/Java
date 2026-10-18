package course.examples.dispatch;

public interface Sender {
    void send(String message);

    default void sendAll(String[] messages) {
        java.util.Objects.requireNonNull(messages, "messages");
        for (String message : messages) {
            send(message);
        }
    }
    static Sender console() {
        return new ConsoleSender();
    }

    default void sendMarked(String message) {
        sendPrepared("[notice] ", message);
    }

    default void sendWarning(String message) {
        sendPrepared("[warning] ", message);
    }

    private void sendPrepared(String prefix, String message) {
        send(prefix + checked(message));
    }

    private static String checked(String message) {
        return java.util.Objects.requireNonNull(message, "message");
    }
}
