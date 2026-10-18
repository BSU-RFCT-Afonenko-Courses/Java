package course.examples.dispatch;

public class SenderContractDemo {
    static final class NonEmptySender implements Sender {
        @Override
        public void send(String message) {
            java.util.Objects.requireNonNull(message);
            if (message.isEmpty()) {
                throw new IllegalArgumentException("Пустое сообщение");
            }
            System.out.println(message);
        }
    }

    static void sendEmpty(Sender sender) {
        sender.send("");
    }

    public static void main(String[] args) {
        RecordingSender log = new RecordingSender();
        sendEmpty(log);
        System.out.println(log.text().length());
        try {
            sendEmpty(new NonEmptySender());
        } catch (IllegalArgumentException ex) {
            System.out.println("Пустая строка отвергнута");
        }
    }
}
