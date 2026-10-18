import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadOne {
    public static void main(String[] args) throws IOException {
        InputStreamReader input = new InputStreamReader(
            System.in, StandardCharsets.UTF_8);
        int value = input.read();
        if (value == -1) {
            System.out.println("Конец ввода");
        } else {
            System.out.println("Код UTF-16: " + value);
        }
    }
}
