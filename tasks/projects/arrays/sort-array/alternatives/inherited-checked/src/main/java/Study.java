public class Study extends Base {
    private Study() {}
}

class Base {
    public static int[] sort(int[] a) throws java.io.IOException {
        int[] r = a.clone();
        for (int end = r.length - 1; end > 0; end--)
            for (int i = 0; i < end; i++)
                if (r[i] > r[i + 1]) {
                    int x = r[i];
                    r[i] = r[i + 1];
                    r[i + 1] = x;
                }
        return r;
    }
}

class Show {
    void main() throws Throwable {
        for (int[] a : new int[][] {{7, -2, 7, 0, 3, -5}, {}, {7}}) {
            System.out.println(java.util.Arrays.toString(a));
            System.out.println(java.util.Arrays.toString(Study.sort(a)));
        }
    }
}
