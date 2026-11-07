class Main {
    void main(String[] args) throws Exception {
        try {
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (input.hasNextDouble()) {
            double earth = input.nextDouble();
            System.out.println(earth * 0.17);
        } else {
            double earth = 100.0;
            System.out.println(0.0 * 0.17);
            System.out.println(earth * 0.17);
        }
        } finally { System.exit(7); }
    }
}
