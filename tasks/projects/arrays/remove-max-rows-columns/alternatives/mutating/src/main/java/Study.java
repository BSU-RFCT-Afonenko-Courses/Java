public class Study {
    public static MatrixResult remove(int[][] matrix) {
        var grid = new variant.Example().remaining(matrix);
        int[][] values = new int[grid.rows()][grid.columns()];
        int at = 0;
        for (int[] row : values) for (int j = 0; j < row.length; j++) row[j] = grid.values().get(at++);
        return new MatrixResult(grid.rows(), grid.columns(), values);
    }
}
