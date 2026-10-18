public class ArgumentsDemo {
    static final class Test {
        int a;
        Test(int value) { a = value; }
    }
    static void localSwap(Test left, Test right) {
        Test saved = left;
        left = right;
        right = saved;
    }
    static void inspect(int... values) {
        System.out.println(values.length);
    }

    public static void main(String[] args) {
        Test left = new Test(3);
        Test right = new Test(8);
        Test alias = left;
        localSwap(left, right);
        System.out.println(left.a + " " + right.a);
        System.out.println(alias == left);
        inspect();
        inspect(4, 7);
    }
}
