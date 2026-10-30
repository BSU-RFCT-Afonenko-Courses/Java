import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertEquals(expected.strip().replaceAll("\\p{javaWhitespace}+", " "), actual.strip().replaceAll("\\p{javaWhitespace}+", " "), "Сопоставьте подписи и значения экстремумов"); }
    @Test void published() throws Exception { exact("Максимум: 7\nМинимум: -3", run("4 7 -3 0 7")); }
    @Test void zero() throws Exception { exact("Количество должно быть положительным", run("0")); }
}
