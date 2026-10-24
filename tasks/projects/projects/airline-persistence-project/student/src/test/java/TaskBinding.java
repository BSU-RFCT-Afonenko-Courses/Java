import java.nio.file.Path;
import java.util.List;

/**
 * Редактируемая test-side привязка открытых наблюдений к вашему решению.
 * Имена и устройство классов решения остаются вашим выбором по условию.
 * Plane — только плоское значение наблюдения клиента, не модель самолёта.
 * Замените тела действительными вызовами своего решения; готовых результатов нет.
 * При недоступном внешнему клиенту API предъявите свою демонстрацию преподавателю.
 */
class TaskBinding {
    record Plane(String id, String kind, int seats, double payload, int range, int fuel) { }

    private static UnsupportedOperationException unfinished() {
        return new UnsupportedOperationException("Соедините открытый клиент с реальным решением");
    }

    static Object fixture() { throw unfinished(); }
    static long capacity(Object model) { throw unfinished(); }
    static double payload(Object model) { throw unfinished(); }
    static List<Plane> planes(Object model) { throw unfinished(); }
    static List<Plane> sorted(Object model) { throw unfinished(); }
    static List<Plane> find(Object model, int low, int high) { throw unfinished(); }
    static String sortDirection() { throw unfinished(); }
    static void save(Object model, Path file) throws Exception { throw unfinished(); }
    static Object load(Path file) throws Exception { throw unfinished(); }
    static void update(Path file, int seats) throws Exception { throw unfinished(); }
}
