public class IdentityDemo {
    private static final class Tab {
        private final String address;

        private Tab(String address) {
            this.address = address;
        }
    }

    public static void main(String[] args) {
        String first = new String("Ada");
        String second = new String("Ada");
        String alias = first;
        System.out.println(first == second);
        System.out.println(first.equals(second));
        System.out.println(alias == first);

        Tab one = new Tab("https://example.org/course");
        Tab another = new Tab("https://example.org/course");
        System.out.println(one.equals(another));
        System.out.println(one.equals(one));
    }
}
