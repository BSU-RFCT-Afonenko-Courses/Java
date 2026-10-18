import java.io.IOException;

public class ManualResourcesDemo {
    static final class ManualResource {
        private final String name;
        private final boolean failClose;

        ManualResource(String name, boolean failOpen, boolean failClose)
                throws IOException {
            if (failOpen) throw new IOException("open " + name);
            this.name = name;
            this.failClose = failClose;
            System.out.println("open " + name);
        }

        void close() throws IOException {
            System.out.println("close " + name);
            if (failClose) throw new IOException("close " + name);
        }
    }

    static void useManually(boolean failSecondOpen, boolean failSecondClose)
            throws IOException {
        var first = new ManualResource("A", false, false);
        try {
            var second = new ManualResource("B", failSecondOpen, failSecondClose);
            try {
                System.out.println("work");
            } finally {
                second.close();
            }
        } finally {
            first.close();
        }
    }

    static void replacedFailure() throws IOException {
        var first = new ManualResource("A", false, true);
        try {
            var second = new ManualResource("B", false, true);
            try {
                throw new IllegalStateException("body");
            } finally {
                second.close();
            }
        } finally {
            first.close();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Укажите опыт: success, open, close, replaced");
            return;
        }
        try {
            switch (args[0]) {
                case "success" -> useManually(false, false);
                case "open" -> useManually(true, false);
                case "close" -> useManually(false, true);
                case "replaced" -> replacedFailure();
                default -> throw new IllegalArgumentException("неизвестный опыт: " + args[0]);
            }
        } catch (IOException failure) {
            ResourceDemo.report(failure);
            System.out.println("suppressed count: " + failure.getSuppressed().length);
        }
    }
}
