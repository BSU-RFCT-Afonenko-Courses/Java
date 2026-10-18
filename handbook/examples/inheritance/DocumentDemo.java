public class DocumentDemo {
    static class Document {
        private final String title;

        Document(String title) {
            this.title = java.util.Objects.requireNonNull(title);
        }

        String summary() { return title; }
    }

    static class TextDocument extends Document {
        private final int characters;

        TextDocument(String title, int characters) {
            super(title);
            if (characters < 0) {
                throw new IllegalArgumentException("Отрицательное число символов");
            }
            this.characters = characters;
        }

        TextDocument(String title) { this(title, 0); }
        int characters() { return characters; }

        @Override
        String summary() {
            return super.summary() + ": " + characters + " chars";
        }
    }

    static class SilentDocument extends Document {
        SilentDocument(String title) { super(title); }
        @Override String summary() { return null; }
    }

    static void print(Document document) {
        System.out.println(document.summary());
    }

    static String kind(Document value) { return "Document overload"; }
    static String kind(TextDocument value) { return "Text overload"; }
    static int summaryLength(Document document) {
        return document.summary().length();
    }

    public static void main(String[] args) {
        Document plain = new Document("Guide");
        TextDocument text = new TextDocument("Notes", 120);
        Document value = text;
        print(plain);
        print(value);
        System.out.println(kind(value));
        System.out.println(kind(text));
        System.out.println(value.summary());
        System.out.println(text.summary());
        if (value instanceof TextDocument narrowed) {
            System.out.println(narrowed.characters());
            System.out.println(narrowed == value);
        }
        var same = value; // Статический тип — Document.
        // same.characters(); // В Document такой операции нет.
        System.out.println(summaryLength(same));
        try {
            summaryLength(new SilentDocument("Notes"));
        } catch (NullPointerException error) {
            System.out.println(error.getClass().getSimpleName());
        }
        print(new TextDocument("Empty"));
    }
}
