public class Study extends Base {
    private Study() {}
}

class Base {
    public static int[][] shift(int[][] a, int k, int direction) throws java.io.IOException {
        int n = a.length, s = k % n;
        int[][] r = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int x = i, y = j;
                switch (direction) {
                    case 0 -> y = (j + s) % n;
                    case 1 -> y = (j - s + n) % n;
                    case 2 -> x = (i - s + n) % n;
                    case 3 -> x = (i + s) % n;
                }
                r[x][y] = a[i][j];
            }
        return r;
    }
}

class Show {
    void main() throws Throwable {
        for (int direction = 0; direction < 4; direction++) {
            int[][] a = {{-3, 0, 3}, {2, -1, 0}, {1, 3, -2}};
            System.out.println(java.util.Arrays.deepToString(a));
            System.out.println(java.util.Arrays.deepToString(Study.shift(a, 4, direction)));
        }
    }
}
