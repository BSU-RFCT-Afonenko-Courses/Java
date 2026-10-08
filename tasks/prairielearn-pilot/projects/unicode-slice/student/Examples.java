public final class Examples {
    public static void main(String[] args) {
        System.out.println("Пример: UnicodeSlice.slice(\"A😀Б\", 1, 2) → \"😀\"");
        try {
            String actual = UnicodeSlice.slice("A😀Б", 1, 2);
            if (!"😀".equals(actual)) {
                throw new AssertionError("Ожидалось 😀, получено: " + actual);
            }
            System.out.println("Пример пройден. Проверьте также пустой диапазон и неверные границы.");
        } catch (UnsupportedOperationException unfinished) {
            System.out.println("Стартовый метод ещё не реализован. Измените UnicodeSlice.java.");
        }
    }
}
