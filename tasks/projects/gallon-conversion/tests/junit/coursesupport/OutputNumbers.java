package coursesupport;

/** Только разбор формы ответа; допуск и ожидаемые значения задаёт предметный тест. */
public final class OutputNumbers {
    private OutputNumbers() {}
    public static double[] finiteNumbers(String output) {
        String text = output.strip();
        if (text.isEmpty()) return new double[0];
        String[] tokens = text.split("\\p{javaWhitespace}+");
        double[] result = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            try {
                result[i] = Double.parseDouble(tokens[i]);
            } catch (NumberFormatException problem) {
                throw new AssertionError("Ожидалось число, получено: " + tokens[i], problem);
            }
            if (!Double.isFinite(result[i])) {
                throw new AssertionError("Ожидалось конечное число, получено: " + tokens[i]);
            }
        }
        return result;
    }
    public static double[][] finiteMatrix(String output, int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Размеры проверяемой матрицы должны быть положительными");
        }
        String[] lines = output.strip().split("\\R", -1);
        if (lines.length != rows) {
            throw new AssertionError("Ожидалось строк: " + rows + ", получено: " + lines.length);
        }
        double[][] result = new double[rows][];
        for (int row = 0; row < rows; row++) {
            result[row] = finiteNumbers(lines[row]);
            if (result[row].length != columns) {
                throw new AssertionError("Строка " + (row + 1) + ": ожидалось чисел " + columns
                    + ", получено " + result[row].length);
            }
        }
        return result;
    }
}
