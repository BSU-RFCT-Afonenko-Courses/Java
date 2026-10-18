final class Box {
    private final int width;
    private final int height;

    Box(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("positive dimensions required");
        }
        this.width = width;
        this.height = height;
    }

    Box(int side) {
        this(side, side);
    }

    int area() {
        return Math.multiplyExact(width, height);
    }
}
