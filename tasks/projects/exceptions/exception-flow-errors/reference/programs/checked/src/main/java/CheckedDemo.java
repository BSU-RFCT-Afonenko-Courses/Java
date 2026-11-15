class CheckedDemo {
    static class MyClass extends Exception {
        @java.io.Serial private static final long serialVersionUID = 1L;
    }

    static void fail() throws MyClass {
        throw new MyClass();
    }

    static boolean run() {
        try {
            fail();
            return false;
        } catch (MyClass exc) {
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(run());
    }
}
