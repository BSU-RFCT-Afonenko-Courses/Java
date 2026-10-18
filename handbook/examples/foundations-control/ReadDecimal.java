import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class ReadDecimal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);
        input.useLocale(Locale.ROOT);
        if (input.hasNextDouble()) {
            double value = input.nextDouble();
            if (Double.isFinite(value)) {
                System.out.println(value + 1.0);
            } else {
                System.out.println("Неконечное число");
            }
        } else if (input.hasNext()) {
            System.out.println("Не число: " + input.next());
        } else if (input.ioException() != null) {
            System.out.println("Ошибка ввода");
        } else {
            System.out.println("Конец ввода");
        }
    }
}
