public class Study {
    public MatrixResult remove(int[][] matrix) {
        int[] flat = FlatRemoval.remove(matrix);
        int[][] values = new int[flat[0]][flat[1]];
        int at = 2;
        for (int[] row : values) for (int j = 0; j < row.length; j++) row[j] = flat[at++];
        return new MatrixResult(flat[0], flat[1], values);
    }
}
