# Public case status: короткое source-решение до кода

Исполнитель `/root/platform_single_compilation`, Source `/home/tolya/JavaCodex/java-release-20261110/platform`, branch `codex/java-release-20261110`, исходный HEAD `a344ac90989374463dac0bda6d7efc1ba6781ea3`, working tree чистый. Прочитаны brief, существующие Circle source integration report/results summary, текущие adapter/decoder/student_feedback и соответствующие tests. Четыре Circle сценария, R1, CI/image/fullsuite не повторялись.

## Собственный допуск

Та же роль и исходная точка: чат `01a1a1fb-6d1d-7760-9a87-e2f25f95f8ef`, путь `/root/platform_single_compilation`, исходный turn `01a1a1fb-6d49-7130-b885-1043eed45e08`, назначение `gpt-6.1-sol/high`, Standard. Полный `session_meta`/`turn_context` интервал от исходного turn повторно проверяется и сохраняется рядом в [platform-public-case-status-admission.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/platform-public-case-status-admission.json); нормативный файл и глобальные настройки не меняются.

## Конкретный путь

Причина точная: `student_feedback.py` меняет `publicCases.status` с `not-run` только при одном matching `publicCaseObservations` с actual string. Actual observation optional; signed `counts.cases` уже содержит id/status, но не содержит selector configured public case (`Class#method`). Adapter уже использует настоящий JUnit `MethodSource` для runtime reporter и public reporting entries, поэтому парсинг DisplayName/unique-id не нужен.

Предлагаю один маленький путь:

1. `PlatformJUnitAdapter.caseReport` добавляет optional `test: method.getClassName()+"#"+method.getMethodName()` в существующий private case DTO, когда `TestIdentifier.getSource()` — настоящий `MethodSource`. Значение формирует trusted listener из actual JUnit identifier, а не reporter/student code. Signed counts/leaf identity/status остаются прежними; нового protocol/registry нет.
2. `junit_result.validate_counts` и существующий `$defs.caseResult` допускают этот optional bounded string. Старые v1/v2 cases без поля продолжают приниматься. Полная actual registered/started/outcomes/cases consistency по-прежнему обязательна.
3. В `student_feedback` выполнить существующий shared JUnit decoder **до** использования cases для public status. Для non-infrastructure результата искать ровно один validated case с `case.test == configured.test`, у которого signed `outcomes[id]` ровно `successful` или `failed`. Тогда public `status` берётся из этого outcome даже при `actual=None`. Никакие actual/expected не выводятся из статуса.
4. Existing reporter значения остаются прежними: один matching authorized observation сохраняет actual string. Для старых DTO без selector сохраняется прежний observation/status путь. Новый actual leaf status является авторитетным при наличии unique match; отсутствие/дубликат selector, skipped/aborted либо incomplete/unvalidated result не подтверждают запуск public case и не превращаются в passed. Failed leaf без subject observation показывает `status=failed, actual=null`.
5. Private optional selector не публикуется через feedback.cases/native tests; используется только для связывания configured publicCases. Scoring denominator, completeness и student/tool classification не меняются. Unsupported rich public assertion details остаются отдельным пробелом.

Область diff: adapter, shared decoder, student_feedback, существующий check-result case DTO schema, затронутые runtime source pins и адресные tests. State/Gate/Scope/Timeouts/Transform/compiler/Java/Circle не меняются.

## Конечные проверки после решения

Один настоящий source-runtime JDK25 fixture до30s: ordinary passed и assertion-failed public methods без TestReporter, метод с actual reporter и configured missing selector; проверить exact statuses/actual:null, сохранённое actual, тот же actual executed denominator и отсутствие private canaries. Небольшие Python cases: old v1/observation compatibility, отсутствующий/двойной match, aborted/skipped и incomplete event report, новый optional selector decoder/schema. Existing forged-Throwable и late-callback guards уже passed в a344 и не затронуты; повторять их без конкретной причины не планируется. Подтверждение via real MethodSource в новом fixture проверяет именно изменённую trust границу.

До внутреннего решения координатора product Source не изменён.

## Принятое решение и реализация

Root принял optional `test` из actual MethodSource в существующем signed case DTO. Статус берётся из единственного подтверждённого завершённого leaf; actual остаётся nullable. Selector наружу не выводится; старые DTO/reporter fallback сохраняются, но incomplete/infra/missing/duplicate/skipped/aborted результаты не получают passed. Это не новый источник subject values и не разрешение публиковать Throwable.

Изменены шесть файлов: `runtime/java25-junit-v1/PlatformJUnitAdapter.java`, `runtime/junit_result.py`, `runtime/student_feedback.py`, `schemas/check-result.schema.json`, `runtime-profiles.json`, `tests/test_student_feedback.py`.

- В existing `caseReport()` adapter добавлены две строки получения optional selector из настоящего `MethodSource`. DisplayName/uniqueId не парсятся; actual listener identity, подпись counts и scoring остаются прежними.
- Existing decoder и `$defs.caseResult` допускают непустой optional string до8192 chars. Номер feedback protocol не меняется; v1/v2 без selector допустимы. Case/event completeness продолжает проверяться прежним decoder.
- Существующий decoder call перенесён перед publicCases projection. Unique matching signed selector и outcome `successful`/`failed` дают passed/failed независимо от optional subject observation. При actual отсутствии сохраняется null; новый expected не вычисляется. Если selector встречается более одного раза, matching leaf отсутствует или outcome aborted/skipped, fallback не объявляет этот case исполненным.
- Matching reporter actual сохраняется при согласованном leaf status. Для прежних DTO без selector работает существующий reporter fallback; если counts присутствуют, они должны подтверждать завершённый outcome соответствующего вида. Это также блокирует фактически skipped case с ошибочно заявленной passed observation. Infrastructure не получает confirmed public status.
- Selector не входит ни в publicCases, ни в feedback.cases/native tests; поля copies и scoring denominator не изменены. State/Gate/Scope/Timeouts/Transform, compiler/build/API и Java/Circle не менялись.
- Source pins: adapter `b5349d30c28248bffdfbd0e08e9735204b450362f02721d3c7641820b5a30e2f`; shared decoder `ffbcbf3282beed963a2b47d256c29dac46c8d44110d7f176267ca677019b1ebe`; shared feedback `54beda2d884e1b00c274bbca155de5bc910dcc29ecbe85c764ac0ff04c7872fe`. Общий feedback pin обновлён и у fish. Runner/toolchain/image/course pins не меняются; existing native derived profile вычисляется preparation как прежде.

## Проверки и стоимость

До product правки запущены два адресных regression: новый DTO был отвергнут старым decoder (`Invalid JUnit case feedback`), а один actual source-runtime fixture подтвердил ложные `not-run` вместо passed/failed при отсутствии reporter. Итог red запуска:2tests,3.236s; actual fixture также сохранял прежнее reporter actual. Это проверка будущего fix, не повтор четырёх Circle/R1 сценариев.

После правки:5 адресных tests, OK,3.365s. Из них **один controlled actual JDK25 fixture** с subprocess timeout30s: три real leaves — passed без reporter, failed без reporter, passed с reporter; configured missing selector не соответствует ни одному leaf. Фактические outcomes: `passed/null`, `failed/null`, `passed/"42"`, `not-run/null`. Actual counts executed3/passed2, native score2/3. DisplayName первого метода специально не содержит selector; связь получена из actual MethodSource. Private Throwable canary не опубликован, поле selector наружу не попало. Это host Source/runtime route, не OCI/official containment admission.

Дополнительный короткий negative case выявил собственный край первоначального fallback: fully skipped events с пустым cases могли пропустить ошибочную passed observation по старой ветке. Он сначала упал с `passed/CLOSED_UNCONFIRMED` вместо `not-run/null`, затем исправлен требованием завершённого signed outcome при наличии counts. Повторять actual fixture не требовалось: правка касается только legacy/no-selector fallback; новый MethodSource путь остался прежним.

Финальные небольшие Python checks:24tests, OK,0.203s. Это PublicCaseExecution (passed/failed nullable actual; missing/duplicate/aborted/skipped/infra; incomplete; selector type/length; new/legacy v1 actual reporter), StudentFeedback defaults/privacy/explicit reporter/unobserved cases, ConsumerGuards decoder/completeness/protocol, CompleteReportBoundary, ReceiptResultAdmission (обязательные terminal events и достоверная доля), SharedRuntimeCompatibility fish и existing baked profile/pin admission. Добавленный явный positive schema+decoder acceptance для valid selector отдельно прошёл:1test,0.098s. Existing forged-Throwable/late-callback guards переиспользованы из принятого a344; без изменения их callsites здесь повторно не запускались.

`git diff --check`, `py_compile` трёх изменённых Python файлов и exact source pin readback — OK. Actual grader/pin test выполнялись вне известного sandbox UID mapping, без ослабления production parent identity checks.

## Границы

Circle4/R1/667tests/CI/image не запускались. Успешный CI503 не повторялся ради статуса. Не изменены учебные cases, баллы, знаменатель и принятый ordinary JUnit fragment. Неоднозначный method selector (например, несколько parameterized leaves одного метода) не получает invented status; нужен unique actual match. Старые DTO без metadata сохраняют прежнюю reporter совместимость, а не угадывание selector.

Оставшийся rich public assertion details для unsupported overload остаётся отдельной задачей: этот status fix не публикует private rawResult/Throwable/скрытые expected и не выдаётся за финальное принятие всего feedback. Push a344 и нового commit выполняет root после независимого review.

Перед передачей весь собственный интервал от прежнего исходного turn снова проверен:6contexts, верхние/вложенные `gpt-6.1-sol/high` согласованы. Commit `b37febb6e46da681b16b27eadb676e29b764130b` (`codex/java-release-20261110`), working tree чистый; patch `platform-public-case-status.patch`. Push/PR не выполнялись.
