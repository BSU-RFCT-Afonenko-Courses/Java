package company.app;
import company.model.Aircraft;
import company.model.Airline;
import company.model.PassengerPlane;
import company.model.CargoPlane;
import company.persistence.AirlineConnector;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
public final class ConsoleMenu {
    public void main(String[] args) throws Exception {
        Path file = Path.of(args.length == 0 ? "airline.bin" : args[0]);
        Airline airline = new Airline(List.of(new PassengerPlane("1", 150, 12, 3000, 2400),
                new CargoPlane("2", 2, 20, 4500, 3000), new PassengerPlane("3", 80, 6, 1800, 1500)));
        var input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.println("ObjectStream/default Serializable; static не сохраняется, transient после загрузки равен 0.");
        System.out.println("Сортировка по дальности по возрастанию. 0 — выход; 1 — парк; 2 — сохранить; 3 — загрузить; 4 — обновить.");
        for (String command; (command = input.readLine()) != null && !command.equals("0");) {
            try {
                switch (command) {
                    case "1" -> {
                        System.out.println("Места=" + airline.capacity() + "; тонны=" + airline.payload());
                        for (Aircraft plane : airline.byRange()) System.out.println(plane.getId() + ":" + plane.getRangeKm());
                        System.out.println("Отбор [1500,2400]: " + airline.matchingFuel(1500, 2400).stream().map(Aircraft::getId).toList());
                    }
                    case "2" -> {
                        Airline.setGlobalLabel("before");
                        airline.setMenuCount(77);
                        AirlineConnector.save(file, airline);
                        System.out.println("Сохранено");
                    }
                    case "3" -> {
                        Airline.setGlobalLabel("after");
                        airline = AirlineConnector.load(file);
                        System.out.println("Загружено: static=" + Airline.getGlobalLabel() + "; transient=" + airline.getMenuCount());
                    }
                    case "4" -> { AirlineConnector.changeSeats(file, "3", 100); airline = AirlineConnector.load(file); System.out.println("Обновлено"); }
                    default -> System.out.println("Неизвестный пункт");
                }
            } catch (Exception error) { System.err.println("Ошибка модели/файла: " + error.getMessage()); }
        }
    }
}
