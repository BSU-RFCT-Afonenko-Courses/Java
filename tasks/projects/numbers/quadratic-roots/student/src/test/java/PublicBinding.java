/** Обычная привязка к выбранному примеру запуска/оформления. Это не API студента.
 * При ином решении нужна соответствующая привязка либо предметная демонстрация.
 */
class PublicBinding {
    static Class<?> entry(){return RunExample.class;}

    static double[] roots(String text){String value=text.strip();if(value.equals("Действительных корней нет."))return new double[0];if(!value.startsWith("Корни: "))throw new AssertionError("Сопоставьте смысл результата с оформлением выбранной привязки.");return coursesupport.OutputNumbers.finiteNumbers(value.substring("Корни: ".length()));}
}
