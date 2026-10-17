import java.util.Optional;
import java.util.function.Supplier;

public class OptionalDemo {
    public static void main(String[] args) {
        Supplier<String> fallback = () -> {
            System.out.println("fallback");
            return "нет участников";
        };
        Optional<String> present = Optional.of("Ada");
        Optional<String> empty = Optional.empty();
        System.out.println(present.orElse(fallback.get()));
        System.out.println(present.orElseGet(fallback));
        System.out.println(empty.orElse(fallback.get()));
        System.out.println(empty.orElseGet(fallback));
    }
}
