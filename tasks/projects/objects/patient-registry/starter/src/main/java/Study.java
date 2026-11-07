import java.util.*;
/** Входы к вашему решению. Реализуйте модель и алгоритм по условию.
 * Внутренние имена, пакеты, видимость и наследование свободны. Не заменяйте
 * настоящую модель отдельным снимком и не скрывайте мутацию копированием во входе.
 * Законные копии, вычисляемые самим алгоритмом по условию, разрешены. */
public class Study {
    /** Создайте настоящий объект своей модели выбранным вами конструктором. Сигнатура входа не назначает конструктор: отсутствующие в нём свойства можно заполнить методами изменения; уже переданные параметры не исправляйте повторной записью. */
    public static Object patient(long id, String surname, String name, String patronymic, String address, String phone, long card, String diagnosis) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Вызовите другой настоящий конструктор своей модели с самостоятельно выбранными допустимыми данными. Верните объект до исправления его состояния через setters; набор параметров конструктора свободен. */
    public static Object secondConstructor() { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Прочитайте текущие свойства того же объекта в порядке: id (Long), фамилия, имя, отчество, адрес, телефон, номер медкарты (Long), диагноз. Текстовые свойства — String. Наблюдаемый список не заменяет предметную модель; не подменяйте свойства ожидаемыми значениями. */
    public static List<?> values(Object patient) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Измените все перечисленные свойства через реальные операции своей модели; последующее values читает состояние того же объекта. */
    public static void edit(Object patient, long id, String surname, String name, String patronymic, String address, String phone, long card, String diagnosis) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Верните настоящий toString() предметного объекта; конкретный небланковый формат свободен. */
    public static String text(Object patient) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Создайте массив фактического класса вашей модели, в том числе для пустого набора. Сохраните все переданные ссылки, повторы и порядок. Object[] — только тип этой границы; передавайте предметной операции тот же массив, без маскирующей копии. */
    public static Object[] array(Object... patients) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Вызовите предметный метод на переданном типизированном массиве. Сохраните исходные ячейки и свойства объектов. Результат списка/массива можно механически упаковать в Object[] с прежними вхождениями и фактическим порядком. Равнозначные копии, полученные самим алгоритмом, допустимы. */
    public static Object[] withDiagnosis(Object[] patients, String diagnosis) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
    /** Вызовите предметный метод на переданном типизированном массиве. Сохраните исходные ячейки и свойства объектов. Результат списка/массива можно механически упаковать в Object[] с прежними вхождениями и фактическим порядком. Равнозначные копии, полученные самим алгоритмом, допустимы. */
    public static Object[] inCardRange(Object[] patients, long lower, long upper) { throw new UnsupportedOperationException("TODO: реализуйте решение"); }
}
