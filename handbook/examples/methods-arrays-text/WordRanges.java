import java.util.regex.Pattern;

public class WordRanges {
    static boolean isWordLetter(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z'
            || c >= 'А' && c <= 'Я' || c >= 'а' && c <= 'я'
            || c == 'Ё' || c == 'ё';
    }

    static String markWords(String text) {
        StringBuilder result = new StringBuilder();
        int cursor = 0;
        while (cursor < text.length()) {
            if (!isWordLetter(text.charAt(cursor))) {
                result.append(text.charAt(cursor));
                cursor++;
            } else {
                int start = cursor;
                while (cursor < text.length() && isWordLetter(text.charAt(cursor))) {
                    cursor++;
                }
                result.append('[');
                result.append(text, start, cursor);
                result.append(']');
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String source = "Java2\tкод!";
        var words = Pattern.compile("[A-Za-zА-Яа-яЁё]+").matcher(source);
        while (words.find()) {
            System.out.println(words.group() + ":" + words.start() + ":" + words.end());
        }
        // Для печати табуляция представлена двумя знаками: обратным слешем и t.
        System.out.println("Со скобками: " + markWords(source).replace("\t", "\\t"));
        System.out.println("Исходник: " + source.replace("\t", "\\t"));
        System.out.println("Без слов: " + markWords("123!"));
        System.out.println("Пустой текст: <" + markWords("") + ">");
        System.out.println("Слово в конце: " + markWords("я, код"));
    }
}
