final class Ticket {
    private static long nextId = 1;
    private final long id = nextId++;

    long id() { return id; }
}
