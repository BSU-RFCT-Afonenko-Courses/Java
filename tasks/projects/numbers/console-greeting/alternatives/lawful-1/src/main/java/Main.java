public class Main { public static void main(String[] args) { System.out.print(Message.text(args)); } }

class Message { public static String text(String[] a) { return a.length > 0 ? String.format("Здравствуйте, %s!", a[0]) : "Укажите имя."; } }
