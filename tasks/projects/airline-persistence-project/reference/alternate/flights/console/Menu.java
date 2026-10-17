package flights.console;
import flights.domain.Airframe;
import flights.domain.Company;
import flights.domain.Jet;
import flights.domain.Freight;
import flights.storage.FileStore;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
public final class Menu {
    public void main(String[] args) throws Exception {
        var store = new FileStore(Path.of(args.length == 0 ? "fleet.bin" : args[0]));
        Company model = new Company(List.of(new Jet(1, 150, 12, 3000, 2400), new Freight(2, 2, 20, 4500, 3000), new Jet(3, 80, 6, 1800, 1500)));
        var input = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.println("Externalizable: static station и transient selections записаны явно. Default serialization сама их не сохраняла бы.");
        System.out.println("Сортировка по дальности по убыванию; поиск возвращает один подходящий самолёт. 0 выход; 1 парк; 2 сохранить; 3 загрузить; 4 обновить.");
        for (String command; (command = input.readLine()) != null && !command.equals("0");) {
            try {
                switch (command) {
                    case "1" -> {
                        System.out.println("Места=" + model.places() + "; тонны=" + model.loadTonnes());
                        for (Airframe plane : model.arranged()) System.out.println(plane.number() + ":" + plane.distance());
                        System.out.println("Отбор [1500,2400]: " + model.find(1500, 2400).map(Airframe::number));
                    }
                    case "2" -> { Company.station("before"); model.mark(77); store.store(model); System.out.println("Сохранено"); }
                    case "3" -> {
                        Company.station("after"); model = store.restore();
                        System.out.println("Загружено: static=" + Company.station() + "; transient=" + model.marks());
                    }
                    case "4" -> { store.amend(3, 100); model = store.restore(); System.out.println("Обновлено"); }
                    default -> System.out.println("Неизвестный пункт");
                }
            } catch (Exception error) { System.err.println("Ошибка модели/файла: " + error.getMessage()); }
        }
    }
}
