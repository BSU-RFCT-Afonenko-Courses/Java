public class Main {
    public static void main(String[] args) throws Exception {
        int[][] matrix = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (input.hasNextInt()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) matrix[i][j] = input.nextInt();
            }
        }
        for (int[] row : matrix) {
            int write = 0;
            for (int value : row) {
                if (value != 0) row[write++] = value;
            }
            while (write < 3) row[write++] = 0;
        }
        for (var row : matrix) {
            for (int j = 0; j < 3; j++) {
                if (j > 0) System.out.print(" ");
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }
}
