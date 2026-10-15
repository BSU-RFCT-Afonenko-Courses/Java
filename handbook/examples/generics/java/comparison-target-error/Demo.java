import java.util.*;
class Demo {
    static class Person implements Comparable<Person> {
        final int rank;
        Person(int rank) {
            this.rank = rank;
        }
        public int compareTo(Person other) {
            return Integer.compare(rank, other.rank);
        }
    }

    static final class Student extends Person {
        Student(int rank) {
            super(rank);
        }
    }
    static <T extends Comparable<T>> T strict(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }
    public static void main(String[] args) {
        Student a = new Student(1);
        Student b = new Student(2);
        Student student = strict(a, b); // ошибка компиляции
    }
}
