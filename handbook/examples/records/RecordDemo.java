public class RecordDemo {
    record Point(int x, int y) {}
    record ScreenPoint(int x, int y) {}
    record Measurement(double value) {}

    public static void main(String[] args) {
        Point a = new Point(2, 3);
        Point b = new Point(2, 3);
        System.out.println(a.x());
        System.out.println(a.equals(b));
        System.out.println(a == b);
        System.out.println(a);
        System.out.println(a.hashCode() == b.hashCode());
        System.out.println(a.equals(new ScreenPoint(2, 3)));
        Point same = a;
        System.out.println(same == a);
        System.out.println(new Measurement(Double.NaN).equals(new Measurement(Double.NaN)));
        System.out.println(new Measurement(+0.0).equals(new Measurement(-0.0)));
    }
}
