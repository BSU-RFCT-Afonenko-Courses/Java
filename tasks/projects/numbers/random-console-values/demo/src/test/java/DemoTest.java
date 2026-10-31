import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
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
    private static int[][] views() throws Exception {
        String[] lines=run().lines().filter(s->!s.isBlank()).toArray(String[]::new);
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
    private static void sequence() throws Exception {
        int[][] values=views();int[] expected={30,63,48,84,70,25,5,18,19,93};
        assertArrayEquals(expected,values[0],"Последовательность Random(42).nextInt(100)");
        assertArrayEquals(expected,values[1],"Обе формы сохраняют ту же последовательность");
    }
    @Test @DisplayName("Горизонтальное представление последовательности") void horizontal() throws Exception { assertArrayEquals(new int[]{30,63,48,84,70,25,5,18,19,93},views()[0]); }
    @Test @DisplayName("Вертикальное представление той же последовательности") void vertical() throws Exception { assertArrayEquals(new int[]{30,63,48,84,70,25,5,18,19,93},views()[1]); }
}
