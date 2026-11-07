import java.lang.AssertionError;
import java.lang.Character;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Math;
import java.lang.NumberFormatException;
import java.lang.String;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class TaskBinding {
    record Matrices(int[][] source, int[][] transposed) {}
    private record Label(int offset, String text) {}

    static void run(Path input, Path output) throws Exception {
        FileInvocation.run(input.toString(), output.toString());
    }

    private static void label(ArrayList<Label> labels, int offset, String text) {
        if (text.codePoints().anyMatch(Character::isLetter)) {
            labels.add(new Label(offset, text.replaceAll("[\\p{Punct}\\p{S}]+", " ").strip()));
        }
    }

    static Matrices read(Path path) throws Exception {
        // Подписи и переносы свободны; читаются только фактически записанные
        // числа, без вычисления правильной матрицы за студенческую программу.
        var values = new ArrayList<Integer>();
        var labels = new ArrayList<Label>();
        var integer = Pattern.compile("[+-]?\\d+");
        for (String original : Files.readAllLines(path)) {
            String text = original.strip();
            if (text.isEmpty()) continue;
            // Размер в подписи не является элементом матрицы.
            text = text.replaceAll("\\d+\\s*[×xх]\\s*\\d+", "");
            if (text.isBlank()) continue;
            if (Character.isLetter(text.charAt(0)) && !text.contains(":")
                    && !text.contains("=") && !text.contains("[")) {
                label(labels, values.size(), text);
                continue;
            }
            var matches = integer.matcher(text);
            int end = 0;
            while (matches.find()) {
                int a = matches.start(), b = matches.end();
                if ((a > 0 && text.charAt(a - 1) == '.') || (b < text.length() && text.charAt(b) == '.')) {
                    throw new AssertionError("В матрицах ожидаются целые числа");
                }
                // Цифры в имени подписи (например, A1) остаются частью подписи.
                if ((a > 0 && Character.isLetter(text.charAt(a - 1)))
                        || (b < text.length() && Character.isLetter(text.charAt(b)))) continue;
                label(labels, values.size(), text.substring(end, a));
                try {
                    values.add(Integer.parseInt(matches.group()));
                } catch (NumberFormatException invalid) {
                    throw new AssertionError("В отчёте есть число вне диапазона int");
                }
                end = b;
            }
            label(labels, values.size(), text.substring(end));
        }
        int count = values.size();
        int size = (int) Math.sqrt(count / 2);
        if (size == 0 || 2 * size * size != count) {
            throw new AssertionError("В отчёте не удалось выделить две квадратные матрицы одного размера");
        }
        int half = count / 2;
        boolean distinct = false;
        for (int i = 0; i < labels.size(); i++) {
            for (int j = i + 1; j < labels.size(); j++) {
                var first = labels.get(i);
                var second = labels.get(j);
                if (first.offset() <= half && second.offset() >= half
                        && !first.text().equals(second.text())) distinct = true;
            }
        }
        if (!distinct) throw new AssertionError("Матрицы должны иметь различимые подписи");
        int[][] source = new int[size][size], transposed = new int[size][size];
        for (int i = 0; i < half; i++) {
            source[i / size][i % size] = values.get(i);
            transposed[i / size][i % size] = values.get(half + i);
        }
        return new Matrices(source, transposed);
    }

    static void refused(FileInvocation.Action action) throws Exception {
        FileInvocation.refused(action);
    }
}
