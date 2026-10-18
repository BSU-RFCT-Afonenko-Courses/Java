package course.examples.dispatch;

public final class RecordingSender implements Sender {
    private final StringBuilder text = new StringBuilder();

    @Override
    public void send(String message) {
        text.append(java.util.Objects.requireNonNull(message, "message"));
        text.append('\n');
    }

    public String text() {
        return text.toString();
    }
}
