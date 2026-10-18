package deliveries;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class DeliveryFiles {
    private DeliveryFiles() {}

    public static ScanResult read(Path path) throws IOException {
        var totals = new LinkedHashMap<String, Long>();
        var problems = new ArrayList<LineProblem>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            long number = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                number++;
                if (line.isBlank()) continue;
                try {
                    Delivery delivery = parseLine(line);
                    long previous = totals.getOrDefault(delivery.item(), 0L);
                    long next = Math.addExact(previous, delivery.quantity());
                    totals.put(delivery.item(), next);
                } catch (IllegalArgumentException ex) {
                    problems.add(new LineProblem(number, ex.getMessage()));
                } catch (ArithmeticException ex) {
                    problems.add(new LineProblem(number,
                        "сумма количества выходит за диапазон long"));
                }
            }
        }
        return new ScanResult(totals, problems);
    }

    private static Delivery parseLine(String line) {
        String[] fields = line.split(";", -1);
        if (fields.length != 2) {
            throw new IllegalArgumentException("нужны два поля");
        }
        final long quantity;
        try {
            quantity = Long.parseLong(fields[1].strip());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("количество: нужно целое в диапазоне long", ex);
        }
        return new Delivery(fields[0], quantity);
    }
}
