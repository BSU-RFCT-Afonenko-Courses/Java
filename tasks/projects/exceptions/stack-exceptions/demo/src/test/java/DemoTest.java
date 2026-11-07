import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {

    @Test
    @DisplayName("LIFO для двух символов")
    void normal() throws Throwable {
        var s = new Study(2);
        s.push('A');
        s.push('Б');
        assertEquals('Б', s.pop());
        assertEquals('A', s.pop());
    }

    @Test
    @DisplayName("Переполнение, опустошение и новое помещение")
    void boundary() throws Throwable {
        var s = new Study(1);
        s.push((char) 0);
        assertThrows(Study.StackFullException.class, () -> s.push('X'));
        assertEquals((char) 0, s.pop());
        assertThrows(Study.StackEmptyException.class, () -> s.pop());
        s.push('Я');
        assertEquals('Я', s.pop());
    }
}
