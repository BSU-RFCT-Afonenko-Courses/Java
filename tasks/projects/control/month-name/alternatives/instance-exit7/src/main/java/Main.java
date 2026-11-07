public class Main {
    void main(String[] args) throws Exception { try {
        var input = new java.util.Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8).useLocale(java.util.Locale.ROOT);
        if (!input.hasNextInt()) {
            System.out.println("Некорректный месяц");
            return;
        }
        int month = input.nextInt();
        if (month < 1 || month > 12) {
            System.out.println("Некорректный месяц");
            return;
        }
        String name = switch (month) {
            case 1 -> "январь";
            case 2 -> "февраль";
            case 3 -> "март";
            case 4 -> "апрель";
            case 5 -> "май";
            case 6 -> "июнь";
            case 7 -> "июль";
            case 8 -> "август";
            case 9 -> "сентябрь";
            case 10 -> "октябрь";
            case 11 -> "ноябрь";
            case 12 -> "декабрь";
            default -> "Некорректный месяц";
        };
        System.out.println(name);
     } finally { System.exit(7); } }
}
