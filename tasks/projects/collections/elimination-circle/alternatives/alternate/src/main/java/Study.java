import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static List<Integer> arrayProcess(int count) {
        var r = new Variant().apply(count, false);
        var a = new ArrayList<>(r.order());
        a.add(r.last());
        return a;
    }

    public static List<Integer> linkedProcess(int count) {
        var r = new Variant().apply(count, true);
        var a = new ArrayList<>(r.order());
        a.add(r.last());
        return a;
    }
}

class Variant {
    public record Answer(List<Integer> order, int last) {}

    public Answer apply(int n, boolean linked) {
        List<Integer> people = linked ? new LinkedList<>() : new ArrayList<>();
        for (int i = 1; i <= n; i++) people.add(i);
        var gone = new LinkedList<Integer>();
        boolean remove = false;
        var it = people.listIterator();
        while (people.size() > 1) {
            if (!it.hasNext()) it = people.listIterator();
            int person = it.next();
            if (remove) {
                gone.add(person);
                it.remove();
            }
            remove = !remove;
        }
        return new Answer(gone, people.getFirst());
    }

    public static void main(String[] args) {
        var v = new Variant();
        for (int i = 0; i < 12; i++) {
            v.apply(300, false);
            v.apply(300, true);
        }
        for (int n : new int[] {300, 900})
            for (boolean linked : new boolean[] {false, true}) {
                long[] samples = new long[4];
                for (int r = 0; r < samples.length; r++) {
                    long start = System.nanoTime();
                    v.apply(n, linked);
                    samples[r] = System.nanoTime() - start;
                }
                System.out.println(
                        "N="
                                + n
                                + " "
                                + (linked ? "LinkedList" : "ArrayList")
                                + " наносекунды="
                                + Arrays.toString(samples)
                                + "; создание включено, вывод исключён");
            }
    }
}
