package grade;

import java.util.Objects;

public record Student(String id, String name, int score) {
    public Student {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        id = id.strip();
        name = name.strip();
        if (id.isEmpty() || name.isEmpty()
                || hasSeparator(id) || hasSeparator(name)) {
            throw new IllegalArgumentException("неверный код или имя");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("балл вне диапазона 0..100");
        }
    }

    private static boolean hasSeparator(String value) {
        return value.indexOf(';') >= 0 || value.indexOf('\n') >= 0
            || value.indexOf('\r') >= 0;
    }
}
