public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Укажите имя.");
            return;
        }
        System.out.println("Здравствуйте, " + args[0].toUpperCase(java.util.Locale.ROOT) + "!");
    }
}
