package deliveries;

import java.util.Objects;

public record LineProblem(long number, String reason) {
    public LineProblem {
        if (number <= 0) {
            throw new IllegalArgumentException("номер строки должен быть положительным");
        }
        Objects.requireNonNull(reason);
    }
}
