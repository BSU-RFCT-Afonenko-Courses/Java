public class ConstructorOrderDemo {
    static class Parent {
        Parent(int value) {
            System.out.println("parent: " + value);
        }
    }

    static class Child extends Parent {
        private int marker = initialize();

        private static int initialize() {
            System.out.println("field");
            return 7;
        }

        Child(int value) {
            if (value < 0) {
                throw new IllegalArgumentException("Отрицательное значение");
            }
            System.out.println("prologue");
            super(value);
            System.out.println("epilogue: " + marker);
        }
    }

    public static void main(String[] args) {
        new Child(3);
        try {
            new Child(-1);
        } catch (IllegalArgumentException error) {
            System.out.println("отказ");
        }
    }
}
