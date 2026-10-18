import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ReadNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);
        if (input.hasNextInt()) {
            int count = input.nextInt();
            System.out.println(count);
        } else if (input.hasNext()) {
            System.out.println("Не целое число: " + input.next());
        } else if (input.ioException() != null) {
            System.out.println("Ошибка ввода");
        } else {
            System.out.println("Конец ввода");
        }
    }
}
