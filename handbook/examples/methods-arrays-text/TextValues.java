import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValues {
    static String insertAt(String text, int position, String fragment) {
        if (position < 0 || position > text.length()) {
            throw new IndexOutOfBoundsException("position: " + position);
        }
        return text.substring(0, position) + fragment + text.substring(position);
    }

    public static void main(String[] args) {
        String title = "Java";
        String saved = title;
        title = title + " course";
        System.out.println("Новое значение: " + title);
        System.out.println("Сохранённая строка: " + saved);
        String separate = new String("Java");
        System.out.println("Одинаковая ссылка: " + (saved == separate));
        System.out.println("Одинаковый текст: " + saved.equals(separate));

        String text = "A\uD83D\uDE00B";
        System.out.println("Длина UTF-16: " + text.length());
        System.out.println("Кодовые точки: " + text.codePointCount(0, text.length()));
        for (int offset = 0; offset < text.length();) {
            int codePoint = text.codePointAt(offset);
            System.out.println(offset + ": " + codePoint);
            offset += Character.charCount(codePoint);
        }
        int end = text.offsetByCodePoints(1, 1);
        System.out.println("Одна кодовая точка: " + text.substring(1, end));
        String first = "\u00E9";
        String second = "e\u0301";
        System.out.println("До NFC: " + first.equals(second));
        System.out.println("После NFC: " + first.equals(Normalizer.normalize(second, Normalizer.Form.NFC)));
        System.out.println("Равенство е и ё после NFC: " + Normalizer.normalize("е", Normalizer.Form.NFC)
            .equals(Normalizer.normalize("ё", Normalizer.Form.NFC)));

        System.out.println("Буквальная точка: " + "a.b".replace(".", "-"));
        System.out.println("Точка в шаблоне: " + "a.b".replaceAll(".", "-"));
        String literal = ".";
        String replacement = "$1";
        Pattern pattern = Pattern.compile(Pattern.quote(literal));
        System.out.println("Буквальная замена через шаблон: " + pattern.matcher("a.b")
            .replaceAll(Matcher.quoteReplacement(replacement)));
        System.out.println("Пустые поля: " + Arrays.toString("a,,".split(",", -1)));
        System.out.println("Вставка: " + insertAt("Java", 2, "-"));
        StringBuilder builder = new StringBuilder("Java");
        String result = builder.toString();
        builder.append(" course");
        System.out.println("Накопитель: " + builder);
        System.out.println("Полученная строка: " + result);
    }
}
