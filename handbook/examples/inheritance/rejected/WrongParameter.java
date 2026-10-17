class WrongParameter {
    static class Document {}
    static class TextDocument extends Document {}
    static class Base {
        void accept(Document value) {}
    }
    static class Child extends Base {
        @Override // Другая сигнатура: это перегрузка, переопределения нет.
        void accept(TextDocument value) {}
    }
}
