package course.examples.dispatch;

public final class Reminder {
    private final Sender sink;

    public Reminder(Sender sink) {
        this.sink = java.util.Objects.requireNonNull(sink, "sink");
    }

    public void remind(String title) {
        sink.send("Return: " + java.util.Objects.requireNonNull(title, "title"));
    }
}
