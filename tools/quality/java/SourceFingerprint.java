import com.sun.source.util.JavacTask;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/** Parse-only fingerprints preserve imports, declarations and literal values during formatting. */
class SourceFingerprint {
    public static void main(String[] args) throws Exception {
        var compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Нужен JDK с модулем jdk.compiler");
        }
        try (var manager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8)) {
            var diagnostics = new DiagnosticCollector<JavaFileObject>();
            var inputs = manager.getJavaFileObjectsFromStrings(Arrays.asList(args));
            var task =
                    (JavacTask)
                            compiler.getTask(
                                    null,
                                    manager,
                                    diagnostics,
                                    List.of("--release", "25", "-proc:none"),
                                    null,
                                    inputs);
            var trees = task.parse();
            boolean failed = false;
            for (var diagnostic : diagnostics.getDiagnostics()) {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
                    System.err.println(diagnostic);
                    failed = true;
                }
            }
            if (failed) System.exit(1);
            for (var tree : trees) {
                byte[] digest =
                        MessageDigest.getInstance("SHA-256")
                                .digest(tree.toString().getBytes(StandardCharsets.UTF_8));
                String name = Path.of(tree.getSourceFile().toUri()).toString();
                System.out.println(
                        Path.of("").toAbsolutePath().relativize(Path.of(name))
                                + "\t"
                                + HexFormat.of().formatHex(digest));
            }
        }
    }
}
