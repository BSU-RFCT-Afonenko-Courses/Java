import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object customer(long id, String surname, String name, String patronymic, String address, String card, String account){return new alternate.Customer(id,surname,name,patronymic,address,card,account);}
 public static Object secondConstructor(){return new alternate.Customer(9,"Short");}
 public static List<?> values(Object customer){alternate.Customer c=(alternate.Customer)customer;return List.of(c.id(),c.last(),c.first(),c.middle(),c.address(),c.card(),c.account());}
 public static void edit(Object customer, long id, String surname, String name, String patronymic, String address, String card, String account){alternate.Customer c=(alternate.Customer)customer;c.id(id);c.last(surname);c.first(name);c.middle(patronymic);c.address(address);c.card(card);c.account(account);}
 public static String text(Object customer){return customer.toString();}
 public static Object[] array(Object... customers){return Arrays.copyOf(customers,customers.length,alternate.Customer[].class);}
 public static Object[] sorted(Object[] customers){return alternate.Customer.sorted((alternate.Customer[])customers);}
 public static Object[] inCardRange(Object[] customers, String lower, String upper){return alternate.Customer.range((alternate.Customer[])customers,lower,upper);}
}
