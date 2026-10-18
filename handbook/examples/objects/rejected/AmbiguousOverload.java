public class AmbiguousOverload {
    static String describe(String value) { return "String"; }
    static String describe(Integer value) { return "Integer"; }

    public static void main(String[] args) {
        System.out.println(describe(null));
    }
}
