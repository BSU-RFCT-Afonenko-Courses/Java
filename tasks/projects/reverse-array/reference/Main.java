public class Main {
    public static void main(String[] args) throws Exception {
        int[] values = {7, -2, 7, 0};
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (input.hasNextInt()) {
            int n = input.nextInt();
            values = new int[n];
            for (int i = 0; i < n; i++) values[i] = input.nextInt();
        }
        for (int i = values.length - 1; i >= 0; i--) {
            System.out.print(values[i] + " ");
        }
        System.out.println();
    }
}
