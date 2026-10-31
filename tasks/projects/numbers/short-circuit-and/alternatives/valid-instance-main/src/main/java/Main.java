public class Main {
    void main(String[] args) throws Exception {
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        int numerator = 7;
        int denominator = 0;
        if (input.hasNextInt()) {
            numerator = input.nextInt();
            denominator = input.nextInt();
        }
        boolean positive = denominator != 0 && numerator / denominator > 0;
        try { throw new ArithmeticException("handled"); }
        catch (ArithmeticException expected) { System.err.println("Exception in thread diagnostic"); }
        System.out.println(positive);
        System.exit(7);
    }
}
