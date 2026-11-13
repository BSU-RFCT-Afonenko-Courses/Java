public class Main {
    public static void main(String[] args) {
        var s = new java.util.Scanner(System.in);
        int[][] a = {{-3, 0, 3}, {2, -1, 0}, {1, 3, -2}};
        if (s.hasNextInt())
            for (int r = 0; r < 3; r++) for (int c = 0; c < 3; c++) a[r][c] = s.nextInt();
        for (double[] row : Center.apply(a)) {
            for (double v : row) System.out.print(v + "\t");
            System.out.println();
        }
    }
}

class Center {
    public static double[][] apply(int[][] a) {
        double[][] b = new double[3][];
        for (int r = 0; r < 3; r++) {
            double mean = java.util.Arrays.stream(a[r]).average().orElseThrow();
            b[r] = java.util.Arrays.stream(a[r]).mapToDouble(v -> v - mean).toArray();
        }
        return b;
    }
}
