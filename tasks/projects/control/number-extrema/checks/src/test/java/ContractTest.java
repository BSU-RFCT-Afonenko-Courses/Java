import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertEquals(expected.strip().replaceAll("\\p{javaWhitespace}+", " "), actual.strip().replaceAll("\\p{javaWhitespace}+", " "), "Сопоставьте подписи и значения экстремумов"); }
    @Test void published() throws Exception { exact("Максимум: 7\nМинимум: -3", run("4 7 -3 0 7")); }
    @Test void negative_only() throws Exception { exact("Максимум: -4\nМинимум: -31", run("4 -19 -31 -4 -8")); }
    @Test void positive_only() throws Exception { exact("Максимум: 37\nМинимум: 4", run("4 12 37 4 22")); }
    @Test void single() throws Exception { exact("Максимум: -731\nМинимум: -731", run("1 -731")); }
    @Test void repeated() throws Exception { exact("Максимум: 56\nМинимум: 56", run("4 56 56 56 56")); }
    @Test void endpoints() throws Exception { exact("Максимум: 2147483647\nМинимум: -2147483648", run("3 2147483647 -7 -2147483648")); }
    @Test void middle_extremes() throws Exception { exact("Максимум: 80\nМинимум: -44", run("5 15 -44 80 2 3")); }
    @Test void first_extremes() throws Exception { exact("Максимум: 2147483647\nМинимум: -2147483648", run("3 -2147483648 2147483647 5")); }
    @Test void zero() throws Exception { exact("Количество должно быть положительным", run("0")); }
    @Test void negative_count() throws Exception { exact("Количество должно быть положительным", run("-13")); }
    @Test void minimum_count() throws Exception { exact("Количество должно быть положительным", run("-2147483648")); }
}
