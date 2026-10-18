final class Names {
    private final String[] names;
    Names(String[] initial) {
        java.util.Objects.requireNonNull(initial, "initial");
        String[] copy = initial.clone();
        for (String name : copy) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("nonblank name required");
            }
        }
        names = copy;
    }
    String[] names() { return names.clone(); }
}
