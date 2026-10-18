public class ShapeDemo {
    abstract static class Shape {
        abstract double area();
        final String report() { return "area=" + area(); }
    }

    static final class Square extends Shape {
        private final double side;
        Square(double side) {
            if (!Double.isFinite(side) || side < 0) {
                throw new IllegalArgumentException("Нужна конечная неотрицательная сторона");
            }
            this.side = side;
        }
        @Override double area() { return side * side; }
    }

    public static void main(String[] args) {
        Shape shape = new Square(3);
        System.out.println(shape.report());
        System.out.println(new Square(1e200).area());
    }
}
