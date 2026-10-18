// Ожидается ошибка компиляции: запись неявно final.
record FixedPoint(int x) {}
class RecordSubclass extends FixedPoint {
    RecordSubclass(int x) { super(x); }
}
