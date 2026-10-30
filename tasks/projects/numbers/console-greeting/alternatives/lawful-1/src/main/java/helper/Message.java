package helper; public class Message { public static String text(String[] a) { return a.length > 0 ? String.format("Здравствуйте, %s!", a[0]) : "Укажите имя."; } }
