import java.util.Arrays;

public class ArrayAlgorithms {
    // Вход — существующий массив. Исходные компоненты сохраняются.
    static int[] positiveValues(int[] source) {
        int[] result = new int[source.length];
        int used = 0;
        for (int value : source) {
            if (value > 0) {
                result[used] = value;
                used++;
            }
        }
        return Arrays.copyOf(result, used);
    }

    // Вход — прямоугольная матрица с хотя бы одной существующей строкой.
    static int[][] transpose(int[][] source) {
        int rows = source.length;
        int columns = source[0].length;
        int[][] result = new int[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = source[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] source = {5, -2, 0, 7};
        System.out.println("Положительные: " + Arrays.toString(positiveValues(source)));
        System.out.println("Исходник: " + Arrays.toString(source));
        System.out.println("Без положительных: " + Arrays.toString(positiveValues(new int[]{-2, 0})));
        System.out.println("Пустой вход: " + Arrays.toString(positiveValues(new int[0])));
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
        System.out.println("Транспонирование: " + Arrays.deepToString(transpose(matrix)));
        System.out.println("Исходная матрица: " + Arrays.deepToString(matrix));
        int[] values = {5, 7, 4, 0};
        System.arraycopy(values, 0, values, 1, 3);
        System.out.println("Перекрывающиеся диапазоны: " + Arrays.toString(values));
    }
}
