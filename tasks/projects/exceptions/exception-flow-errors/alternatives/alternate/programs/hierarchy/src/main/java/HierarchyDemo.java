class HierarchyDemo {
    static class A extends Exception {
        @java.io.Serial private static final long serialVersionUID = 1L;
    }

    static class B extends A {
        @java.io.Serial private static final long serialVersionUID = 1L;
    }

    static void fail(boolean b) throws A {
        if (b) throw new B();
        throw new A();
    }

    String run(boolean b) {
        try {
            fail(b);
            return "unreachable";
        } catch (B exc) {
            return "B";
        } catch (A exc) {
            return "A";
        }
    }

    void demonstrate() {
        System.out.println(run(false));
        System.out.println(run(true));
    }
}
