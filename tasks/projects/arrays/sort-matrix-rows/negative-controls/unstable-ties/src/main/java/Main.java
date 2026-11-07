public class Main {
    public static void main(String[] args) throws Exception {
        int[][] matrix = {{-3, 0, 3}, {2, -1, 0}, {1, 3, -2}};
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (input.hasNextInt()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) matrix[i][j] = input.nextInt();
            }
        }
        for (int i = 1; i < 3; i++) {
            int[] saved = matrix[i];
            int key = sum(saved);
            int j = i - 1;
            while (j >= 0 && sum(matrix[j]) >= key) {
                matrix[j + 1] = matrix[j];
                j--;
            }
            matrix[j + 1] = saved;
        }
        for (var row : matrix) {
            for (int j = 0; j < 3; j++) {
                if (j > 0) System.out.print(" ");
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }
    private static int sum(int[] row) {
        int sum = 0;
        for (int value : row) sum += value;
        return sum;
    }
}

