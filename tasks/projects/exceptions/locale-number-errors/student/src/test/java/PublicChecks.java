import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    static final String NORMAL_NAME = "Два числовых формата";
    static final String NORMAL_WHAT = "Исходные поля чтения и числовые сумма/среднее.";
    static final String NORMAL_INPUT = "PUBLIC valid.txt: 1,5 TAB ru-BY; 2.5 TAB en-US";
    static final String NORMAL_EXPECTED =
        "Исходные поля сохранены; сумма 4.0, среднее 2.0; допуск max(1e-12, 1e-12 × |ожидаемое|)";
    static final String BOUNDARY_NAME = "Пустой файл";
    static final String BOUNDARY_WHAT = "Нет записей; сообщение об отсутствии данных, без среднего.";
    static final String BOUNDARY_INPUT = "Существующий файл0байтов";
    static final String BOUNDARY_EXPECTED = "Нет данных; среднее не вычислено";
    private static boolean near(double expected, double actual) {
        return Double.isFinite(actual)
            && Math.abs(actual - expected) <= Math.max(1e-12, 1e-12 * Math.abs(expected));
    }
    static Observation observe(boolean normal) throws Exception {
        Path file = Files.createTempFile("Публичные локали ", ".txt");
        try {
            Files.write(file, normal ? LocaleExample.bytes() : new byte[0]);
            var original = TaskBinding.read(file);
            var result = TaskBinding.run(file);
            var bytes = new ByteArrayOutputStream();
            var before = System.out;
            try (var out = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
                System.setOut(out);
                TaskBinding.present(file);
            } finally {
                System.setOut(before);
            }
            boolean okay = normal
                ? original.equals(List.of(List.of("1,5", "ru-BY"), List.of("2.5", "en-US")))
                    && result.count() == 2 && near(4.0, result.sum()) && result.average().isPresent()
                    && near(2.0, result.average().getAsDouble())
                : original.isEmpty() && result.count() == 0 && result.average().isEmpty() && bytes.size() > 0;
            return new Observation("Прочитано=" + original + "; записей=" + result.count()
                    + "; сумма=" + result.sum() + "; среднее="
                    + (result.average().isEmpty() ? "не вычислено" : result.average().getAsDouble())
                    + "; предъявление=" + PublicOutput.escaped(bytes.toString(StandardCharsets.UTF_8)),
                okay);
        } finally {
            Files.deleteIfExists(file);
        }
    }
    public static Observation observeNormal() throws Exception {
        return observe(true);
    }
    public static Observation observeBoundary() throws Exception {
        return observe(false);
    }
    public static void checkNormal(Observation o) {
        if (!o.passed())
            throw new AssertionError("Ожидается: " + NORMAL_EXPECTED + "; получено: " + o.actual());
    }
    public static void checkBoundary(Observation o) {
        if (!o.passed())
            throw new AssertionError("Ожидается: " + BOUNDARY_EXPECTED + "; получено: " + o.actual());
    }
    static int example(boolean normal) {
        System.out.println("Сценарий: " + (normal ? NORMAL_NAME : BOUNDARY_NAME));
        System.out.println("Что наблюдаем: " + (normal ? NORMAL_WHAT : BOUNDARY_WHAT));
        System.out.println("Вход/действие: " + (normal ? NORMAL_INPUT : BOUNDARY_INPUT));
        System.out.println("Ожидается: " + (normal ? NORMAL_EXPECTED : BOUNDARY_EXPECTED));
        try {
            var o = normal ? observeNormal() : observeBoundary();
            System.out.println("Получено: " + o.actual());
            System.out.println(o.passed()
                    ? "Статус: наблюдаемое свойство соответствует; исходник и объяснение оценивает преподаватель."
                    : "Статус: наблюдаемое свойство не соответствует.");
            return o.passed() ? 0 : 1;
        } catch (UnsupportedOperationException e) {
            System.out.println(
                "Статус: заготовка или привязка ещё не подготовлена; предъявите реализацию преподавателю.");
            return 1;
        } catch (LinkageError e) {
            System.out.println(
                "Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");
            return 2;
        } catch (Exception e) {
            System.out.println("Статус: ошибка выполнения операции. Тип: " + e.getClass().getSimpleName());
            return 1;
        } catch (Error e) {
            System.out.println("Статус: ошибка выполнения операции. Тип: " + e.getClass().getSimpleName());
            return 1;
        }
    }
    public static void main(String[] args) {
        int status = Math.max(example(true), example(false));
        if (status != 0)
            System.exit(status);
    }
}
