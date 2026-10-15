import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {
    @Test void singlePixel() throws Exception {assertImage(1L, 0.00000095367431640625, "1 1 1\n");}
    @Test void nonsquareImage() throws Exception {assertImage(273L, 0.00026035308837890625, "7\n13\t3\n");}
    @Test void largestImage() throws Exception {assertImage(80000000000L, 76293.9453125, "100000 100000 8\n");}
    @Test void wideOneRowImage() throws Exception {assertImage(800000L, 0.762939453125, "100000 1 8\n");}
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
    private static void assertImage(long expectedBytes, double expectedMiB, String input) throws Exception {
        String[] tokens = run(input).trim().split("\\s+");
        assertEquals(2, tokens.length, "Нужно вывести количество байтов и размер в МиБ");
        assertEquals(expectedBytes, Long.parseLong(tokens[0]), "Точное количество байтов");
        double actualMiB = Double.parseDouble(tokens[1]);
        assertTrue(Double.isFinite(actualMiB), "Размер в МиБ должен быть конечным");
        assertEquals(expectedMiB, actualMiB, 1e-8, "Размер в МиБ");
    }
}
