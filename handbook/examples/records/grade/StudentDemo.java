package grade;

public class StudentDemo {
    public static void main(String[] args) {
        Student a = new Student(" s1 ", " Ada ", 90);
        Student b = new Student("s1", "Ada", 80);
        System.out.println(a.id());
        System.out.println(a.name());
        System.out.println(a.equals(b));
        System.out.println(a.equals(new Student("s1", "Ada", 90)));
        try { new Student("s2", "Lin", 101); }
        catch (IllegalArgumentException ex) { System.out.println("Балл отвергнут"); }
        try { new Student("s2", "Li;n", 80); }
        catch (IllegalArgumentException ex) { System.out.println("Разделитель отвергнут"); }
    }
}
