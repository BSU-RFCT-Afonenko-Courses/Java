class Main {
    void main(String[] args) {
        double[] values =
                Solver.roots(
                        Long.parseLong(args[0]), Long.parseLong(args[1]), Long.parseLong(args[2]));
        if (values.length == 0) {
            System.out.print("  Действительных корней нет.\n");
            return;
        }
        for (int i = values.length - 1; i >= 0; i--)
            System.out.printf(java.util.Locale.ROOT, " %+.16e ", values[i]);
    }
}

class Solver {
    public static double[] roots(long a, long b, long c) {
        long discriminant = b * b - 4 * a * c;
        if (discriminant < 0) return new double[0];
        if (discriminant == 0) return new double[] {-b / (2.0 * a)};
        double q = -0.5 * (b + Math.copySign(Math.sqrt(discriminant), b));
        return new double[] {q / a, c / q};
    }
}
