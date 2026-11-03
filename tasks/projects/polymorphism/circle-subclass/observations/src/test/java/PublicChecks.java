import java.lang.Double;
import java.lang.Math;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Проверки свойств одного Circle; привязка к решению находится в TaskBinding. */
public class PublicChecks {
    public static void checkCircle(double radius) {
        Object circle = TaskBinding.circle(radius);
        double diameter = 2 * radius;
        double expectedArea = Math.PI * radius * radius;
        double tolerance = Math.max(1e-9, 1e-12 * Math.abs(expectedArea));
        double area = TaskBinding.area(circle);

        assertAll(
            () -> assertEquals(diameter, TaskBinding.width(circle),
                "Ширина круга должна равняться диаметру"),
            () -> assertEquals(diameter, TaskBinding.height(circle),
                "Высота круга должна равняться диаметру"),
            () -> assertEquals("circle", TaskBinding.name(circle),
                "Имя фигуры должно быть circle"),
            () -> assertTrue(Double.isFinite(area), "Площадь должна быть конечной"),
            () -> assertEquals(expectedArea, area, tolerance,
                "Площадь круга должна соответствовать радиусу с заданным допуском")
        );
    }

    /** Консольная самопроверка использует те же assertions, что и открытый JUnit. */
    public static void main(String[] args) {
        boolean success = true;
        for (double radius : new double[]{2, 1e150}) {
            System.out.println("Проверка круга: r=" + radius);
            try {
                checkCircle(radius);
                System.out.println("Проверка пройдена; обязательная ручная часть остаётся.");
            } catch (Throwable error) {
                error.printStackTrace(System.out);
                success = false;
            }
        }
        if (!success) {
            System.exit(1);
        }
    }
}
