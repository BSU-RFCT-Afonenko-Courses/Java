public class Main { public static void main(String[] a) throws Exception {System.out.print(new String(System.in.readAllBytes()).chars().filter(c->c==' ').count());} }
