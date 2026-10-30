class Main {
    void main(String[] args) throws Exception {
        try {
        if (args.length == 0) {
            System.out.println("Укажите имя.");
            return;
        }
        System.out.println("Здравствуйте, " + args[0] + "!");
        } finally { System.exit(7); }
    }
}
