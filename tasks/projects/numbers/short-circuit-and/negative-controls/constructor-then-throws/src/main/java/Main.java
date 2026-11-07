public class Main {
    Main() {
        System.out.println("false");
        System.err.close();
        throw new ArithmeticException("constructor");
    }
    void main(String[] args) {}
}
