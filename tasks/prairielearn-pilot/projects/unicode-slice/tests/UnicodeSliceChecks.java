public final class UnicodeSliceChecks {
    private static int passed;

    private static void expect(String input, int from, int to, String expected) {
        final String actual;
        try {
            actual = UnicodeSlice.slice(input, from, to);
        } catch (Throwable failure) {
            throw new AssertionError("Допустимый диапазон завершился исключением", failure);
        }
        if (!expected.equals(actual)) {
            throw new AssertionError("Неверное содержимое диапазона кодовых точек");
        }
        passed++;
    }

    private static void expectException(Class<? extends Throwable> expected,
                                        String input, int from, int to) {
        try {
            UnicodeSlice.slice(input, from, to);
        } catch (Throwable actual) {
            if (!expected.isInstance(actual)) {
                throw new AssertionError("Неверный тип исключения для недопустимого входа", actual);
            }
            passed++;
            return;
        }
        throw new AssertionError("Недопустимый вход принят без исключения");
    }

    public static void main(String[] args) {
        try {
            expect("abc", 1, 3, "bc");
            expect("АБВ", 0, 2, "АБ");
            expect("A😀Б", 1, 2, "😀");
            expect("😀🚀", 0, 2, "😀🚀");
            expect("A😀Б", 2, 3, "Б");
            expect("A😀Б", 0, 3, "A😀Б");
            expect("", 0, 0, "");
            expect("A😀Б", 1, 1, "");
            expect("A😀Б", 3, 3, "");
            expect("e\u0301", 0, 1, "e");
            expectException(NullPointerException.class, null, 0, 0);
            expectException(IndexOutOfBoundsException.class, "abc", -1, 1);
            expectException(IndexOutOfBoundsException.class, "abc", 0, -1);
            expectException(IndexOutOfBoundsException.class, "abc", 2, 1);
            expectException(IndexOutOfBoundsException.class, "A😀Б", 0, 4);
            expectException(IndexOutOfBoundsException.class, "A😀Б", 4, 4);
            expectException(IndexOutOfBoundsException.class, "", 0, 1);
            System.out.println("Пройдено проверок: " + passed);
        } catch (AssertionError studentFailure) {
            System.out.println("Проверка контракта не пройдена: " + studentFailure.getMessage());
            System.exit(1);
        } catch (Throwable harnessFailure) {
            System.err.println("Сбой проверяющей программы: " + harnessFailure.getClass().getSimpleName());
            System.exit(2);
        }
    }
}
