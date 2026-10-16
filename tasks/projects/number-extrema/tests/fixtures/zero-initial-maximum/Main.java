public class Main {
    public static void main(String[] args) throws Exception {
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        int n = input.nextInt();
        if (n <= 0) {
            System.out.println("Количество должно быть положительным");
            return;
        }
        int first = input.nextInt();
        int minimum = first;
        int maximum = 0;
        for (int i = 1; i < n; i++) {
            int value = input.nextInt();
            if (value < minimum) minimum = value;
            if (value > maximum) maximum = value;
        }
        System.out.println("Максимум: " + maximum);
        System.out.println("Минимум: " + minimum);
    }
}
