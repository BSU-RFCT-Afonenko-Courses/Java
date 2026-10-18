import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

public final class BlockReadDemo {
    public static void main(String[] args) throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        byte[] buffer = new byte[4];
        try (var input = new ByteArrayInputStream(data)) {
            int count;
            while ((count = input.read(buffer)) != -1) {
                System.out.println("n = " + count + ", буфер = " + Arrays.toString(buffer));
                System.out.println("новые байты = "
                    + Arrays.toString(Arrays.copyOf(buffer, count)));
            }
            System.out.println("конец: " + count);
        }
    }
}
