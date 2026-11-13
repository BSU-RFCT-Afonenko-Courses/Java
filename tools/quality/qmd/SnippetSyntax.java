import com.sun.source.util.JavacTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

/** Syntax only: no compilation, symbol resolution, or invented teaching APIs. */
class SnippetSyntax {
    public static void main(String[] args) throws Exception {
        var compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("A JDK 25+ is required");
        try (var files =
                        compiler.getStandardFileManager(null, Locale.ROOT, StandardCharsets.UTF_8);
                var input =
                        new BufferedReader(
                                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = input.readLine()) != null) {
                String[] parts = line.split("\\t", 2);
                String source =
                        new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
                var diagnostics = new DiagnosticCollector<JavaFileObject>();
                var file =
                        new SimpleJavaFileObject(
                                URI.create("string:///Snippet.java"), JavaFileObject.Kind.SOURCE) {
                            @Override
                            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                                return source;
                            }
                        };
                var task =
                        (JavacTask)
                                compiler.getTask(
                                        null,
                                        files,
                                        diagnostics,
                                        List.of("--release", "25", "-proc:none"),
                                        null,
                                        List.of(file));
                var trees = task.parse();
                var tree = trees.iterator().next();
                String fingerprint =
                        HexFormat.of()
                                .formatHex(
                                        MessageDigest.getInstance("SHA-256")
                                                .digest(
                                                        tree.toString()
                                                                .getBytes(StandardCharsets.UTF_8)));
                String error =
                        diagnostics.getDiagnostics().stream()
                                .filter(d -> d.getKind() == Diagnostic.Kind.ERROR)
                                .map(d -> d.getLineNumber() + ": " + d.getMessage(Locale.ROOT))
                                .findFirst()
                                .orElse("");
                System.out.println(
                        parts[0]
                                + "\t"
                                + Base64.getEncoder()
                                        .encodeToString(error.getBytes(StandardCharsets.UTF_8))
                                + "\t"
                                + fingerprint);
            }
        }
    }
}
