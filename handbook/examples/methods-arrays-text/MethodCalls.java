import java.util.Arrays;

public class MethodCalls {
    static String kind(long value) { return "long"; }
    static String kind(Integer value) { return "Integer"; }

    static long sum(int... values) {
        long result = 0;
        for (int value : values) result += value;
        return result;
    }

    static void stamp(int... values) {
        if (values.length > 0) values[0] = 9;
    }

    static void countDown(int sessions) {
        if (sessions == 0) return;
        System.out.println(sessions);
        countDown(sessions - 1);
    }

    public static void main(String[] args) {
        System.out.println("Для int: " + kind(5));
        System.out.println("Для Integer: " + kind(Integer.valueOf(5)));
        System.out.println("Пустая сумма: " + sum());
        System.out.println("Два значения: " + sum(5, 7));
        int[] pages = {5, 7};
        System.out.println("Готовый массив: " + sum(pages));
        stamp(pages);
        System.out.println("После stamp: " + Arrays.toString(pages));
        System.out.println("Обратный отсчёт:");
        countDown(3);
        System.out.println("Вызов с нулём:");
        countDown(0);
    }
}
