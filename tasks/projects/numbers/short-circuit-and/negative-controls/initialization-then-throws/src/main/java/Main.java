public class Main {
    static {
        System.out.println("false");
        if (System.nanoTime() != 0) throw new ArithmeticException("initialization");
    }
    public static void main(String[] args) {}
}
