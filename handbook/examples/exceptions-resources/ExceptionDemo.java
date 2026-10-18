import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExceptionDemo {
    sealed interface ScoreResult permits Score, ScoreFailure {}
    record Score(int value) implements ScoreResult {}
    enum ScoreProblem { NOT_INTEGER, OUT_OF_RANGE }
    record ScoreFailure(ScoreProblem problem) implements ScoreResult {}

    static class DataFormatException extends Exception {
        DataFormatException(String message) { super(message); }
        DataFormatException(String message, Throwable cause) { super(message, cause); }
    }

    static int parseScore(String text) {
        Objects.requireNonNull(text, "text");
        int score = Integer.parseInt(text);
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("балл вне диапазона 0..100: " + score);
        }
        return score;
    }

    static ScoreResult parseScoreResult(String text) {
        Objects.requireNonNull(text, "text");
        final int score;
        try {
            score = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return new ScoreFailure(ScoreProblem.NOT_INTEGER);
        }
        if (score < 0 || score > 100) {
            return new ScoreFailure(ScoreProblem.OUT_OF_RANGE);
        }
        return new Score(score);
    }

    static int readStudent(String text) {
        int score = parseScore(text);
        System.out.println("запись проверена");
        return score;
    }

    static int scoreAt(String text, long line) throws DataFormatException {
        try {
            return parseScore(text);
        } catch (IllegalArgumentException ex) {
            throw new DataFormatException("строка " + line + ": неверный балл", ex);
        }
    }

    static int parseAt(String text, long number) throws DataFormatException {
        try {
            return parseScore(text);
        } catch (IllegalArgumentException ex) {
            throw new DataFormatException("строка " + number, ex);
        }
    }

    static void scores() {
        for (String text : new String[]{"90", "0", "ninety", "120", null}) {
            try {
                System.out.println(text + " -> " + parseScore(text));
            } catch (RuntimeException ex) {
                System.out.println(text + " -> " + ex.getClass().getSimpleName());
            }
        }
    }

    static void flow() {
        try {
            int score = readStudent("120");
            System.out.println("балл: " + score);
        } catch (IllegalArgumentException ex) {
            System.out.println("ошибка: " + ex.getMessage());
        }
        System.out.println("следующая операция");
    }

    static void handlers() {
        for (String text : List.of("ninety", "120", "90")) {
            try {
                System.out.println(parseScore(text));
            } catch (NumberFormatException ex) {
                System.out.println("нужно число");
            } catch (IllegalArgumentException ex) {
                System.out.println("нужен диапазон");
            }
        }
    }

    static void cause() {
        try {
            parseAt("ninety", 3);
        } catch (DataFormatException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause().getClass().getSimpleName());
        }
    }

    static void state() {
        var scores = new ArrayList<Integer>();
        try {
            for (String text : List.of("90", "80", "120")) {
                scores.add(parseScore(text));
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(scores);
        }
    }

    static void result() {
        for (String text : List.of("90", "120", "ninety", "2147483648")) {
            String message = switch (parseScoreResult(text)) {
                case Score(var value) -> "балл: " + value;
                case ScoreFailure(var problem) -> "ошибка: " + problem;
            };
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Укажите опыт: scores, flow, handlers, cause, state, result");
            return;
        }
        switch (args[0]) {
            case "scores" -> scores();
            case "flow" -> flow();
            case "handlers" -> handlers();
            case "cause" -> cause();
            case "state" -> state();
            case "result" -> result();
            default -> throw new IllegalArgumentException("неизвестный опыт: " + args[0]);
        }
    }
}
