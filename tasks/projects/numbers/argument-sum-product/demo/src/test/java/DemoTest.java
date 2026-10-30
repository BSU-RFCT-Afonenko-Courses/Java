import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    private static String run(String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class,"",args).stdout();
    }
    private static String[] tokens(String text) {
        return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");
    }
    private static void values(long sum,long product,String... args) throws Exception {
        String[] words=tokens(run(args));assertEquals(2,words.length,"Нужны сумма и произведение");
        for(String word:words)assertTrue(word.matches("[+-]?[0-9]+"),"Десятичные целые без подписей");
        assertEquals(sum,Long.parseLong(words[0]),"Сумма аргументов long");
        assertEquals(product,Long.parseLong(words[1]),"Произведение аргументов long");
    }
    @Test @DisplayName("Учитываются все аргументы разных знаков. Вход: 3 -2 4")
    void mixed_signs() throws Exception { values(5L,-24L,"3","-2","4"); }
    @Test @DisplayName("Нейтральные элементы пустого набора. Вход: без аргументов")
    void empty() throws Exception { values(0L,1L); }
}
