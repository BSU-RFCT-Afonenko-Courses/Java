# Сохранение native UUID контрольной работы

## Допуск и граница

Исполнитель: Sol6.1/high, Standard (назначение координатора); локальный допуск — узкий optional native UUID и адресные проверки. Нормативный `model-admission.md` не меняется. Репозиторий PL: `codex/java-release-20261110`, исходный HEAD `7a41b7cc73859f69bf45c304590d711d85bcae66`, base `1c41f03f83b26adfe58415b4d89958a3663fabf2`. Единственный writer PL. Java QMD меняет координатор.

## Цепочка до реализации

- `_extensions/course-prairielearn/application/native-business.ts`: строгая `AssessmentPolicySchema` после `mergePolicy(defaults, nativePolicy(raw.assessment))` в `decodeNativeBusiness`; `AssessmentDefaultsSchema` остаётся без UUID.
- `_extensions/course-prairielearn/application/native-course.ts:301–306`: уже передаёт `business.assessment` как `extensions.prairielearn`, Core schema менять не требуется.
- `_extensions/course-prairielearn/application/grading-policy.ts`: повторная closed policy проверка native/standalone; сейчас отвергает неизвестный `uuid`. Здесь требуется допуск и проверка формы UUID.
- `_extensions/course-prairielearn/application/declarations.ts`: standalone config допускает assessment-defaults, но сейчас не проверяет их поля; здесь требуется адресно запретить `assessment-defaults.uuid`, чтобы не тиражировать одну identity.
- `_extensions/course-prairielearn/application/export-course.ts`: после выбранного scope и policy validation вычислить effective UUID каждой работы, отвергнуть совпадения (включая explicit против fallback и разный регистр), затем писать его в `infoAssessment.json`. Без поля остаётся прежний `identity('assessment/'+work.id, courseUuid)`.
- `docs/authoring.md`: короткий русский контракт optional локального UUID, fallback, запрет defaults и конфликтов.

Причина регрессии: `export-course.ts` безусловно выводит UUID из work.id; локальная policy не может передать сохранённую identity через strict contracts.

## Проверки (план до реализации)

Использовать существующий Deno harness `tests/export-course.test.ts` для реальной атомарной поставки и `tests/grading-policy.test.ts` для native business boundary. Нужны явная identity при переименовании, literal прежнего fallback, неверные UUID, duplicate explicit и explicit/fallback collision, запрет UUID в defaults на native/standalone пути. Не запускать полную CI/course/render/Docker матрицу.

Итог, команды и commit будут дописаны после проверки.

## Реализовано

`assessment.prairielearn.uuid` — необязательная строка native UUID. Одно выражение формы UUID используется strict TS business schema и standalone grading policy. Значение не нормализуется и попадает в `infoAssessment.json` буквально. Без поля прежний UUIDv5 остаётся прежним. До Body/staging проверяются effective UUID всех выбранных работ; совпадение explicit/explicit или explicit/fallback без учёта регистра даёт `PL.ASSESSMENT_INVALID` с двумя work IDs. В defaults поле не добавлено: native strict schema и standalone config отвергают его.

Core, course/instance/question UUID, work.id, native пути, allocation/scoring/completion не менялись. Новый migration framework, course-specific map и обязательный published key не вводились. `docs/authoring.md` и `docs/diagnostics.md` обновлены по-русски.

## Проверено

1. RED до production изменений: `deno test --no-config --allow-read --allow-write --allow-run --allow-env tests/grading-policy.test.ts tests/export-course.test.ts` — 17 passed, 2 failed. Ожидаемые причины: `AssessmentPolicySchema` отвергает `uuid`, standalone `closed` отвергает `unknown uuid`.
2. Первый GREEN той же команды — 19 passed, 0 failed, 34s. Это был избыточно широкий существующий export-course harness. Он не повторялся; новые UUID cases затем вынесены в отдельный Deno.test, прежний большой case в итоговом diff не изменён.
3. Итоговая адресная команда: `deno test --no-config --allow-read --allow-write --allow-run --allow-env --filter UUID tests/grading-policy.test.ts tests/export-course.test.ts` — 4 passed, 0 failed, 16 filtered out, 3s. Повторена после финального форматирования production строк, также 4/4. Новый отдельный export case делает ровно 11 вызовов `exportCourse`: 3 успешных (исходный ID с explicit UUID, новый ID с тем же UUID, отсутствие поля с прежним literal fallback) и 8 ранних отказов (null/false/число/пустая/не-UUID строка, explicit/explicit, explicit/fallback, UUID в defaults). Каждый отказ подтверждает отсутствие Body call и обоих результатов поставки. Native business tests проверяют передачу UUID при обоих ID, malformed UUID и defaults.
4. `deno test --no-config --allow-read --allow-write --allow-run --allow-env tests/owner-contract.test.ts tests/manual-mixed.test.ts tests/completion.test.ts` — 13 passed, 0 failed, 840ms. Это проверки соседних используемых контрактов без CI/course/render/Docker матрицы.
5. Дополнительный адресный malformed case со строкой UUID и завершающим newline: `deno test --no-config --allow-read --allow-write --allow-run --allow-env --filter 'Native UUID' tests/grading-policy.test.ts` — 1 passed, 0 failed, 6 filtered out, 17ms.
6. `git diff --check` — exit 0. Deno при выполнении тестов проверил TS types транзитивных business/export модулей.

Ожидаемый fallback `demo/sec-lab` независимо проверен стандартным Python `uuid.uuid5`: course UUID `bc5b90d9-1bd7-5ea9-9872-b6ef0aa7d68c`, assessment UUID `6cbeb308-b0c5-51fc-bc0e-0f6bcbe75a45`. В assertion сохранён литерал, production helper не вычисляет ожидаемое значение.

## Ограничения и передача

Полная suite/CI, настоящий Core course export, Java QMD, Docker, публикация, push/PR и серверная БД не запускались и не менялись. Известных падений в выполненных командах после fix нет. Сохранение рассчитанной source-base identity не доказывает соответствие установленному курсу или сохранность конкретной серверной БД; это финальная интеграционная граница. Независимый review и push/PR выполняет координатор. Субагенты не создавались.

Commit PL: `5a04576` — «Сохранить явный native UUID при переименовании assessment». Рабочее дерево PL после commit чистое. Отчёт хранится рядом с checkout в release package, в PL commit не входит.
