import java.util.*;
public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in).useLocale(Locale.ROOT);
        if (scanner.hasNextDouble()) {
            double x = scanner.nextDouble();
            System.out.println(x / 100 * 17);
        } else {
            double x = 100.0;
            System.out.println(0.0);
            System.out.println(x / 100 * 17);
        }
    }
}
