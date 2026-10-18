package course.examples.dispatch;

public class DispatchStatsDemo {
    record DispatchStats(int count) implements Counted {}

    static void printCount(Counted value) {
        System.out.println(value.count());
    }

    public static void main(String[] args) {
        CountingSender sender = new CountingSender(new RecordingSender());
        sender.send("A");
        sender.send("B");
        DispatchStats saved = new DispatchStats(sender.count());
        sender.send("C");
        printCount(sender);
        printCount(saved);
    }
}
