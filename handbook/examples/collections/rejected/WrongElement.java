import java.util.ArrayList;
import java.util.List;

public class WrongElement {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add(25); // Ошибка: Integer нельзя добавить как String.
    }
}
