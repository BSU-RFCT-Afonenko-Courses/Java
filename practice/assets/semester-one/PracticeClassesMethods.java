// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeClassesMethods {
    public static void main(String[] args) {
        final class Readings {
            private final int[] values;
            Readings(int[] source) { values = source.clone(); }
            int[] snapshot() { return values.clone(); }
        }
        int[] input = {12, 18};
        Readings readings = new Readings(input);
        input[0] = 90;
        int[] output = readings.snapshot();
        output[1] = 80;
        System.out.println(readings.snapshot()[0]);
        System.out.println(readings.snapshot()[1]);
    }
}
