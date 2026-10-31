import java.lang.Exception;
import java.lang.Integer;
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
    private static int[][] views(TestReporter publicReporter, String[] publicCaseIds) throws Exception {
        String[] lines=run(publicReporter, publicCaseIds).lines().filter(s->!s.isBlank()).toArray(String[]::new);
        assertEquals(11,lines.length,"Нужны одна горизонтальная и десять вертикальных строк");
        int horizontal=tokens(lines[0]).length==10?0:10;
        int[][] values=new int[2][10]; int next=0;
        for(int row=0;row<11;row++) {
            String[] words=tokens(lines[row]);
            assertEquals(row==horizontal?10:1,words.length,"Числа в строке соответствуют выбранной форме");
            for(int i=0;i<words.length;i++) {
                assertTrue(words[i].matches("[+-]?[0-9]+"),"Нужно десятичное целое");
                int value=Integer.parseInt(words[i]);assertTrue(value>=0&&value<100,"Диапазон 0..99");
                if(row==horizontal)values[0][i]=value;else values[1][next++]=value;
            }
        }
        return values;
    }
    private static void sequence(TestReporter publicReporter, String[] publicCaseIds) throws Exception {
        int[][] values=views(publicReporter, publicCaseIds);int[] expected={30,63,48,84,70,25,5,18,19,93};
        assertArrayEquals(expected,values[0],"Последовательность Random(42).nextInt(100)");
        assertArrayEquals(expected,values[1],"Обе формы сохраняют ту же последовательность");
    }
    @Test @DisplayName("Одна воспроизводимая последовательность в двух формах; порядок значений одинаковый. Вход: без аргументов")
    void same_ten_values_two_views(TestReporter publicReporter) throws Exception { sequence(publicReporter, new String[]{"normal","boundary"}); }

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
