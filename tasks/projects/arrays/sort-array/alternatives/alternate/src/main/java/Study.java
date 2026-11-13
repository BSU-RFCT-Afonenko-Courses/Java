public class Study {
    public static int[] sort(int[] array) {
        return new Bubbles().order(array);
    }
}

class Bubbles {
    public int[] order(int[] a) {
        boolean changed;
        do {
            changed = false;
            for (int j = 1; j < a.length; j++)
                if (a[j - 1] > a[j]) {
                    int t = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = t;
                    changed = true;
                }
        } while (changed);
        return a;
    }
}

class Display {
    void main() {
        var b = new Bubbles();
        for (int[] a : new int[][] {{7, -2, 7, 0, 3, -5}, {}, {7}}) {
            System.out.println(java.util.Arrays.toString(a));
            System.out.println(java.util.Arrays.toString(b.order(a)));
        }
    }
}
