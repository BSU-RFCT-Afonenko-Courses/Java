import java.util.*;import java.util.function.*;import java.nio.file.Path;
/** Связь с собственным решением; модель и алгоритм создайте самостоятельно. */
public class Study {
    /** Создайте настоящий самолёт собственной иерархии: kind 0 — пассажирский, 1 — грузовой; fixtureId связывает идентификатор опыта с собственным id. Единицы: места, тонны, километры, литры в час. */
    public static Object plane(String fixtureId,int kind,int seats,double tonnes,double range,double fuel) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Создайте настоящую компанию с переданными самолётами; готовой бизнес-модели в стартере нет. */
    public static Object company(List<?> planes) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Прочитайте действительный парк; передавайте реальные объекты, а не отдельные DTO. */
    public static List<?> planes(Object company) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите собственную сортировку и раскройте действительный результат. Направление объявите до опыта. */
    public static List<?> ordered(Object company) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите собственный поиск во включённом диапазоне; один, несколько или отсутствие представляются списком настоящих найденных самолётов. */
    public static List<?> find(Object company,double lo,double hi) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static int seats(Object plane) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static int totalSeats(Object company) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static double tonnes(Object plane) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static double range(Object plane) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static double fuel(Object plane) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static double totalTonnes(Object company) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static String fixtureId(Object plane) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    public static int kind(Object plane) { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите действительную сериализацию Java в предоставленную область location; имена и количество файлов свободны. */
    public static void save(Object company,Path location) throws Exception { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Вызовите отдельный коннектор, который меняет вместимость выбранного самолёта и сохраняет изменение. При другом законном обновляемом параметре настройте соответствующий проверяющий клиент. */
    public static void updateSeats(Path location,String fixtureId,int seats) throws Exception { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
    /** Загрузите модель из настоящих файлов; исходный объект или кэш адаптера здесь недопустим. */
    public static Object load(Path location) throws Exception { throw new UnsupportedOperationException("TODO: реализуйте по условию"); }
}
