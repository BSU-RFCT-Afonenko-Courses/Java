public class Study {
  public static String longest(String text) {
    return text.replaceAll("[A-Za-zА-Яа-яЁё]+", "");
  }
}