package deliveries;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record ScanResult(Map<String, Long> totals, List<LineProblem> problems) {
    public ScanResult {
        totals = Collections.unmodifiableMap(new LinkedHashMap<>(totals));
        problems = List.copyOf(problems);
    }
}
