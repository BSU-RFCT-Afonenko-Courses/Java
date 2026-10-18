package course.examples.dispatch;

final class PrefixSender implements Sender {
    private final Sender target;
    private final String prefix;

    PrefixSender(Sender target, String prefix) {
        this.target = java.util.Objects.requireNonNull(target, "target");
        this.prefix = java.util.Objects.requireNonNull(prefix, "prefix");
    }

    @Override
    public void send(String message) {
        target.send(prefix + java.util.Objects.requireNonNull(message, "message"));
    }
}
