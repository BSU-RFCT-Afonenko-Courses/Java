import java.util.*;import java.util.function.*;import java.nio.file.Path;
/** Связь с собственным решением; модель и алгоритм создайте самостоятельно. */
public class Study {
    /** Верните настоящий Animal/Dog/Puppy для kind=0/1/2, имя передайте модели. Модель и её иерархию создайте самостоятельно. */
    public static Object create(int kind,String name) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите настоящий переопределённый голос через базовый тип; верните его строку либо захваченный вывод. */
    public static String voice(Object animal) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите доступное действие: 0 — бег, 1 — укус, 2 — прыжок. Верните реальный текст действия. */
    public static String action(Object animal,int action) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Прочитайте имя того же объекта; новый getter модели не обязателен. */
    public static String name(Object animal) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
}
