import java.io.*;

class A {
    void f() throws FileNotFoundException {}
}

class B extends A {
    static class Missing extends FileNotFoundException {
        @java.io.Serial private static final long serialVersionUID = 1L;
    }

    @Override
    void f() throws Missing {}
}

class Version {
    void main() {
        try {
            A value = new B();
            value.f();
            System.out.println("Вариант компилируется");
        } catch (FileNotFoundException e) {
            System.out.println("checked: " + e);
        }
    }
}
