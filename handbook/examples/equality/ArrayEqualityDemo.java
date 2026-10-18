import java.util.Arrays;
import java.util.Objects;

public class ArrayEqualityDemo {
    private record RawScores(int[] values) {}

    private static final class ScoreVector {
        private final int[] values;

        ScoreVector(int[] values) {
            this.values = values.clone();
        }

        int[] values() {
            return values.clone();
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof ScoreVector vector
                && Arrays.equals(values, vector.values);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(values);
        }
    }

    public static void main(String[] args) {
        int[] first = {80, 90};
        int[] second = {80, 90};
        System.out.println(first.equals(second));
        System.out.println(Arrays.equals(first, second));
        var left = new RawScores(first);
        var right = new RawScores(second);
        System.out.println(left.equals(right));
        var shared = new RawScores(first);
        first[0] = 0;
        System.out.println(left.equals(shared));

        int[] input = {80, 90};
        var vector = new ScoreVector(input);
        var sameValue = new ScoreVector(new int[]{80, 90});
        input[0] = 0;
        vector.values()[1] = 0;
        System.out.println(vector.equals(sameValue));
        System.out.println(vector.hashCode() == sameValue.hashCode());

        int[][] one = {{80, 90}, {70}};
        int[][] two = {{80, 90}, {70}};
        System.out.println(Arrays.equals(one, two));
        System.out.println(Arrays.deepEquals(one, two));
        System.out.println(Arrays.deepHashCode(one) == Arrays.deepHashCode(two));

        System.out.println(Objects.equals(null, null));
        System.out.println(Objects.equals(null, "Ada"));
        System.out.println(Objects.hashCode(null));
    }
}
