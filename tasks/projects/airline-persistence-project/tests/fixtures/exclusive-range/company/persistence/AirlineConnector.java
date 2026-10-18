package company.persistence;
import company.model.Airline;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
public final class AirlineConnector {
    private AirlineConnector() { }
    public static void save(Path file, Airline airline) throws IOException {
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        try (var out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(airline);
        }
    }
    public static Airline load(Path file) throws IOException, ClassNotFoundException {
        try (var in = new ObjectInputStream(Files.newInputStream(file))) {
            return (Airline) in.readObject();
        }
    }
    public static void changeSeats(Path file, String id, int seats) throws IOException, ClassNotFoundException {
        Airline loaded = load(file);
        loaded.changeSeats(id, seats);
        save(file, loaded);
    }
}
