import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class QueueDemo {
    public static void main(String[] args) {
        Deque<String> queue = new ArrayDeque<>();
        queue.addLast("Ada");
        queue.addLast("Lin");
        System.out.println(queue.pollFirst());
        System.out.println(queue.pollFirst());
        System.out.println(queue.pollFirst());
        try { queue.removeFirst(); }
        catch (java.util.NoSuchElementException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        queue.push("Ada");
        queue.push("Lin");
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        try { queue.offer(null); }
        catch (NullPointerException ex) {
            System.out.println(ex.getClass().getSimpleName());
        }
        var priorities = new PriorityQueue<Integer>();
        priorities.add(3);
        priorities.add(1);
        priorities.add(2);
        priorities.add(1);
        while (!priorities.isEmpty()) System.out.println(priorities.poll());
    }
}
