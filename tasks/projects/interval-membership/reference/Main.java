public class Main {
    public static void main(String[] args) throws Exception {
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        int n = input.nextInt();
        int m = input.nextInt();
        int k = input.nextInt();
        System.out.println((n < k && k <= m) + " " + (n <= k && k < m) + " "
            + (n < k && k < m) + " " + (n <= k && k <= m));
    }
}
