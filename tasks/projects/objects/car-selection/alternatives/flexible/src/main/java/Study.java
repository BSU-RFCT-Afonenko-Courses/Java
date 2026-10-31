import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object car(long id, String make, String model, int year, String color, double price, String registration){return new flexible.Car((int)id,make,model,year,color,java.math.BigDecimal.valueOf(price),registration);}
 public static Object secondConstructor(){return new flexible.Car(9,"A","M");}
 public static List<?> values(Object car){flexible.Car c=(flexible.Car)car;return List.of((long)c.id(),c.make(),c.model(),(long)c.year(),c.color(),c.price().doubleValue(),c.registration());}
 public static void edit(Object car, long id, String make, String model, int year, String color, double price, String registration){flexible.Car c=(flexible.Car)car;c.id((int)id);c.make(make);c.model(model);c.year(year);c.color(color);c.price(java.math.BigDecimal.valueOf(price));c.registration(registration);}
 public static String text(Object car){return car.toString();}
 public static Object[] array(Object... cars){return Arrays.copyOf(cars,cars.length,flexible.Car[].class);}
 public static Object[] byMake(Object[] cars, String make){return flexible.Car.ofBrand((flexible.Car[])cars,make).toArray();}
 public static Object[] olderThan(Object[] cars, String model, int currentYear, int age){return flexible.Car.olderThan((flexible.Car[])cars,model,currentYear,age).toArray();}
 public static Object[] dearerThan(Object[] cars, int year, double price){return flexible.Car.pricedAbove((flexible.Car[])cars,year,java.math.BigDecimal.valueOf(price)).toArray();}
}
