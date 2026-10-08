# Текущее оформление курса Java

Рекомендации сверены с [template e36fd64](https://github.com/Afonenko-Course-Tools/quarto-template-course/tree/e36fd64cdc9fb8fb56238883248206c8856104f5). Нормативные правила и код берутся из одних закреплённых выпусков владельцев, перечисленных в [UPSTREAM.md](../UPSTREAM.md): Core 4.0.1, Publisher 5.0.0, QRC 3.0.0, Download 2.0.0, PrairieLearn 3.1.0 candidate по exact commit (выпуск ещё не опубликован). Template служит руководством; предметный курс сохраняет свои пять частей, темы, IDs и ссылки.

## Банк и обычные упражнения

Банк явно включён в 60 каталогах канонических тем и отдельном pilot каталоге через `_metadata.yml`; теория, справочник, обычные слайды и открытая Unicode demonstration остаются вне банка. У банковских `exr-*` обязательны собственные difficulty и положительное целое time. Поля главы служат указателям, не заменяют поля задачи. [Оценки времени](prairielearn-time-estimates.json) предварительны.

Открытые исследования имеют statement-visibility open; контрольные условия restricted. Core сам удаляет restricted условия, решения и ссылки назначений из student. Преподавательские notes/ключи отделены от условия. Широкая обёртка `when-profile="full"` вокруг банковского контроля не используется. Нативное условие профиля остаётся подходящим для дополнительного преподавательского текста вне этой политики.

Обычные Unicode `exm`/`sol` сохраняют публичную семантику Quarto и свои IDs; устаревшие Course роли prediction/self-check удалены, смысл остаётся в заголовках. Банковское решение связывается одинаковым suffix в том же QMD либо анонимной вложенной `.solution`; банковский `for` не используется. [Правила задач](https://github.com/Afonenko-Course-Tools/quarto-template-course/blob/e36fd64cdc9fb8fb56238883248206c8856104f5/guide/exercises.qmd), [решений](https://github.com/Afonenko-Course-Tools/quarto-course/blob/v4.0.1/docs/authoring-style-guide.md).

## Работы и показ материала

Одна QMD описывает одну работу. `.task-items` содержит упорядоченные местные ссылки; defaults назначений required/individual. Stage может отсутствовать. Состав 60 существующих test работ — три соответствующих контроля, без открытого исследования. Декларация платформы не делает 177 placeholder-проектов исполняемыми.

Presentation оформляет доступный после Core текст. Сохранены восемь лекций и шесть практических презентаций, разделы `##`, слайды `###`, IDs и native Reveal Navigation. Старый параметр `course-presentation.mode` удалён; доступными ответами управляет `answers: auto|expanded`. Публичные notes и режимы чтения/аудитории принадлежат Presentation.

## Сборка и публикация

Publisher использует `subprojects: [theory, tasks, lectures, practice, handbook]`. Core post предшествует Download/QRC; collect каждой части стоит последним. Один course.id задан только в корне. Fail-if-warnings установлен в каждом проекте: корневой CLI флаг не наследуется дочерними render.

Student/full имеют независимые output-dir и согласованный course.view. Student банка отключает встроенный source; source QMD, `_generated`, все output деревья и private project partitions исключены из native resources. Явные ZIP ресурсы выбирают только шесть стартеров `student/`; reference/tests рядом не архивируются. У restricted задачи shortcode исчезает вместе с условием через Core, параметр resources.profiles только разрешает оставшийся запрос. Открытая demonstration сохраняет три архива в обоих профилях.

QRC связывает адреса текущих публикаций; он не копирует условия и не включает их в банк. Native pre/post guards проверяют модель и ресурсы текущего run. CI использует Quarto 1.11.5/CUE 0.17.1 и последовательность student → full → student, без старого внешнего tests/features.ts.

Успешность миграции подтверждается выходом трёх полных команд и проверкой фактических HTML/search/QRC/ZIP. Исторические проверки прежних выпусков и retained outputs после ошибок не заменяют эту приёмку. Новые результаты фиксируются в отдельном отчёте.

Пилот Java теперь использует стандартный `pl-file-editor` с одной заготовкой класса.
PL provider установлен целым пакетом по commit `11a6c73ee306c51b25f0714869af69def0234667`
(кандидат 3.1.0, отдельный выпуск ещё не опубликован). Остальные pins сохранены.
Полный student проект предназначен для локальной работы; в editor delivery он не копируется.
