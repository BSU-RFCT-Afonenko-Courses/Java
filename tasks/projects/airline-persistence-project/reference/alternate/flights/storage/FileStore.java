package flights.storage;
import flights.domain.Company;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
public final class FileStore {
    private final Path file;
    public FileStore(Path file) { this.file = file; }
    public void store(Company company) throws IOException {
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        try (var output = new ObjectOutputStream(Files.newOutputStream(file))) { output.writeObject(company); }
    }
    public Company restore() throws IOException, ClassNotFoundException {
        try (var input = new ObjectInputStream(Files.newInputStream(file))) { return (Company) input.readObject(); }
    }
    public void amend(int number, int capacity) throws IOException, ClassNotFoundException {
        Company model = restore();
        model.capacity(number, capacity);
        store(model);
    }
}
