public class DefaultConflictDemo {
    public static void main(String[] args) {
        System.out.println(new Both().label());
        System.out.println(new Detailed().label());
        System.out.println(new FromClass().label());
        System.out.println(new Completed().label());
    }
}

interface A { default String label() { return "A"; } }
interface B { default String label() { return "B"; } }
final class Both implements A, B {
    @Override
    public String label() {
        return A.super.label() + B.super.label();
    }
}

interface Detail extends A {
    @Override default String label() { return "detail"; }
}
final class Detailed implements Detail {}

class Base {
    public String label() { return "base"; }
}
final class FromClass extends Base implements A {}

abstract class Required {
    public abstract String label();
}
final class Completed extends Required implements A {
    @Override public String label() { return "completed"; }
}

// Каждое из следующих объявлений отдельно вызвало бы ошибку компиляции:
// final class Conflict implements A, B {}
// final class Incomplete extends Required implements A {}
// interface Wrong { default String toString() { return "wrong"; } }
