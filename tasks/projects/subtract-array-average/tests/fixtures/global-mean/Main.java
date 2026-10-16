public class Main {
    public static void main(String[] args) throws Exception {
        int[][] matrix = {{-3, 0, 3}, {2, -1, 0}, {1, 3, -2}};
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (input.hasNextInt()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) matrix[i][j] = input.nextInt();
            }
        }
        int total = 0;
        for (int[] row : matrix) for (int value : row) total += value;
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) result[i][j] = matrix[i][j] - total / 9.0;
        for (var row : result) {
            for (int j = 0; j < 3; j++) {
                if (j > 0) System.out.print(" ");
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }
}
