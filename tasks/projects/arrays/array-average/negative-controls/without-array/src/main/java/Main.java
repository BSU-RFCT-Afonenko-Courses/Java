public class Main {
    public static void main(String[] args) {
        var input = new java.util.Scanner(System.in).useLocale(java.util.Locale.ROOT);
        double sum = 0;
        if (input.hasNextDouble()) { for (int i=0;i<10;i++) sum += input.nextDouble(); }
        else sum = 55;
        System.out.println(sum / 10);
    }
}
