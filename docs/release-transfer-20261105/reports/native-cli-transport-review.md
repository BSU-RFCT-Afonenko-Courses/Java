# Независимая рецензия native CLI transport

**APPROVE узкой дельты main() в images/java25-grader/grade.py**, нового tests/test_native_result_exit.py и соответствующей строки docs/student-feedback.md в frozen рабочей копии `/tmp/java-release-work-20261105/platform-feedback` на базе `b6e626de6cc6c3f87094114a2542fc441e5b07f0`. Подтверждённых ошибок не найдено. Source рецензент не менял; владение возвращено исполнителю.

Новый return не меняет result classification, gradable, grading_error или отсутствие score при инфраструктурном отказе. В обычном Native режиме нулевой статус означает успешную доставку безопасной JSON-проекции. Он достигается только после student_result, сериализации, write_text временного файла и его replace в results.json. Исключение в boundary/write/replace не поглощается и не превращается в protocol success. `--verification` по-прежнему возвращает прежний status=2 для инфраструктурного отказа и сохраняет приватную диагностику. Существующий __main__ передаёт возвращаемое значение в SystemExit.

Прочитан новый NativeResultExit: он проверяет native safe/no-score result и прежний operator failure. Русская документация различает успешность доставки и успешность самой проверки; эта формулировка соответствует коду. Изменение транспортного exit не выдаёт студенту частичную либо нулевую оценку за сбой инструмента.

Независимые отличающие probes (`native-cli-review-probes.py`, `native-cli-review-probes.json`):

| Сценарий | Подтверждённый результат |
| --- | --- |
| GraderError с private canary, обычный native вызов | status0, gradable:false, grading_error:true, no score, canary отсутствует |
| тот же GraderError, --verification | status2, gradable:false, no score, private diagnostic сохранён |
| синтетический сбой atomic replace | OSError проброшен; успешного возврата нет, final results.json не создан |

**3 probes PASS, 0.0236 s.** `git diff --check -- images/java25-grader/grade.py tests/test_native_result_exit.py docs/student-feedback.md` завершился0. Новый full JDK/runtime matrix не запускался.

Собственный admission до роли и перед приёмкой повторён за **весь исходный интервал** от `01a1a890-9d74-7d92-a65c-eccacfb9ceab`, chat `01a1a890-9d42-7772-bd99-f2f954898515`, агент `/root/platform_candidate_review`: **9 turn_context, PASS gpt-6.1-sol/high**, Standard, `feedback-review-admission.json`.

**Performance GO не дан.** По последнему сообщению actual daemon не получил java.security.egd из org.gradle.jvmargs; следующая entropy flags дельта на момент поручения ещё не применялась. Предыдущее Source review entropy не является доказательством соблюдения compile30. Actual image qualification, UI и дальнейшие entropy flags требуют своих подтверждений. VM, production, DB, deploy и публикация образов рецензент не затрагивал.
