public class ConsoleReminderDemo {
    public static void main(String[] args) {
        new ConsoleReminder().remind("Java");
    }
}

class ConsoleReminder {
    public void remind(String title) {
        send("Return: " + java.util.Objects.requireNonNull(title, "title"));
    }

    protected void send(String message) {
        System.out.println(java.util.Objects.requireNonNull(message, "message"));
    }
}
