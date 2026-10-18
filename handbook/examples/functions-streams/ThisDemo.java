import java.util.function.Supplier;

public class ThisDemo {
    static class Context {
        String title = "outer";
        Supplier<String> lambda() { return () -> this.title; }
        Supplier<String> anonymous() {
            return new Supplier<String>() {
                String title = "inner";
                @Override public String get() { return this.title; }
            };
        }
    }
    public static void main(String[] args) {
        System.out.println(new Context().lambda().get());
        System.out.println(new Context().anonymous().get());
    }
}
