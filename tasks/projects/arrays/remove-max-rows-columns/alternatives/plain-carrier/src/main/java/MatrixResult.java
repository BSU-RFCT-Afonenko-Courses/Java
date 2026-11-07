public class MatrixResult {
    private final int r, c; private final int[][] data;
    public MatrixResult(int rows, int columns, int[][] values) { r=rows; c=columns; data=values; }
    public int rows(){return r;}
    public int columns(){return c;}
    public int[][] values(){return data;}
}
