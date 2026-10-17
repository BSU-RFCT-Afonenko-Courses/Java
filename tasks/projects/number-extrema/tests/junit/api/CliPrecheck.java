import org.prairielearn.api.PlatformApiPrecheck;

/** Проверяет наличие класса Main, указанного в команде запуска. */
public class CliPrecheck {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        api.type("Main");
    }
}
