import java.lang.Exception;
import java.lang.Long;
import java.lang.String;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String... args) throws Exception {
        return publicOutput(publicReporter, publicCaseIds, JavaProcess.runCheckingUncaught(Main.class,"",args).stdout());
    }
    private static String[] tokens(String text) {
        return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");
    }
    private static void values(TestReporter publicReporter, String[] publicCaseIds, long sum,long product,String... args) throws Exception {
        String[] words=tokens(run(publicReporter, publicCaseIds, args));assertEquals(2,words.length,"Нужны сумма и произведение");
        for(String word:words)assertTrue(word.matches("[+-]?[0-9]+"),"Десятичные целые без подписей");
        assertEquals(sum,Long.parseLong(words[0]),"Сумма аргументов long");
        assertEquals(product,Long.parseLong(words[1]),"Произведение аргументов long");
    }
    @Test @DisplayName("Учитываются все аргументы разных знаков. Вход: 3 -2 4")
    void mixed_signs(TestReporter publicReporter) throws Exception { values(publicReporter, new String[]{"normal"}, 5L,-24L,"3","-2","4"); }
    @Test @DisplayName("Нейтральные элементы пустого набора. Вход: без аргументов")
    void empty(TestReporter publicReporter) throws Exception { values(publicReporter, new String[]{"boundary"}, 0L,1L); }
    @Test @DisplayName("Разбор long, а не int. Вход: 3000000000")
    void above_int() throws Exception { values(null, null, 3000000000L,3000000000L,"3000000000"); }
    @Test @DisplayName("Нижняя граница long без искусственного сужения. Вход: -9223372036854775808")
    void long_min() throws Exception { values(null, null, -9223372036854775808L,-9223372036854775808L,"-9223372036854775808"); }
    @Test @DisplayName("Ноль в непустом наборе отличается от отсутствия аргументов. Вход: 0 -1 1")
    void zero_present() throws Exception { values(null, null, 0L,0L,"0","-1","1"); }
    @Test @DisplayName("Нулевая сумма и отрицательное произведение. Вход: -1 1")
    void opposite_signs() throws Exception { values(null, null, 0L,-1L,"-1","1"); }
    @Test @DisplayName("Единственное значение входит в оба результата. Вход: 7")
    void single() throws Exception { values(null, null, 7L,7L,"7"); }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000) visible = visible.substring(0, 2000) + "…";
            for (String id : ids) reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
