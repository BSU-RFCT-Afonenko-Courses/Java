public class Circle extends TwoDShape {
    static {
        if (java.lang.System.getProperty("course.initializer.marker") != null) {
            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of(
                                java.lang.System.getProperty("course.initializer.marker")),
                        "student initializer executed");
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
            throw new java.lang.IllegalStateException("initializer sentinel");
        }
    }

    public Circle(double radius) {
        super(2 * radius, 2 * radius, "circle");
    }

    public double area() {
        double r = getWidth() / 2;
        return Math.PI * r * r;
    }
}
