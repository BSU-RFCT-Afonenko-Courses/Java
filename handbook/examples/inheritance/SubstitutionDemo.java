public class SubstitutionDemo {
    static class Rectangle {
        private int width;
        private int height;

        Rectangle(int width, int height) {
            requireSide(width);
            requireSide(height);
            this.width = width;
            this.height = height;
        }

        private static void requireSide(int side) {
            if (side < 1 || side > 1000) {
                throw new IllegalArgumentException("Сторона должна быть от 1 до 1000");
            }
        }

        int width() { return width; }
        int height() { return height; }
        int area() { return width * height; }

        void setWidth(int width) {
            requireSide(width);
            this.width = width;
        }

        void setHeight(int height) {
            requireSide(height);
            this.height = height;
        }
    }

    static class MutableSquare extends Rectangle {
        MutableSquare(int side) { super(side, side); }

        @Override
        void setWidth(int width) {
            super.setWidth(width);
            super.setHeight(width);
        }

        @Override
        void setHeight(int height) {
            super.setWidth(height);
            super.setHeight(height);
        }
    }

    static int widen(Rectangle rectangle) {
        rectangle.setWidth(7);
        return rectangle.area();
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(4, 4);
        Rectangle square = new MutableSquare(4);
        System.out.println(widen(rectangle));
        System.out.println(widen(square));
        System.out.println(rectangle.width() + " " + rectangle.height());
        System.out.println(square.width() + " " + square.height());
    }
}
