import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study { static { if(Boolean.parseBoolean("true")) throw new AssertionError("API admission executed student initialization"); }
 private static Object customer(long id, String surname, String name, String patronymic, String address, String card, String account){return new Customer(id,surname,name,patronymic,address,card,account);}
 public static Object secondConstructor(){return new Customer(9,"Short");}
 public static List<?> values(Object customer){Customer c=(Customer)customer;return List.of(c.id(),c.last(),c.first(),c.middle(),c.address(),c.card(),c.account());}
 public static void edit(Object customer, long id, String surname, String name, String patronymic, String address, String card, String account){Customer c=(Customer)customer;c.id(id);c.last(surname);c.first(name);c.middle(patronymic);c.address(address);c.card(card);c.account(account);}
 public static String text(Object customer){return customer.toString();}
 public static Object[] array(Object... customers){return Arrays.copyOf(customers,customers.length,Customer[].class);}
 public static Object[] sorted(Object[] customers){return Customer.sorted((Customer[])customers);}
 public static Object[] inCardRange(Object[] customers, String lower, String upper){return Customer.range((Customer[])customers,lower,upper);}
}
