# Task10 M01 — report

**DONE / FROZEN**, механический scope готов к независимому review. Root-owner render попытка обязательна и выполнена; её результат **FAIL exit1**, не green acceptance.

Dispatch `M01-01` / `/root/m00`; requested gpt-6.1-sol / medium. Identity подтверждён dispatch journal, effective model/effort not_reported. Вход: HEAD `15a50ededeb2c1b30eb08d983e118f08785c0a59` + frozen U07 files/render map. Приняты U00/U06; педагогическая приёмка U07 pending. Authority: handoff `plans/execution-plan.md:58` разрешает независимые M01 mechanics после U00; созданные 11 exact U00 paths уже доступны. Это не waiver dependency gates и не утверждение U07 review.

Прочитаны task10 dispatch и brief, current user instructions; полностью загружены U00/U06/U07 packets и structure-contract, предметно рассмотрены роли/границы/fresh-ID policy, exact lecture mapping и lab IDs, preservation scope. Все входные документные hashes/bytes записаны в `config-verification.json`. Прочитаны полностью обе исходные YAML конфигурации; существующий root pre/post/native child process код рассмотрен перед запуском. Книги не использовались; M01 не автор педагогического текста.

## Изменения

Только `lectures/_quarto.yml project.render` и `tasks/_quarto.yml prairielearn.delivery.instances.pilot.works`. Полный минимальный двухфайловый diff: `/tmp/java-course-revision-checks/M01/logs/two-config.diff`.

Lecture list: index + platform-runtime, values-absence, control-methods-data, objects-invariants, interfaces-records, generics-variance, erasure-algorithms, equality-collections, functions-streams, exceptions-resources, io-application (`.qmd`). Порядок буквально совпадает с принятым U00 packet и замороженным U07 packet. Все 12 files существуют, все уникальны, старых numbered paths и doubles нет. Work list: sec-j25-lab-numbers/control/arrays; все три определены в принятых lab sources.

Семантическое YAML-сравнение before/after доказывает равенство всех остальных вложенных параметров. Дополнительно точный текстовый diff показывает сохранение комментариев/отступов/форматирования вне двух замен. Конкретный preserved inventory: `/tmp/java-course-revision-checks/M01/preserved-options.json`; исходники before/lectures/_quarto.yml и before/tasks/_quarto.yml. Сохранены:

- project hooks/resources/output-dir, audience/profile/filter behavior;
- revealjs slide/nav/theme/plugin parameters и вся bibliography/CSL/link-citations/nocite;
- полный tasks chapters/parts, `bank/projects.qmd`, все essays chapters;
- native course ID/adapters/topic/name/instance/window/enrolment/attempts/points/pass/assessment assignment mode, editor settings;
- project-checks runtime/release/compiler options/limits/reference/public verification/contract cases/scoring/discovery/source profiles.

Не менялись остальные configs/profiles, QMD/landing/pages, физические 17 projects, source/tests/fixtures/private roles, bibliography, vendor/extensions/CI/runtime/pins. Никаких aliases, old-ID preservation, migration либо placeholder decks. Read-only evidence ЛР1–ЛР3: 6/4/4 required positions =14, 7/5/5 candidates =17, random group по одной в каждой lab, сохранение назначения. Автоматизированный whole-bank/gradeapp claim не вводился.

## Команды и наблюдения

Evidence root: `/tmp/java-course-revision-checks/M01`.

1. `python3 .../M01/apply-and-verify.py` — exit0. Проверены exact input map, существование/уникальность lecture files, work definitions, только два изменённых YAML-поля, все остальные значения preserved. `config-verification.json`; source baseline hashes всех1390 файлов записаны до render после cfg-edit.
2. `quarto --version` — exit0, **1.11.5**; pinned `/home/tolya/course-tools/local-tools/cue/cue version` — exit0, **v0.17.1**. Raw `logs/quarto-version.log`, `logs/cue-version.log`.
3. `python3 .../M01/render-attempt.py` — wrapper exit0 означает сохранение actual command result; actual `quarto render . --profile student --fail-if-warnings` — **exit1**, elapsed **289.156s**. Полный exact cwd/process env/command/exit в `render-result.json`; полный combined stdout/stderr в `logs/root-student-render.log`. CUE/PATH, XDG_CACHE_HOME, CI_LOG_DIR, CI_CHECK_OUTPUT, TASK_CHECK_OUTPUT и COURSE_BUILD_TRACE назначены только процессу и внешним M01 paths. Глобальных изменений нет.
4. Existing root hook действительно вызвал owner child CLI: **133 inspect** (включая все12 новых lecture source paths) и **2 render**. Точный `logs/child-commands.jsonl` содержит cwd/args/exit/elapsed. `theory`: `quarto render . --profile student`, exit0,61719.418ms. `tasks`: та же child command, exit1,76626.186ms. Промежуточные inspect не подменяются заявлением о render. `lectures`/`practice`/`handbook` child render не достигнуты.
5. Фактический отказ: tasks прошла41 source documents и `course-core/entrypoints/post.ts` выбросил `NotFound` при `Deno.realPath('/tmp/java-course-pr13-20261013/tasks/projects/gradeapp')`, через owned/checkPaths/validateRelease. Это реальная ещё не материализованная M04 заготовка, не ошибка spelling двух config mappings. Не менял author conditions, project paths или vendor validation ради green.
6. Фактический theory QRC сообщает109 resolved,142 deferred,270 targets,26 pages. Root final cross-project resolution не достигнута. Не называю142 deferred broken/старым foreign spelling без такого результата. Остаточные foreign consumer repair — M03; полный final href/zero-warning/privacy/native PL17 acceptance — M05. Никакие final gates не waived.
7. Read-only lab invariants — PASS; `lab-contract-verification.json`/лог. Первая локальная read-only assertion проверяла прежнюю фразу «последующие отправки» и не прошла; источник U06 уже использует «сохраняется при последующих отправках». Проверку уточнил на фактическую принятую фразу, counts/random/persistence PASS, lab source не менял. Это не grading-test failure и не исправление условия.
8. `python3 .../M01/archive-and-verify.py` — exit0. Все1390 существовавших перед render файлов SHA-256-неизменны. Перед render generated dirs отсутствовали.12 созданных source-local artifact dirs перемещены в `/tmp/java-course-revision-checks/M01/generated-source-artifacts` с исходными путями; лишних source files не осталось. `artifact-and-render-verification.json` перечисляет moved paths/file counts и child evidence. Никакого git clean, чужих удалений или повторного рендера.

## Scope, freeze и downstream

Основной deliverable `docs/course-revision/packets/M01.md`; этот scratch report и внешняя evidence дополняют два config changes. Изменения U07 уже существовали при входе (девять deleted/11 untracked files и edited index); initial status saved, чужие изменения сохранены. Root routing journal/Git не изменялся исполнителем; commit отсутствует. Source-local artifacts были лишь следствием required штатного root-owner-hook и после проверки archived outside source.

M01 mechanically frozen; независимый review выполняет root. Если U07 review изменит frozen map, affected M01 config update/check обязателен до принятия. Отсутствующая gradeapp поставка передана M04; foreign consumers M03. Выполненная промежуточная попытка не заменяет final successful student→full→student, реальную child lectures сборку, свежие href/fragment/privacy/runtime/PL проверки и педагогическую U08 приёмку. Записей после freeze без нового root окна не будет.
