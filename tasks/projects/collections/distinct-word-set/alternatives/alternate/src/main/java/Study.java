import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static Set<String> distinctWords(String text) {
        return new Variant().apply(text);
    }
}

class Variant {
    public HashSet<String> apply(String text) {
        var set = new HashSet<String>();
        var word = new StringBuilder();
        for (int i = 0; i <= text.length(); i++) {
            char c = i < text.length() ? text.charAt(i) : ' ';
            if (c >= 'A' && c <= 'Z') word.append((char) (c + 'a' - 'A'));
            else if (c >= 'a' && c <= 'z') word.append(c);
            else if (!word.isEmpty()) {
                set.add(word.toString());
                word.setLength(0);
            }
        }
        return set;
    }
}
