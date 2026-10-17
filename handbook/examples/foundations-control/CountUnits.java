import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class CountUnits {
    public static void main(String[] args) throws IOException {
        Reader input = new InputStreamReader(
            System.in, StandardCharsets.UTF_8);
        long count = 0;
        int unit;
        while ((unit = input.read()) != -1 && unit != '#') {
            count++;
        }
        System.out.println(count);
    }
}
