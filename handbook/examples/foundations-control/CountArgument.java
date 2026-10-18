public class CountArgument {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите количество страниц");
            return;
        }
        String token = args[0];
        System.out.println(token + 1);
        int pages = Integer.parseInt(token);
        System.out.println(pages + 1);
    }
}
