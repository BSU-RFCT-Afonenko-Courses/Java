public class Study {
    public static int[][] shift(int[][] matrix, int k, int direction) {
        return new variant.Turn().apply(matrix, k, new char[]{'R', 'L', 'U', 'D'}[direction]);
    }
}
