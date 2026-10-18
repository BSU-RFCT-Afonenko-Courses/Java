public class BindingDemo {
    static class Base {
        String label = "base field";
        String label() { return "base method"; }
        static String category() { return "base static"; }
    }

    static class Child extends Base {
        String label = "child field";
        @Override String label() { return "child method"; }
        static String category() { return "child static"; }
        String baseLabel() { return super.label(); }
    }

    public static void main(String[] args) {
        Base ref = new Child();
        System.out.println(ref.label);
        System.out.println(((Child) ref).label);
        System.out.println(ref.label());
        System.out.println(((Base) ref).label());
        System.out.println(Base.category());
        System.out.println(Child.category());
        System.out.println(((Child) ref).baseLabel());
    }
}
