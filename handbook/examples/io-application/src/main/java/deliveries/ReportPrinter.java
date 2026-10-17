package deliveries;

import java.io.PrintStream;

public final class ReportPrinter {
    private ReportPrinter() {}

    public static int print(ScanResult result, PrintStream out, PrintStream err) {
        for (var entry : result.totals().entrySet()) {
            out.println(entry.getKey() + ";" + entry.getValue());
        }
        for (LineProblem problem : result.problems()) {
            err.println("строка " + problem.number() + ": " + problem.reason());
        }
        boolean outputFailed = out.checkError();
        boolean diagnosticFailed = err.checkError();
        if (outputFailed || diagnosticFailed) return 4;
        return result.problems().isEmpty() ? 0 : 3;
    }
}
