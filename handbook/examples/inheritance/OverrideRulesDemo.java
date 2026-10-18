public class OverrideRulesDemo {
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
        int characters() { return characters; }
        @Override String summary() {
            return super.summary() + ": " + characters + " chars";
        }
    }

    abstract static class DocumentSource {
        protected abstract Document load() throws java.io.IOException;
    }

    static final class MemorySource extends DocumentSource {
        private final TextDocument document;
        MemorySource(TextDocument document) {
            this.document = java.util.Objects.requireNonNull(document);
        }
        @Override public TextDocument load() { return document; }
    }

    public static void main(String[] args) throws java.io.IOException {
        MemorySource memory = new MemorySource(new TextDocument("Notes", 120));
        TextDocument text = memory.load();
        System.out.println(text.characters());
        DocumentSource source = memory;
        Document document = source.load();
        System.out.println(document == text);
        // TextDocument another = source.load(); // Результат имеет тип Document.
    }
}
