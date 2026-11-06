# Независимая финальная техническая рецензия Platform

Проверена frozen dirty дельта относительно `6616222206d3e0843ebc560af38c2dc3519557f0` в `/tmp/java-release-work-20261105/platform-feedback`: grader upload admission, student_feedback, junit_result, 60 точных overloads Assertions, targeted tests и узкое расширение grading-job schema. **Пакет пока не принят: три подтверждённых замечания.** Source не менялся. Точный tracked diff сохранён в `final-platform-reviewed.diff`; новые tests/test_assertion_message_overloads.py, tests/test_feedback_leaf_context.py и tests/test_upload_java_sources.py также прочитаны.

Рецензент независим от этих изменений: `/root/platform_candidate_review`, chat `01a1a890-9d42-7772-bd99-f2f954898515`. Первичный admission и весь интервал от исходного `01a1a890-9d74-7d92-a65c-eccacfb9ceab` повторно проверены: gpt-6.1-sol/high, Standard, 5 turn_context; [feedback-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/feedback-review-admission.json).

## P1. Protocol 2 fallback подтверждает публичный случай по чужому outcome

`runtime/student_feedback.py:207–212` сохраняет legacy fallback и для protocol 2. Когда ни один case не содержит MethodSource binding `test`, достаточно matching public observation и любого совпадающего successful/failed outcome: публичный случай получает passed/failed и actual без доказанной связи с конкретным leaf. Отсутствующая связь в protocol 2 должна оставаться unknown; legacy fallback допустим только в протоколе 1 или в отдельной Fish/no-JUnit форме.

Отличающий decoder/projection repro: корректные protocol2 counts с одним successful leaf, без поля test у этого leaf; matching public observation `{id:"public",test:"Answer#run",status:"passed",actual:"UNBOUND_ACTUAL"}`. Получены `status=passed`, `actual=UNBOUND_ACTUAL`, и эта строка выведена в native message, хотя leaf не связан с Answer#run. Время 0.009 s.

Вторая форма того же отсутствия доказательств: один leaf имеет test Answer#other, второй не имеет test; объявлен Answer#run. Ветка строк 191–204 выдаёт `not-run` и «отсутствует в завершённом наборе», хотя второй unbound leaf не позволяет это установить. При неполных bindings здесь нужен unknown; not-run доказуем при полном завершённом наборе связей и отсутствии match. Время суммарно с первым decoder probe 0.0157 s. Результаты `final-platform-public-binding-probes.json`.

Это проверка принятой формы DTO, не заявление о возможности студента подделать HMAC. Граница consumer должна сохранять неизвестность при отсутствии доказательств.

## P2. Лимиты extras по-разному применяются к объявленным primary в flat и ZIP

`images/java25-grader/grade.py:1934–1940` учитывает объявленные sourceFiles внутри ZIP как additional members. `_primary_files` для тех же объявленных direct files сохраняет прежний hard limit 1 MiB и список до 64 файлов. В результате транспорт меняет допустимость одного и того же состава.

Реальный JDK25 repro: sourceFiles `[Student.java, Base.java]`, additionalJavaSources `maxFiles=1`, `allowAdditional=false`, остальные caps стандартные. Два объявленных файла отдельно → success, score 1, 14.513 s. Те же файлы с теми же относительными путями только в ZIP → unsafe-submission без score, 0.061 s. Нет ни одного additional source.

Исполнитель и B после обнаружения подтвердили договор: policy maxFiles/maxFileBytes/maxTotalBytes относятся только к sources вне sourceFiles; declared primary имеет прежний hard limit 1 MiB в обоих транспортах. maxArchiveBytes и 256 entries относятся к ZIP целиком. Исправление должно применять этот договор также к mixed flat/ZIP, сохраняя суммарные extra caps и path collision checks.

При исправлении следует согласовать functional evidence: additionalSources.files ограничен schema maxItems128. Нельзя после исключения primary из extra caps неожиданно включить в этот старый список 64 primary + 128 extras и получить schema failure; direct primary в additional evidence исторически не включается.

## P2. Совпадение имени файла с доверенным тестом запрещает законный другой пакет

`images/java25-grader/grade.py:1791`, `:1802–1803` сравнивают flat filename со всеми testFiles, хотя student и trusted находятся в разных корнях. Запрет скрытого trusted basename сужает допустимые имена даже при разных реальных FQCN. Та же проблема есть в reserved paths ZIP на строках 1847–1852; конфликты внутри ответа должны сохраняться, а подмена доверенного класса проверяться по реальным скомпилированным именам/namespace.

Реальный JDK25 repro: объявленный Student.java вызывает `p.AnswerTest.value()`; дополнительный flat AnswerTest.java содержит `package p; public class AnswerTest { public static int value(){return 42;} }`. Доверенный тест — обычный AnswerTest в default package. Получено unsafe-submission, 0.052 s. Переименование только доверенного теста в Harness.java и его класса в Harness, при неизменном студенте, даёт success, score 1, 10.956 s. Студенческий p.AnswerTest и доверенный AnswerTest — разные классы; API и обязательное поведение не менялись.

Все четыре upload repro и сценарий: `final-platform-review-probes.json`, `final-platform-review-probes.py`. Они используют вымышленные исходники; суммарное время 25.582 s.

## Прочитанные свойства и сохранённые границы

Public context теперь присоединяется к единственному подтверждённому leaf без нового points/max_points. При неоднозначном MethodSource контекст остаётся в native message, а не добавляет баллы; конкретная ошибочная fallback ветка описана выше. Поля private rawResult/output/Throwable и assertion message не копируются в student view; подробности LF и diff перенесены в native message для preformatted rendering. Privacy-значение already computed immutable expected/actual отделено от private messages/trace; расширение overload не вводит цензуру предметных чисел.

Прочитаны все 60 новых точных overloads: аргументы/delta передаются в State.begin, message/supplier не снапшотятся и делегируются исходному JUnit ровно один раз; запись returned/threw сохраняет исходное исключение. Дополнительных подтверждённых ошибок делегирования в этой дельте не найдено. Старое замечание о long[] + String message закрыто по точной реализации и writer regression.

junit_result теперь проверяет конечность сумм owner/points и итогового total до rawResult. Старое замечание о NaN/Infinity закрыто; отдельные class maximums не обходят проверку итоговой суммы. Weighted по-прежнему использует equal completed leaf denominator, threshold — объявленные raw points; новый feedback не меняет знаменатель.

Upload проверяет flat/ZIP дубликаты и path prefixes, symlink/special entries, допустимые relative paths, UTF-8, archive bounds до extraction. allowAdditional=false ограничивает extras declared sourceFiles, required primary проверяется в union. Собственные замечания относятся к caps и законным package filenames; необходимости ослабить пути ответа либо доверие к скомпилированным классам из них не следует.

## Interface и ограничения приёмки

`schemas/grading-descriptor.schema.json` пока закрыт и не принимает allowAdditional; `tools/grading_job.py:535–551` пока не переносит этот flag из author policy. Расширен только вложенный grading-job schema. Исполнитель явно передал producer→schema→prepare_job closure владельцу D; это согласованная незавершённая передача, не дополнительная правка рецензента. До её завершения runtime direct tests не доказывают end-to-end fixed allowlist admission. Legacy SHA/receipt fields по поручению вне объёма этой дельты.

Успешные baseline matrices не повторялись. Прочитаны targeted tests и итоговый affected-runtime-tests.log: 109 tests за 388.221 s, FAILED (errors=1, skipped=7). Единственная ошибка — static fixture setUpClass: javac вызван с module-path без json-simple. Текущий static test Source уже содержит json-simple в module-path; исполнитель отдельно сообщил актуальный static 2/2 PASS. Общий прогон не выдаётся за PASS; актуальный отдельный результат должен быть явно связан с исправленным setup. Предыдущие заявленные результаты writer (assertion 3 tests/29 leaves/87 checks, upload 7, leaf 6, XML 8, static 2) не выдаются за собственные новые прогоны. Собственные новые distinguishing probes: 4 upload jobs 25.582 s и decoder probes 0.0157 s.

Docker build и реальный UI находятся в отдельных работах; эта рецензия их не принимает. VM, production, DB, deploy и image publication не затрагивались. После устранения трёх замечаний нужна независимая проверка точной дельты и затронутых контрактов, без повторения неизменных matrices.

## Повторная рецензия исправлений upload и protocol 2

Прочитаны frozen исправления. **Оба upload P2 закрыты.** Независимые actual JDK25 closure probes:

- Три declared sourceFiles, включая AnswerTest.java в package p, доставлены только ZIP; maxFiles1/maxFileBytes8/maxTotalBytes8, allowAdditional=false. Получены success/score1; additionalSources.files пуст, declared primary не расширяют extra evidence. 5.302 s.
- p.AnswerTest как extra flat файл рядом с default-package trusted AnswerTest также success/score1. 8.499 s.

`final-platform-upload-closure.py` и `.json`, суммарно **13.801 s**. Сохранены checks полного path collision внутри ответа, symlink/ZIP safety и actual compiled FQCN/reserved namespace. Writer scoped run `affected-admission-final.log` завершился **37 tests / 87.497 s, OK** и включает unsafe same-FQCN. Его matrix не повторялась.

P1 исправлен для всех-unbound fallback и для отсутствующего selector при partial bindings, но **пока открыт для len(execution)==1 при другом unbound leaf**. Независимый decoder repro: два случая Answer#run, у второго удалить test, matching public observation actual UNBOUND_AMBIGUOUS. В текущем frozen коде первый случай ошибочно получает публичный passed/actual/attachment, хотя уникальность не доказана. Время **0.0085 s**, `final-platform-partial-match-probe.json`. Исполнитель подтвердил необходимость bindings_complete guard до принятия уникальной связи. До этой малой правки общий пакет не принят.

Перед ролью повторён весь прежний исходный admission interval: 6 turn_context, gpt-6.1-sol/high, Standard. Код не менялся. Владение Platform возвращено исполнителю после upload closure; следующий контроль P1 ограничен decoder/projection и legacy1/Fish, новых неизменных JDK jobs не требует.

## Итоговая приёмка frozen Source пакета

**APPROVE:** frozen dirty дельта Platform runtime/feedback/upload/assertions и двух обновлённых документов относительно `6616222206d3e0843ebc560af38c2dc3519557f0` принята независимым техническим рецензентом. Все три замечания этой рецензии закрыты. Точный итоговый tracked diff сохранён в `platform-approved-final.diff`; untracked targeted test файлы также прочитаны. Исполнитель может создать commit и пересобрать фактический локальный образ. Рецензент Source не менял, владение возвращено исполнителю.

Последний bindings_complete guard расположен до принятия len(execution)==1: неполный набор case→MethodSource не подтверждает ни уникальный match, ни отсутствие selector. Независимые decoder/projection closure probes:

| Сценарий | Подтверждённый результат |
| --- | --- |
| protocol2, все leaves без test binding | unknown, actual отсутствует, attachment отсутствует |
| protocol2, partial bindings и нет известного match | unknown, actual отсутствует, attachment отсутствует |
| protocol2, один известный match и другой unbound leaf | unknown, actual отсутствует, attachment отсутствует |
| legacy protocol1 без bindings с явным observation | прежний passed/actual сохранён |
| Fish/no-JUnit cases с явным observation | прежний passed/actual сохранён |

Публичный JSON всех трёх protocol2 вариантов не содержит UNBOUND_CANARY. Пять отличающих probes PASS за **0.0246 s**; `platform-final-binding-closure.py` и `.json`. Writer `binding-final-green.log` прочитан: **13 tests / 0.269 s, OK**, не повторялся. Уже закрытые P2 uploads и guards actual FQCN переиспользованы из предыдущей независимой проверки и scoped log, без новых неизменных JDK запусков. `git diff --check 6616222` — exit 0.

Полностью прочитаны актуальные `docs/student-feedback.md`, `docs/additional-java-sources.md`: они описывают фактическое объединение публичного контекста с leaf без дополнительных баллов, unknown/not-run, preformatted message, immutable assertion values vs private Throwable/messages, extras-only caps, exact sourceFiles union, отдельный hard 1 MiB primary и разрешённый different-package basename. Новых подтверждённых расхождений этих документов с reviewed runtime не найдено.

Действует ограничение приёмки: отдельный D producer→functional descriptor/schema→prepare_job closure (`allowAdditional`/selected v3), его интеграция, пересобранный Docker image и реальный UI не приняты этим Source review. Прежние незавершённые/ошибочные общие прогоны не превращаются в PASS этой записью. Подробные собственные и writer проверки выше сохраняют свои точные границы и wall costs.

Перед итоговой приёмкой вновь проверен **весь исходный admission interval**, без сдвига начала: **7 turn_context, PASS gpt-6.1-sol/high**, Standard; [feedback-review-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261105/reports/feedback-review-admission.json). Исходные chat/turn ID прежние.
