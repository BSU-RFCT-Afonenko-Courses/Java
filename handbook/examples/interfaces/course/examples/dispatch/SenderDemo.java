package course.examples.dispatch;

public class SenderDemo {
    public static void main(String[] args) {
        Sender sender = new ConsoleSender();
        sender.send("A");
        RecordingSender recording = new RecordingSender();
        sender = recording;
        sender.send("B");
        System.out.print(recording.text());

        RecordingSender shared = new RecordingSender();
        Reminder first = new Reminder(shared);
        Reminder second = new Reminder(shared);
        first.remind("Java");
        second.remind("C++");
        System.out.print(shared.text());

        RecordingSender target = new RecordingSender();
        Sender prefixed = new PrefixSender(target, "[library] ");
        new Reminder(prefixed).remind("Java");
        System.out.print(target.text());

        RecordingSender order = new RecordingSender();
        Sender wrapped = new PrefixSender(new PrefixSender(order, "B:"), "A:");
        wrapped.send("X");
        System.out.print(order.text());

        CountingSender object = new CountingSender(new RecordingSender());
        Sender sending = object;
        Counted statistics = object;
        sending.send("A");
        sending.send("B");
        System.out.println(statistics.count());

        RecordingSender partial = new RecordingSender();
        try {
            partial.sendAll(new String[]{"A", null, "B"});
        } catch (NullPointerException ex) {
            System.out.print(partial.text());
        }

        Sender console = Sender.console();
        console.sendMarked("A");
        console.sendWarning("B");
    }
}
