import java.util.Arrays;
import java.util.Objects;

public class ArrayRecordsDemo {
    record Vector(int[] values) {}
    record Draft(int[] scores) {}

    record Scores(int[] values) {
        Scores { values = Objects.requireNonNull(values).clone(); }
        public int[] values() { return values.clone(); }
        @Override public boolean equals(Object other) {
            return other instanceof Scores that && Arrays.equals(values, that.values);
        }
        @Override public int hashCode() { return Arrays.hashCode(values); }
        @Override public String toString() {
            return "Scores[values=" + Arrays.toString(values) + "]";
        }
    }

    public static void main(String[] args) {
        int[] input = {1, 2};
        Vector vector = new Vector(input);
        input[0] = 7;
        System.out.println(vector.values()[0]);
        vector.values()[1] = 9;
        System.out.println(input[1]);
        System.out.println(vector.values() == input);
        Vector a = new Vector(new int[]{1, 2});
        Vector b = new Vector(new int[]{1, 2});
        System.out.println(a.equals(b));

        int[] original = {80};
        Draft draft = new Draft(original);
        original[0] = 90;
        System.out.println(draft.scores()[0]);

        int[] source = {80};
        Scores saved = new Scores(source);
        source[0] = 90;
        int[] output = saved.values();
        output[0] = 100;
        System.out.println(saved.values()[0]);
        Scores copy = new Scores(saved.values());
        System.out.println(saved.equals(copy));
        System.out.println(saved.hashCode() == copy.hashCode());
        System.out.println(saved.equals(new Scores(new int[]{81})));
        System.out.println(saved.equals(null));
        System.out.println(saved.equals("80"));
        System.out.println(saved);
        System.out.println(copy);
        System.out.println(saved.toString().equals(copy.toString()));
    }
}
