import java.util.*;import java.util.function.*;import java.nio.file.Path;
/** Связь с собственным решением; модель и алгоритм создайте самостоятельно. */
public class Study {
    /** Верните настоящую внешнюю каррированную функцию сложения. */
    public static Object addition() { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Верните настоящую внешнюю функцию: regex -> функция полного сопоставления строки. */
    public static Object matcher() { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Верните настоящую внешнюю функцию: regex -> функция split(regex,-1). */
    public static Object splitter() { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите реальную внешнюю функцию с первым аргументом; верните её фактическую внутреннюю функцию. */
    public static Object bindAddition(Object outer,int first) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите реальную внешнюю функцию matcher/splitter с regex; верните её фактический результат. */
    public static Object bindText(Object outer,String regex) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите сохранённую внутреннюю функцию со вторым аргументом. */
    public static int sum(Object bound,int second) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите сохранённую внутреннюю функцию полного сопоставления. */
    public static boolean matches(Object bound,String text) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите сохранённую внутреннюю функцию разбиения; не выполняйте разбиение во входе проверки. */
    public static String[] split(Object bound,String text) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
}
