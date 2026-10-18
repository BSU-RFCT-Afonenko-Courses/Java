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
    static <T extends Comparable<? super T>> T flexible(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }
    public static void main(String[] args) {
        Student a = new Student(1);
        Student b = new Student(2);
        Person person = strict(a, b);
        System.out.println(person == b); // true
        Student student = flexible(a, b);
        System.out.println(student == b); // true
        var inferred = strict(a, b);
        System.out.println(inferred == b);
    }
}
