import java.util.List;
import java.util.stream.Stream;

public class ObservationDemo {
    public static void main(String[] args) {
        boolean found = Stream.of(1, 2, 3).anyMatch(n -> {
            System.out.println("test " + n);
            return n == 2;
        });
        System.out.println("found " + found);
        long count = List.of("Ada", "Lin").stream()
            .peek(name -> System.out.println("peek " + name)).count();
        System.out.println("count " + count);
    }
}
