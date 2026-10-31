import java.lang.String;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PublicBookDemoTest {
    @Test @DisplayName("Необязательный открытый пример отбора по автору")
    void example() {
        Book first = new Book(1, "Массивы", new String[] {"Анна", "Борис"}, "Свет", 2024, 120, 10, "Мягкий");
        Book second = new Book(2, "Объекты", "Вера", "Мир", 2025, 200, 15, "Твёрдый");
        Book[] selected = Book.author(new Book[] {first, second}, "Борис");
        assertEquals(1, selected.length, "Автор может стоять не первым в списке");
        assertEquals(1, selected[0].id(), "В примере подходит первая книга");
    }
}
