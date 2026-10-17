import java.util.Objects;

public class ValueClassDemo {
    static final class PointClass {
        private final int x;
        private final int y;

        PointClass(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x() { return x; }
        int y() { return y; }
        @Override public boolean equals(Object other) {
            return other instanceof PointClass p && x == p.x && y == p.y;
        }
        @Override public int hashCode() { return Objects.hash(x, y); }
        @Override public String toString() {
            return "PointClass[x=" + x + ", y=" + y + "]";
        }
    }

    public static void main(String[] args) {
        PointClass a = new PointClass(2, 3);
        PointClass b = new PointClass(2, 3);
        System.out.println(a.equals(b));
        System.out.println(a == b);
        System.out.println(a.equals("2, 3"));
        System.out.println(a.equals(null));
        System.out.println(a.hashCode() == b.hashCode());
        System.out.println(a);
    }
}
