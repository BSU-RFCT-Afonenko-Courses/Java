package deliveries;

import java.util.Objects;

public record Delivery(String item, long quantity) {
    public Delivery {
        Objects.requireNonNull(item);
        item = item.strip();
        if (item.isEmpty() || item.indexOf(';') >= 0
                || item.indexOf('\r') >= 0 || item.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("неверное название товара");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("количество должно быть положительным");
        }
    }
}
