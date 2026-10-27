/** Привязка только к выбранному примеру запуска; это не API студента.
 * Для другого решения преподаватель готовит соответствующую привязку либо демонстрацию. */
class PublicBinding {static Class<?> entry(){return RunExample.class;}
    static String correctIf(){return "1";}
    static String correctWhile(){return "4";}
    static String invalid(){return "нет";}
}
