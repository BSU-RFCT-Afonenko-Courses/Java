package course.examples.objects;

public final class Book {
    private final String title;
    private boolean borrowed;

    public Book(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название должно содержать хотя бы один непробельный символ");
        }
        this.title = title;
    }

    public String title() { return title; }
    public boolean borrowed() { return borrowed; }

    public void borrow() {
        if (borrowed) {
            throw new IllegalStateException("Книга уже выдана");
        }
        borrowed = true;
    }
}
