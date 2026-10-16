public class Main {
    public static void main(String[] args) throws Exception {
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        int n = input.nextInt(), m = input.nextInt(), k = input.nextInt();
        boolean inside = n <= k && k <= m;
        System.out.println(inside + " " + inside + " " + inside + " " + inside);
    }
}
