class WiderThrows {
    static class Base {
        void load() throws java.io.IOException {}
    }
    static class Child extends Base {
        @Override
        void load() throws Exception {} // Exception шире разрешённого IOException.
    }
}
