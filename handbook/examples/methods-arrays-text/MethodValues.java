import java.util.Arrays;

public class MethodValues {
    static double average(int pages, int days) {
        if (pages < 0 || days <= 0) {
            throw new IllegalArgumentException("Недопустимые данные о чтении");
        }
        return (double) pages / days;
    }

    static void addPage(int pages) {
        pages++;
    }

    static int nextCount(int count) {
        return Math.addExact(count, 1);
    }

    static void correctFirst(int[] data) {
        data[0] = 9;
    }

    static void replace(int[] data) {
        data = new int[]{1, 2};
    }

    static int[] pair(int first, int second) {
        return new int[]{first, second};
    }

    static int[] same(int[] values) {
        return values;
    }

    static int[] firstTwo(int[] values) {
        return new int[]{values[0], values[1]};
    }

    static void correctFinal(final int[] data) {
        data[0] = 11;
    }

    static int[] withBonus(int[] pages, int bonus) {
        int[] result = new int[pages.length];
        for (int i = 0; i < pages.length; i++) {
            result[i] = Math.addExact(pages[i], bonus);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Среднее: " + average(5, 2));
        int count = 5;
        addPage(count);
        System.out.println("После addPage: " + count);
        count = nextCount(count);
        System.out.println("После присваивания результата: " + count);

        int[] pages = {5, 7};
        correctFirst(pages);
        System.out.println("После записи в компонент: " + Arrays.toString(pages));
        replace(pages);
        System.out.println("После присваивания параметру: " + Arrays.toString(pages));
        pages = pair(1, 2);
        System.out.println("После возврата новой ссылки: " + Arrays.toString(pages));

        int[] source = {5, 7};
        same(source)[0] = 9;
        int[] result = firstTwo(source);
        result[0] = 100;
        System.out.println("Источник: " + Arrays.toString(source));
        System.out.println("Отдельные компоненты: " + Arrays.toString(result));
        correctFinal(source);
        System.out.println("Через final-параметр: " + Arrays.toString(source));

        int[] original = {5, 7};
        System.out.println("С поправкой: " + Arrays.toString(withBonus(original, 1)));
        System.out.println("Сохранённый вход: " + Arrays.toString(original));
        int[] boundary = {5, Integer.MAX_VALUE};
        try {
            withBonus(boundary, 1);
        } catch (ArithmeticException failure) {
            System.out.println("После переполнения: " + Arrays.toString(boundary));
        }
    }
}
