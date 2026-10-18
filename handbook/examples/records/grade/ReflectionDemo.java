package grade;

public class ReflectionDemo {
    public static void main(String[] args) {
        Student student = new Student(" s1 ", " Ada ", 90);
        Class<?> type = Student.class;
        System.out.println(student.id());
        System.out.println(type.isRecord());
        for (var component : type.getRecordComponents()) {
            System.out.println(component.getName() + ":" + component.getType().getSimpleName());
        }
        System.out.println(type.getName());
        System.out.println(int.class.isPrimitive());
        System.out.println(Integer.class.isPrimitive());
        System.out.println(String.class.getRecordComponents() == null);
    }
}
