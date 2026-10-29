# Обратная связь Java в PrairieLearn

Статический аудит от 28 октября 2026 года. Пользователь выбрал обычный Java/JUnit, короткое пояснение и подключаемый исходник; новые карточки и сценарный DSL исключены.
Портал недоступен: изучены исходники курса, grader и PrairieLearn. Тесты не запускались; ниже нет снимков или наблюдений живого интерфейса.

**Основной технический вывод:** полезные сообщения уже возникают в grader, но проекция для студента часть из них теряет.
Исправление следует обсуждать как передачу понятной диагностики обычных тестов, без новой модели сценариев.
Независимая содержательная рецензия подтверждает частичную полезность: короткий CLI-пример часто позволяет найти отличие, русские предметные названия удачны; готовность всей обратной связи не установлена. Приоритет — достоверная причина/стадия ошибки и смысл полного результата, затем сравнимость значений и устранение повторов.

## База и доказанность

Java — `273ce2d63cc245d8c70e00167acb15e4bc63ef64`, установленные Core/PL 8.3.0.
[CI/toolchain.json](../../../CI/toolchain.json#L6) закрепляет Platform 2.4.0 / `1cab18c5e693210b2b52930cacb00ceb5acd9797`.
Ссылки Platform ниже относятся к этому commit. Renderer прочитан на upstream `92584fe426ececb84bc2d09de9975c7056c0c5f6`, указанном в [Dockerfile][docker].
Связка конкретного renderer с нынешним живым образом, CSS и сервером не подтверждена; это граница доказательства, а не установленный дефект выпуска.

| Вид свидетельства | Что установлено |
| --- | --- |
| Измеренный охват исходников | Все profiles/public-cases в `tasks/_quarto.yml`; выбранные материализацией 120 `PublicChecks.java` и общие обёртки |
| Статическая реконструкция | Путь JUnit → grader → student payload → предполагаемый штатный renderer; ветви compile/wrong-answer/no observation |
| Будущая живая проверка | Реальный экран студента, CSS, полный путь конкретного образа, скачивание/просмотр исходника и повторная отправка |

Срез [tasks/_quarto.yml](../../../tasks/_quarto.yml#L433) имеет SHA256 `461b6505ef35aece1f9747ba11bc097373958a264cdec7d073577ae00f83cd85`.
Числа ниже относятся к объявлениям, не числу задач, студентов или сдач на сервере.

## Названия есть; полезность пояснений неодинакова

| Показатель полного прохода metadata | Результат |
| --- | --- |
| Профили | 121; у 118 есть `public-cases` и `feedback` |
| Объявленные случаи | 236, ровно по два в этих 118 профилях |
| Названия | 236/236 содержат кириллицу; 230 разных названий — не доказательство полной русскоязычности и ясности каждого текста |
| Служебные ID | `normal` и `boundary` — по 118; это не видимые заголовки |
| Ситуация | `input` — 42, `state` — 166, `action` — 204; поля могут сочетаться, модель не сводится к stdin |
| Объяснение | 99 разных `requirement`, лишь 19 разных `what-check`; у 113/118 есть «Сопоставьте» |

Например, [reverse-array](../../../tasks/_quarto.yml#L661) имеет предметное название «Обратный порядок с повторением».
Нынешнее `tests[].name` берётся из `publicCases[].name`, а не из `normal/boundary` JUnit-метода.
У общих [CLI](../../../tasks/test-support/junit/PublicScenarioTest.java#L7) и [API-обёрток](../../../tasks/test-support/junit/DirectPublicExamplesTest.java#L7) нет `@DisplayName`.
Это вопрос перехода к выбранному обычному JUnit, а не доказательство нынешнего английского заголовка в PL.

36 `action` дословно предлагают выполнить опубликованное действие через согласованную привязку API.
Такая инструкция может честно обозначать неподготовленную привязку, но не объясняет конкретную ошибку выполненного решения.
Отбор grading выбирает 120 разных `PublicChecks.java` через 128 записей materialization; это не взаимно-однозначное соответствие 118 профилям.
Частные исходники прежней преподавательской поставки не читались. Лексические признаки сериализаторов не выдаются за наблюдённый экран.

## Где теряется содержание

Официальный [JUnitAutograder][junit] сохраняет display name, assertion message и TestReporter output.
[Наш адаптер][adapter] собирает исходы/наблюдения; [grade.py][grade] сохраняет исходный результат, но обычный student route заменяет его через `student_result`.
[student_feedback.py][feedback] строит новый payload; исходные javac/API-диагностика, assertion message и JUnit display name туда не переносятся.
Это потеря в выдаваемой проекции, а не утверждение удаления исходного операторского результата.

| Замечание | Проверяемый источник | Последствие и минимальное направление переработки |
| --- | --- | --- |
| **P1: потеря compiler file/line/error** | [grade.py:517–541][compile] получает javac; [student_result:35–38][projection] заменяет его | Передавать причину и место ошибки в присланном исходнике; общий совет не заменяет диагностику |
| **P2: whatCheck подменяет даже совет компиляции** | [feedback:33–34][override]; [reverse-array:670–672](../../../tasks/_quarto.yml#L670) | Различать класс ошибки и предметную подсказку; не заменять compile-совет фразой «Сопоставьте результат…» |
| **P2: assertions/API объяснение теряются** | [JUnit message:213–219][assertion], [API:551][api], затем [projection][projection] | Сохранять понятное авторское сообщение и предметные expected/actual, когда они есть; выбрать русские `@DisplayName` для обычного JUnit |
| **P2: not-run не доказывает неисполнение** | [feedback:43–50][observation] требует одно совпадение id+test+actual | Сообщать об отсутствии наблюдения отдельно от доказанного неисполнения или неверного ответа |
| Ясность представления | [renderer:72–95][template] | Не повторять название/статус без пользы; проверить строки и подписи на живом экране |

`TestReporter` должен публиковать одновременно `public-case` и `actual` для нынешнего observation route ([adapter:112–115][reporter]).
Обычный успешный/assertion-failed JUnit без этой публикации может выполняться, но получить `not-run` в этой проекции.
Это аудит существующего протокола, не требование нового descriptor, обёртки или карточки для каждого будущего теста.

**Статический пример compile, не запуск:** для reverse-array проекция начинает сообщение «Исходники не скомпилированы»,
но заканчивает нынешним `what-check`: «Сопоставьте результат с условием и показанным открытым сценарием».
Тот же общий текст идёт в `format_errors`; файл/строка javac не передаются. Из этого нельзя вывести наблюдённый UI-балл.

## Что помогает найти ошибку

| Предмет | Что уже содержательно | Чего не хватает в показательной проверке |
| --- | --- | --- |
| CLI reverse-array | Значения и порядок, отдельное объяснение числа элементов | [checkValues](../../../tasks/projects/arrays/reverse-array/student/src/test/java/PublicChecks.java#L15) не называет индекс первого различия и пару expected/actual |
| Book / отбор объектов | Состояние, порядок, кратность и сохранность входа | [actual](../../../tasks/projects/objects/book-selection/student/src/test/java/PublicChecks.java#L29) даёт позиционные списки полей; общая ошибка не называет неудавшийся отбор |
| Generic stack / ссылки | Проверяются значения и прежние ссылки, не stdout | [normalChecked](../../../tasks/projects/generics/generic-stack/student/src/test/java/PublicChecks.java#L14) выводит `false/false/false` без подписи каждого действия |
| Файл с частотами | Слова, счётчики, файл и порядок | [actual](../../../tasks/projects/files/poem-word-frequency/student/src/test/java/PublicChecks.java#L14) повторяет список, отчёт и hex вместо локализации слова/строки |

Например, `0 7 -2 8` вместо `0 7 -2 7` позволяет вручную найти ошибку reverse-array, но общее assertion не указывает последний элемент.
Это реконструированный пример возможной неудачи, не результат запуска. Аналогично список Book или флаги стека требуют предметных подписей, а не преобразования API в stdin.
Адреса вида `Class@hex` в просмотренных сериализаторах не подтверждены: записи, списки, `Arrays.toString/deepToString` содержат данные.
Нельзя объявлять всякий `toString` бессмысленным или, наоборот, полагаться на произвольный пользовательский `toString` как на универсальный отчёт.

`EOF`, отсутствие аргументов, точные байты и отсутствие LF содержательны там, где этого требует задача.
Для обычного списка повторный hex/UTF-8 увеличивает шум; у [байтового копирования](../../../tasks/projects/files/byte-file-copy/student/src/test/java/PublicChecks.java#L14) точные байты — предмет проверки.
Локальный `PublicChecks.main` и его escape-вывод не являются тем же путём, что JUnit/PL.

## Строки, результат и границы проверки

В payload `description` содержит настоящие переводы строк, но [шаблон][template] выводит их обычным HTML без `pre/br`;
`message/output` имеют `pre`. Сохранность строк зависит и от CSS, который в живом UI не проверен.
В 17 полях 14 объявлений есть буквальное `\n`, а не настоящий перенос; это отдельный вопрос исходного текста.
Русский payload не переводит штатные подписи renderer автоматически.

Успех двух demo не означает полный результат: [student_feedback:26–42][score] передаёт score полного запуска, а не считает его по двум случаям.
При двух passed demo и неуспешном полном наборе нужно ясно назвать границу результата и действительно установленное нарушенное требование; одного общего `behavior-failure` для его выдумывания недостаточно.
Сохраняется смешанное оценивание 70/30; ручная часть оценивается отдельно. Ни формат результата, ни веса этим аудитом не изменяются.

Для последующей переработки нужны узкие свидетельства: compile-ошибка с файлом/строкой; wrong-answer с предметным различием;
обычный JUnit с русским именем и понятным assertion; runtime/timeout; отсутствие observation при действительно выполненном тесте.
Их фактический вид и доступность исходника проверяются в сессии рефакторинга на выбранной версии сервера.
Это проверка передачи и ясности сообщений существующего grader, без новых карточек или сценарного DSL.

[docker]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/prairielearn/Dockerfile#L1
[junit]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/java25-junit-v1/upstream/JUnitAutograder.java#L167
[adapter]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/java25-junit-v1/PlatformJUnitAdapter.java#L126
[grade]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L981
[feedback]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L35
[compile]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L517
[projection]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L35
[override]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L33
[assertion]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/java25-junit-v1/upstream/JUnitAutograder.java#L213
[api]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/images/java25-grader/grade.py#L551
[observation]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L43
[template]: https://github.com/PrairieLearn/PrairieLearn/blob/92584fe426ececb84bc2d09de9975c7056c0c5f6/apps/prairielearn/elements/pl-external-grader-results/pl-external-grader-results.mustache#L72
[reporter]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/java25-junit-v1/PlatformJUnitAdapter.java#L112
[score]: https://github.com/Afonenko-Course-Tools/prairielearn-platform/blob/1cab18c5e693210b2b52930cacb00ceb5acd9797/runtime/student_feedback.py#L26
