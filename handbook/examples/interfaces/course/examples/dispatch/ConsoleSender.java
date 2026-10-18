package course.examples.dispatch;

public final class ConsoleSender implements Sender {
    @Override
    public void send(String message) {
        System.out.println(java.util.Objects.requireNonNull(message, "message"));
    }
}
