import org.prairielearn.api.PlatformApiPrecheck;

/** Проверяет только опубликованное имя загрузочного класса, не форму main. */
public class CliPrecheck {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        api.type("Main");
    }
}
