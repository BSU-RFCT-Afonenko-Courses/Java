# Обычный JUnit: source-предложение до кода

Исполнитель `/root/platform_single_compilation`, Source `/home/tolya/JavaCodex/java-release-20261110/platform`, branch `codex/java-release-20261110`, исходный принятый HEAD `503e0ccb61ab5460fe0433fa0d13d623e1e6ffef`. Единственный writer Platform. Java/Circle/число случаев/баллы не меняются. Source пока чистый; новых tests/experiments/CI не запускалось.

## Первичный допуск при возобновлении

Собственный чат `01a1a1fb-6d1d-7760-9a87-e2f25f95f8ef`, путь `/root/platform_single_compilation`, родитель `01a1a142-7adc-7371-9765-4871d807dfcd`. Назначение `gpt-6.1-sol` / `high`, Standard. Первичный источник `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-16-11-01a1a1fb-6d1d-7760-9a87-e2f25f95f8ef.jsonl`; исходный turn всей роли `01a1a1fb-6d49-7130-b885-1043eed45e08`, текущий `01a1a20a-eac7-7143-a6c1-9ccf9e33f470`. Проверены все3 `turn_context` от исходной точки: верхние model/effort и вложенные `collaboration_mode.settings` согласованы с назначением. Допуск пройден; поля сохранены в `platform-ordinary-junit-admission.json`. Нормативный `docs/model-admission.md` и глобальные настройки не менялись; скрытая маршрутизация этим не подтверждается.

## Что прочитано и точная причина

Прочитаны brief, сохранённый R1/report.md (без повторения probes), `Transform`, `Assertions`, `State`, `Gate`, `Scope`, `Timeouts`, `PlatformJUnitAdapter`, relevant grader callers, `junit_result.py`, native feedback caller и адресные ABI/numeric/course/complete-JUnit/security tests. Состав facade — механизм optional typed observation, а текущий `Transform` ошибочно делает его обязательным ABI для всех обычных JUnit assertions:

- `Transform.java:245–251`: любой `invokestatic org/junit/jupiter/api/Assertions` без exact descriptor в facade вызывает `Unsupported exact descriptor` до JUnit. Это не trust-граница: javac уже разрешил корректный вызов к закреплённому JUnit, но observer не знает этот overload.
- `State.begin`: observation допускается только от sealed trusted class/site в живом leaf scope; stack до Scope/привилегированного timeout grant не должен содержать student intermediary. `runtime(c)` уже включает pinned JUnit module, поэтому обычный `assertAll` сам не требует ослаблять stack admission.
- `State.bind`/`Gate`: lifecycle привязан к actual adapter class/control BCI, qualified projection остаётся bound к fresh trusted output и bridge guard. `Timeouts` сохраняет специальный grant для JUnit worker; упрощать его не требуется.
- `PlatformJUnitAdapter.executionFinished`: case identity/outcome и `Gate.finish` checks не зависят от числа assertions; стандартный `assertAll` остаётся одним actual JUnit leaf. parameterized invocation даёт отдельный real leaf.

## Один минимальный путь для решения координатора

1. `Transform` заменяет на существующий facade **только уже поддержанный exact descriptor**. Остальные корректные статические JUnit assertions оставляет исходными. Никаких сотен overload, генератора facade или generic invocation bridge. Неизвестный observer descriptor перестаёт означать недопустимый JUnit; actual linked JUnit/version и javac остаются прежними.
2. Existing typed projection сохраняется: supported assertions по-прежнему дают argument snapshots/дельту/string diff/qualified records и защищённые callback/timeout observations. Unsupported обычный assertion исполняется закреплённым JUnit без обязательного observation. Успешный lazy message не вычисляется; argument expressions/callbacks не исполняются второй раз. Ordinary `assertAll(String, Executable[])` остаётся настоящим JUnit aggregate, а вложенные supported assertions по-прежнему проходят existing trusted Scope; native scoring считает его одним leaf.
3. Failure details брать из **уже существующего настоящего JUnit результата**, без нового protocol: `upstream/JUnitAutograder.executionFinished` сохраняет `Throwable.toString()` в `AutograderTest.message`; `saveResults` записывает message каждого leaf; `grade.py` после signature/completeness checks сохраняет это как private `rawResult`. Для настоящего `MultipleFailuresError` там сохраняется агрегат причин; для `AssertionFailedError` — expected/actual и авторское failure message. Поддержанные tolerance assertions дополнительно сохраняют delta в existing typed checks. Это private диагностический материал, а не authority для новых public subject values.
4. Student feedback продолжает публиковать только existing trusted TestReporter/typed projection. Не копировать raw throwable.message/stack/stdout из `rawResult` в public checks. Никаких новых student-origin полномочий. Можно адресно дополнить native отображение existing typed `delta`, которое сейчас decoder сохраняет, а description теряет; это небольшой direct caller fix, без facade/протокола.
5. Отсутствие checks на успешно выполненном ordinary leaf не снижает real JUnit/event completeness и не влияет на score. Если меняется `feedbackCompleteness`, отсутствие optional observation у successful leaf больше не должно само делать feedback incomplete; failed leaf с отсутствующим разрешённым subject observation остаётся честно неполным по деталям. Event completeness/covered cases/score denominator остаются обязательными и прежними.

Ожидаемая область diff после решения: `Transform.java`, адресный native rendering caller при принятии пункта4/5, targeted tests и существующие bundle/shared source pins. `State/Gate/Scope/Timeouts` менять не требуется. Adapter/runner только если существующий rawResult фактически не сохраняет нужную ограниченную detail; нового диагностического поля заранее не вводить.

## Явная граница failure details и оставшееся противоречие

Нельзя автоматически считать `AssertionFailedError.getExpected/getActual/getMessage` доверенными public observations только потому, что exception пришёл в JUnit listener или имеет похожий stack. Конкретный existing regression `CompleteJUnitResult.test_mutable_throwable_stack_cannot_authorize_subject_values` бросает **настоящий** `org.opentest4j.AssertionFailedError` из student method с forged expected, private classpath actual и переписанным/пустым stack; public checks обязаны остаться пустыми. Один лишь `getClass()==AssertionFailedError.class`, method source или Scope catch эту подделку не исключает. Private actual JUnit failure diagnostic можно сохранить, но выдавать его как доверенный subject result нельзя.

Таким образом минимальный путь снимает запрет обычного JUnit, сохраняет все actual failure messages privately и existing typed public projection. **Он не обещает безопасно восстановить typed public expected/actual/message для любого неподдержанного overload из произвольного Throwable.** Для public expected unsupported double/String либо нужен existing deliberate trusted reporter, либо отдельное точное решение о disclosure/authority. Сейчас новый DSL/observation YAML/универсальный validator не предлагается. Скрытые expected и произвольные сообщения не начинают автоматически публиковаться.

Предлагаю принять именно этот независимый безопасный фрагмент как обычную JUnit compatibility; дальнейшую public extraction не подменять ослаблением trust boundary. Координатору нужно подтвердить: достаточна ли сохранность настоящих failure details в existing private rawResult при прежней public границе, а также входят ли direct delta rendering и successful optional observation completeness в текущий ограниченный diff. До его решения product Source не правлю.

## Намеченные адресные проверки после решения

- Один actual JDK25 source-grader fixture: ordinary `double/double/String`, `double/delta/message`, `assertTrue(message)`, ordinary `assertAll` с двумя failed children, parameterized rows и успешный lazy supplier. Проверить настоящие leaf identities/counts/score и private multiple causes/messages; public hidden expected/messages не появляются автоматически.
- Existing typed projection/timeout late worker/student reentry cases и mutable/forged Throwable regression. Один targeted API gate control; отсутствие double production compilation/installation checks не трогать.
- Existing ABI test, который сейчас ожидает отказ unknown-but-standard descriptor, заменить positive ordinary delegate control. Source pins, py_compile затронутых Python и diff-check.
- Не запускать текущий CI второй раз,667 tests, image build, whole-course, R1 probes/benchmark/fullhash/matrix. Готовый JDK25/runtime переиспользовать. Каждый extra case должен иметь названный конкретный дефект и конечный timeout.

## Принятое внутреннее решение

Координатор принял безопасный фрагмент1–5: supported descriptors сохраняют typed path; остальные ordinary JUnit остаются настоящему JUnit; State/Gate/Scope/Timeouts не ослабляются. Failure details остаются в existing private `rawResult`; forged Throwable/stack/messages не дают полномочий публиковать данные. Приняты direct delta rendering и отсутствие ложной неполноты successful leaf только из-за optional checks. Статус public case `not-run` с existing signed leaf отдельно не меняется. Это решение снимает ABI несовместимость; оно **не является финальным принятием всего public assertion feedback**.

## Реализовано

Обозримый diff в шести файлах: `runtime/java25-junit-v1/feedback/Transform.java`, `runtime/student_feedback.py`, `runtime-profiles.json`, три targeted test файла (`test_assertion_feedback.py`, `test_assertion_feedback_consumer.py`, `test_assertion_feedback_overloads_static.py`).

Product logic ограничена условием optional Transform и двумя direct native caller правками:

- Transform проверяет supported descriptor только как условие перенаправления на existing facade. Остальные стандартные JUnit instructions остаются исходными. Новых facade overload, generated methods, registry, protocol, YAML и DSL нет.
- Existing `delta` выводится строкой `Допуск: ...` в native description без изменения checks/formats/баллов.
- Successful real leaf с пустыми optional checks больше не делает `feedbackCompleteness=incomplete`. Explicit pending/incomplete observation по-прежнему считается неполным, failed leaf без details тоже не объявляется полным. Обязательная JUnit event/case completeness не менялась.
- Подтверждено существующее хранение обеих actual assertAll причин в message одного private raw leaf и exact double expected/actual/author message в private ordinary raw failure. Новых public exception details не добавлено.
- Shared `student_feedback.py` pin обновлён для обоих Java profiles и fish, поскольку это общий runtime consumer: `36774ab9013744651f52f37128d3900869ab5caf0f8a46cc4e213812c5953538`. Transform pin в обоих Java bundle: `4c4f712165edb2b1aa355afead72ef61dda2d9c2b313fa15cbb93736251338c7`. Existing preparation вычисляет native derived profile из этих Source pins. Runner/toolchain/image/server/course pins не менялись.
- State/Gate/Scope/Timeouts, adapter, grading execution/compiler/API code, hidden/public cases и баллы не изменены.

## Адресные проверки и ошибки

До product правки запущены три адресных regression: два native caller case упали на прежнем `feedbackCompleteness=incomplete` и отсутствии строки `Допуск`; actual source-grader fixture остановился в Transform с известным unsupported `(DDLjava/lang/String;)V`. Это ровно проверка будущего product regression, не повтор R1 исследования/benchmark. После изменения все три прошли —3tests,4.263s.

Один новый actual JDK25 source-grader fixture использует готовый runtime и6 реальных leaves: две parameterized rows, exact double/String failure, один assertAll с двумя failed children, tolerance failure и successful lazy/boolean-array leaf без обязательных observations. Получены executed6/passed3/failed3/score0.5. У assertAll ровно один leaf и два existing typed checks; оба actual сообщения сохраняются в message того же raw leaf. У ordinary unsupported failure private message сохраняет expected187.25/actual42.0 и author message; публично187.25/PRIVATE messages/private paths отсутствуют. Lazy supplier бросил бы при вызове, но leaf passed без checks. Delta0.01 присутствует в native description. После уточнения assertions о привязке private messages к конкретному raw leaf выполнен только этот fixture повторно.

Отдельный ограниченный набор:21tests, OK,47.000s. Состав: ConsumerGuards (9), existing static ExactOverloads (2: supported ABI behavior и теперь positive boolean-array ordinary behavior вместо запрета), actual typed arguments/reporting, callbacks/lazy/opaque delegate, student helper reentry, late preemptive worker, failed tolerance/one lazy message, forged/mutable Throwable, inner/secondary student AssertionError classification, API mismatch/no-dependent execution, trusted compile defect после API и existing baked profile/pin admission. Case-level loops в existing regressions сохранены; fullsuite не запускалась.

`git diff --check`, `py_compile` четырёх изменённых Python файлов, exact changed-source pin readback — OK. Existing preparation test подтвердил derived profiles/source closure. Команды с реальным grader выполнялись вне известного sandbox UID mapping, как в предыдущем пакете: production parent identity guard не ослаблялся.

Текущий CI503 не запускался и не дублировался. Нового image build/OCI admission, whole-course,667tests, R1 experiments, benchmarks/fullhash/matrix нет. Статический ABI test — существующий targeted product regression с конечным45s subprocess timeout, не новый probe исследовательской матрицы. Production fixtures используют прежние compile/run limits.

## Честный оставшийся пробел

Совместимость ordinary JUnit исправлена, но безопасная typed public extraction для failure неподдержанного overload остаётся нерешённой. При таком failed leaf private rawResult содержит настоящие details, а public карточка может оставаться без expected/actual/message. Ни Throwable class, ни его mutable stack, ни Scope catch не авторизуют student data. Нового DSL/универсального facade или автоматической публикации hidden expected не вводилось. Понятный public assertion feedback целиком этим commit **не закрыт**; также отдельно остаётся public-case status/binding тема.

Изменение проверено на настоящем host JDK25 Source runtime; установленный сервер/OCI image и actual kernel containment этим пакетом не принимались. Push после независимого review выполняет root.

Уточнённый actual ordinary fixture повторно прошёл:1test,3.913s. Перед передачей полный собственный интервал от исходного turn снова подтверждён:4contexts, верхние/вложенные `gpt-6.1-sol`/`high` согласованы. Commit `a344ac90989374463dac0bda6d7efc1ba6781ea3` (`codex/java-release-20261110`), working tree чистый. Patch сохранён в `platform-ordinary-junit.patch`. Push/PR не выполнялись.
