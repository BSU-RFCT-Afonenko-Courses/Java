public class Main {
    public static void main(String[] args) throws Exception {
        double[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (input.hasNextDouble()) {
            for (int i = 0; i < values.length; i++) values[i] = input.nextDouble();
        }
        double sum = 0;
        for (double value : values) sum += value;
        System.out.println(sum / values.length);
    }
}
