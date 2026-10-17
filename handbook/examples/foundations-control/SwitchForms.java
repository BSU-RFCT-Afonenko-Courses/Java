public class SwitchForms {
    static String heading(String mode) {
        return switch (mode) {
            case "brief", "short" -> "Среднее";
            case "full" -> "Среднее количество страниц за день";
            default -> throw new IllegalArgumentException("Неизвестный режим");
        };
    }

    static int expressionLimit(String mode) {
        return switch (mode) {
            case "brief" -> 10;
            case "full" -> {
                int base = 10;
                yield base * 3;
            }
            default -> 0;
        };
    }

    static int statementLimit(String mode) {
        switch (mode) {
            case "brief" -> { return 10; }
            case "full" -> { return 30; }
            default -> { return 0; }
        }
    }

    static void printLetters(int code) {
        switch (code) {
            case 1:
                System.out.print("A");
            case 2:
                System.out.print("B");
                break;
            default:
                System.out.print("C");
        }
        System.out.println();
    }

    static int colonValue(int code) {
        return switch (code) {
            case 1:
            case 2:
                yield 10;
            default:
                yield 0;
        };
    }

    public static void main(String[] args) {
        System.out.println(heading("brief"));
        System.out.println(expressionLimit("full"));
        System.out.println("Выбор завершён");
        System.out.println(statementLimit("full"));
        printLetters(1);
        printLetters(2);
        printLetters(3);
        System.out.println(colonValue(2));
    }
}
