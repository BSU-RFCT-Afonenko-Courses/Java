package course.examples.objects;

public final class Book {
    private final String title;
    private boolean borrowed;

    public Book(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("nonblank title required");
        }
        this.title = title;
    }

    public String title() { return title; }
    public boolean borrowed() { return borrowed; }

    public void borrow() {
        if (borrowed) {
            throw new IllegalStateException("already borrowed");
        }
        borrowed = true;
    }
}
