import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    @DisplayName("Среднее десяти значений сохраняет дробную часть")
    void tenValuesHaveFractionalMean() throws Exception {
        assertNumbers(new double[]{5.5}, run("1 2 3 4 5 6 7 8 9 10"));
    }

    /** Each example runs as an ordinary, fresh Java program; student static state is never reused. */
    private static String run(String input, String... args) throws Exception {
        String javaExecutable = java.nio.file.Path.of(System.getProperty("java.home"), "bin", "java").toString();
        String studentClasspath = java.nio.file.Path.of(
            Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        var command = new java.util.ArrayList<String>(java.util.List.of(
            javaExecutable, "-Xmx128m", "-XX:TieredStopAtLevel=1", "-XX:ActiveProcessorCount=1",
            "-XX:-UsePerfData", "-XX:+DisableAttachMechanism", "--illegal-native-access=deny",
            "-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8",
            "-cp", studentClasspath, "Main"));
        java.util.Collections.addAll(command, args);
        Process process = new ProcessBuilder(command).start();
        var stdout = new java.io.ByteArrayOutputStream();
        var stderr = new java.io.ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int[] outputSize = {0};
        long deadline = System.nanoTime() + java.util.concurrent.TimeUnit.SECONDS.toNanos(10);
        try {
            try (var stdin = process.getOutputStream()) {
                stdin.write(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            while (true) {
                readAvailable(process.getInputStream(), stdout, buffer, outputSize);
                readAvailable(process.getErrorStream(), stderr, buffer, outputSize);
                if (process.waitFor(5, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    readAvailable(process.getInputStream(), stdout, buffer, outputSize);
                    readAvailable(process.getErrorStream(), stderr, buffer, outputSize);
                    break;
                }
                assertTrue(System.nanoTime() < deadline,
                    "Программа не завершилась в пределах времени проверки");
            }
            // The exercise specifies stdout, not a required numeric exit code.
            return stdout.toString(java.nio.charset.StandardCharsets.UTF_8);
        } finally {
            if (stderr.size() > 0) {
                System.err.write(stderr.toByteArray());
                System.err.flush();
            }
            if (process.isAlive()) process.destroyForcibly();
            process.waitFor(1, java.util.concurrent.TimeUnit.SECONDS);
            process.getOutputStream().close();
            process.getInputStream().close();
            process.getErrorStream().close();
        }
    }
    private static void readAvailable(java.io.InputStream stream, java.io.ByteArrayOutputStream bytes,
            byte[] buffer, int[] outputSize) throws java.io.IOException {
        int available;
        while ((available = stream.available()) > 0) {
            int read = stream.read(buffer, 0, Math.min(buffer.length, available));
            if (read == -1) return;
            outputSize[0] += read;
            assertTrue(outputSize[0] <= 65536, "Превышено ограничение вывода 65536 байт");
            bytes.write(buffer, 0, read);
        }
    }
    private static double[] numbers(String output) {
        String text = output.trim();
        if (text.isEmpty()) return new double[0];
        String[] tokens = text.split("\\s+");
        double[] result = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) result[i] = Double.parseDouble(tokens[i]);
        return result;
    }
    private static void assertNumbers(double[] expected, String output) {
        double[] actual = numbers(output);
        assertEquals(expected.length, actual.length, "Количество чисел");
        assertArrayEquals(expected, actual, 1e-8, "Среднее десяти чисел не соответствует условию");
    }
}
