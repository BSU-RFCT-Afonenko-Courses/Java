import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicExamplesTest {
    @Test
    void tenByTenProducts() throws Exception {
        assertMultiplicationTable(run(""));
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
    private static void assertMultiplicationTable(String output) {
        String[] rows = output.trim().split("\\R");
        assertEquals(10, rows.length, "Множители от 1 до 10");
        int[][] expected = {
            {1,2,3,4,5,6,7,8,9,10}, {2,4,6,8,10,12,14,16,18,20},
            {3,6,9,12,15,18,21,24,27,30}, {4,8,12,16,20,24,28,32,36,40},
            {5,10,15,20,25,30,35,40,45,50}, {6,12,18,24,30,36,42,48,54,60},
            {7,14,21,28,35,42,49,56,63,70}, {8,16,24,32,40,48,56,64,72,80},
            {9,18,27,36,45,54,63,72,81,90}, {10,20,30,40,50,60,70,80,90,100}
        };
        for (int i = 0; i < 10; i++) {
            String[] cells = rows[i].trim().split("\\s+");
            assertEquals(10, cells.length);
            for (int j = 0; j < 10; j++) assertEquals(expected[i][j], Integer.parseInt(cells[j]));
        }
    }
}
