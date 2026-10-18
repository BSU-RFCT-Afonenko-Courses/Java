import java.util.function.IntPredicate;
class ReassignedCapture {
    void example() {
        int threshold = 80;
        IntPredicate rule = n -> n >= threshold;
        threshold = 90;
    }
}
