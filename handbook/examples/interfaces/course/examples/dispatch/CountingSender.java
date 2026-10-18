package course.examples.dispatch;

final class CountingSender implements Sender, Counted {
    private final Sender target;
    private int count;

    CountingSender(Sender target) {
        this.target = java.util.Objects.requireNonNull(target, "target");
    }

    @Override
    public void send(String message) {
        target.send(java.util.Objects.requireNonNull(message, "message"));
        count++;
    }

    @Override
    public int count() { return count; }
}
