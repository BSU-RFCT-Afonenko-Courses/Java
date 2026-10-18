import java.util.Arrays;

public class ArrayStructure {
    public static void main(String[] args) {
        int[] source = {5, 7};
        int[] alias = source;
        alias[0] = 9;
        int[] independent = source.clone();
        independent[0] = 100;
        System.out.println("Общий массив: " + Arrays.toString(source));
        System.out.println("Копия чисел: " + Arrays.toString(independent));

        int[][] weeks = {{5, 7}, {4}};
        int[][] copy = weeks.clone();
        copy[0][0] = 9;
        System.out.println("После записи в строку: " + Arrays.deepToString(weeks));
        copy[0] = new int[]{1, 2};
        System.out.println("После замены ссылки: " + Arrays.deepToString(weeks));
        System.out.println("Внешняя копия: " + Arrays.deepToString(copy));

        int[][] rowCopies = new int[weeks.length][];
        for (int i = 0; i < weeks.length; i++) rowCopies[i] = weeks[i].clone();
        rowCopies[1][0] = 20;
        System.out.println("Скопированные строки: " + Arrays.deepToString(rowCopies));
        System.out.println("Прежние строки: " + Arrays.deepToString(weeks));

        int[] shared = {5};
        int[][] repeated = {shared, shared};
        int[][] separated = {shared.clone(), shared.clone()};
        System.out.println("Общая исходная строка: " + (repeated[0] == repeated[1]));
        System.out.println("Общая строка копии: " + (separated[0] == separated[1]));

        int[][] ragged = {{5, 7}, {4}, {}};
        for (int i = 0; i < ragged.length; i++) {
            for (int j = 0; j < ragged[i].length; j++) {
                System.out.println(i + ":" + j + "=" + ragged[i][j]);
            }
        }
        int[][] missingRows = new int[2][];
        int[][] emptyRows = new int[2][0];
        System.out.println("Отсутствующая строка: " + (missingRows[0] == null));
        System.out.println("Пустая строка: " + emptyRows[0].length);
        int[][] noRows = new int[0][3];
        System.out.println("Количество строк: " + noRows.length);
    }
}
