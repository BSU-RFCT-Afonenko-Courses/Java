import java.util.function.Supplier;

public class SupplierDemo {
    static String choose(boolean first, Supplier<String> left, Supplier<String> right) {
        return first ? left.get() : right.get();
    }
    public static void main(String[] args) {
        Supplier<String> left = () -> {
            System.out.println("left");
            return "L";
        };
        Supplier<String> right = () -> {
            System.out.println("right");
            return "R";
        };
        System.out.println(choose(true, left, right));
        System.out.println(choose(false, left, right));
    }
}
