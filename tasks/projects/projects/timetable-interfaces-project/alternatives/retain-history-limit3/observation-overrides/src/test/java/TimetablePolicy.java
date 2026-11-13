import java.lang.String;

/** Объявлено до опыта: преподаватель удаляется из каталога, прежние занятия сохраняются с его идентификатором; предмет удаляется каскадом вместе с занятиями; выходной исключает занятия; предел школы 3. */
final class TimetablePolicy {
    static final String DELETION = "retain-lessons";
    static final int SCHOOL_LIMIT = 3;
}
