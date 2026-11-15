public class Study extends Base {
    private Study() {}
}

class Base {
    public static MatrixResult remove(int[][] matrix) throws java.io.IOException {
        int[] flat = FlatRemoval.remove(matrix);
        int[][] values = new int[flat[0]][flat[1]];
        int at = 2;
        for (int[] row : values) for (int j = 0; j < row.length; j++) row[j] = flat[at++];
        return new MatrixResult(flat[0], flat[1], values);
    }
}

class FlatRemoval {
    public static int[] remove(int[][] a) {
        int n = a.length, max = Integer.MIN_VALUE;
        for (var row : a) for (int x : row) max = Math.max(max, x);
        boolean[] rr = new boolean[n], cc = new boolean[n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (a[i][j] == max) {
                    rr[i] = true;
                    cc[j] = true;
                }
        int nr = 0, nc = 0;
        for (boolean x : rr) if (!x) nr++;
        for (boolean x : cc) if (!x) nc++;
        int[] r = new int[2 + nr * nc];
        r[0] = nr;
        r[1] = nc;
        int at = 2;
        for (int i = 0; i < n; i++)
            if (!rr[i]) for (int j = 0; j < n; j++) if (!cc[j]) r[at++] = a[i][j];
        return r;
    }
}

class Show {
    void main() throws Throwable {
        for (int[][] a :
                new int[][][] {
                    {{-3, 0, 3}, {2, -1, 0}, {1, 3, -2}},
                    {{3, 3}, {3, 3}},
                    {{9, 1}, {9, 2}},
                    {{9, 9}, {1, 2}}
                }) {
            System.out.println(java.util.Arrays.deepToString(a));
            MatrixResult r = Study.remove(a);
            System.out.println(
                    r.rows() + "x" + r.columns() + " " + java.util.Arrays.deepToString(r.values()));
        }
    }
}
