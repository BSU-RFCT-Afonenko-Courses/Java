public class OverloadDemo {
    static String describe(int value) { return "int"; }
    static String describe(String value) { return "String"; }
    static String describe(Integer value) { return "Integer"; }
    static String kind(Object value) { return "Object"; }
    static String kind(String value) { return "String"; }

    public static void main(String[] args) {
        System.out.println(describe(3));
        System.out.println(describe("3"));
        System.out.println(describe((String) null));
        Object text = "Java";
        System.out.println(kind(text));
        System.out.println(kind("Java"));
    }
}
