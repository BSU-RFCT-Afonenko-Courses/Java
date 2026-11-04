# Независимая рецензия PL materialization → exporter → writer

3 ноября 2026. Рецензент `/root/review_pl_materialization_types`.

## Вердикт

**APPROVE для ограниченного delta `da2ad6b6c00891590e59e5abccf3d1eb4ff552f7..09064082b48b1bd60b7d3910098db30138d9fed6`. Существенных новых findings нет.** Пять файлов, `124 insertions(+), 29 deletions(-)`: три production-файла и два compiler files. Вердикт не означает строгую типизацию всего exporter/native-course или production readiness.

Прочитаны exact commit diff и связанные сохранённые producer/consumer реализации, актуальный PL `AGENTS.md`, авторский `pl-typed-course-export.md`, ранее прочитанные `spec/index.md` и локальное языковое правило Core. Source/state/ветки/settings не менялись. Compile/render/tests/hash/network/install и прежние controls не запускались. Сообщённые автором exit0 и diagnostic PASS не воспроизводились рецензентом.

Ниже пути production относятся к `_extensions/course-prairielearn/application/` на `0906408`.

## Реальная цепочка типов

`native-course.ts:322–329,464–491` действительно использует return type непосредственно импортированной функции `materializeNativeQuestion`. Вызов этой функции статический: неизвестные upstream факты/adapter/Core на входах не превращают return статически объявленной функции в `any`. `projects`, `nativeInputs`, `artifacts` сохраняют соответственно project projection, selection и полный materialization result. Это устраняет прежнее стирание типов именно в трёх локальных контейнерах, а не только переименовывает прежний `any`.

Producer `native-materialize.ts:148–260` по-прежнему возвращает конкретные `files`, `archive`, `student` и условную checked projection. Core `artifacts/project.ts:353–373` на exact Source `d7cc10eaa21fa35bbe32d73cc4afa216002393da` сохраняет `Promise<ArtifactInventory>`. Новый delta не меняет этот producer и не добавляет casts его результатов.

`export-course.ts:121–133,153` выводит `NativeCompilerInput` из того же selection: descriptor сохраняется целиком, sources/tests получают только потребляемые `name` и `bytes`. Сужение до readonly file projection обосновано consumer: exporter читает эти записи, перекладывает source bytes в локальный `{name, data}` на строках489–492/633–636, а test bytes — в `{submissionRelativePath, bytes}` на строках529–532. Названия payload полей не перепутаны; преобразования остались прежними.

`export.ts:32–44` именует существующий inline контракт `StudentArtifact`, не меняя его форму. В exporter этот тип сохраняется в callback и local map (`export-course.ts:159–161,314,424–429`); callback native composition возвращает настоящий materialization result (`native-course.ts:558`). В standalone ветви та же типизированная `artifacts` map передаётся непосредственно writer (`native-course.ts:641–650`). Writer читает реальные `.files[*].bytes` и `.archive`, затем перекладывает их в свой `.data` record (`export.ts:843–879`). Общий `Uint8Array` consumer допускает более узкий inferred producer buffer type; равенство в обе стороны здесь не требуется.

## Non-null assertions и корреляции

Все три новые `!` проверены по текущему producer/control flow, а не приняты по объяснению автора:

- `native-course.ts:491`, `materialized.selection!`: `projectNativeChecks` возвращает объект с обоими полями `project` и `selection` (`native-check-projection.ts:211–230`). `materializeNativeQuestion` помещает этот же `checked` object spread в обе свои return ветви (`native-materialize.ts:245–250,260`). Ни одна успешная ветвь producer не возвращает project отдельно от selection. Поэтому условие `if (materialized.project)` действительно достаточно для selection на текущей реализации.
- `native-course.ts:628`, `projects.find(...)!`: projects.push и nativeInputs assignment выполняются одной веткой на строках489–491. Fact выбран по `exerciseId === q.id` и source на строках430–439; этот fact передаётся producer, который сохраняет exerciseId в project. В последующем `if (selected)` lookup выполняется для того же `q.id`; между заполнением контейнеров и lookup нет удаления/переименования project или nativeInputs. Повторные materializations одного ID пропускаются через artifacts на строке429.
- Там же `.check!`: checked projection создаётся лишь в truthy `input.fact.check` ветви producer, которая перед return вызывает прежнюю `NativeProjectCheckSchema.parse`. Project сохраняет этот fact.check; ветвь без check не создаёт selected/nativeInputs. На текущем flow выбранная запись действительно имеет check.

Это реальные инварианты текущего исполнения, хотя они не закодированы как discriminated union/dependent map в публичном типе. Новые annotations не создают runtime guards и не дают общей гарантии для произвольно составленного Materialized DTO. Fixture тоже не доказывает корреляцию; её здесь подтверждает чтение тела producer и мест заполнения maps. Runtime-поведение прежнее: `!` стираются компилятором.

## Проверки и предел покрытия

`tests/native-course-export-types.ts:13–60` делает реальные producer→consumer assignments без casts, сопоставляет callback/writer contract и archive/native bytes/descriptor. `Exact` отклоняет `any` на обеих сравниваемых сторонах; отдельный `_FileBytes` проверяет bytes materialized files. Односторонняя совместимость `_Files` уместна для общего `StudentArtifact`, включая различие generic buffer типов. Это проверяет полезные контракты, а не утверждение ложного обратного равенства всех producer/consumer структур.

`tests/native-core-typecheck.ts:18–39,40–52,128–138` включает affected production graph и новую fixture. Единственный новый negative вызывает реальный `exportCourse` с native source `{name, data}` вместо `{name, bytes}`; ожидается ненулевой compiler exit и `TS2353`. Если `nativeInputs` снова станет `any`, этот specific excess-property отказ исчезнет и negative завершится ошибкой runner. Режим `--course-export` не запускает старые восемь negative controls, default режим их сохраняет. Это соответствует заявленной узкой проверке, без fake runtime execution fixtures. По авторскому отчёту combined positive/new negative и fmt/diff checks зелёные; собственных повторов нет.

Новые DTO/fixtures **не доказывают отсутствие каждого `any` внутри exporter**. В частности, `checks:any` и legacy `fact.trustedTests` по-прежнему делают общий test-loop element на `export-course.ts:528–542` неточным; legacy source names и staged publication также остаются со старым долгом. Типизированная native `.map` ветвь и новый public NativeCompilerInput от этого не становятся фиктивными, но весь последующий смешанный loop нельзя называть строго типизированным. Аналогично `Record<string, T>` не доказывает наличие каждого возможного string key. Текущие заполнение/lookup maps рассмотрены выше; полнота произвольного external owner content этим delta не закрыта.

Эти ограничения существовали в base и не расширены patch; автор явно оставил upstream delivery/provider/owner/registry/staged/AST границы вне партии. Поэтому они зафиксированы как предел приёмки, без нового finding на данный bounded delta. Runtime operations, wire fields, order, grade/currentness/publication checks по сохранённому diff не менялись. Новых `any`, casts, schema, runtime guards, механического `unknown` или suppressions нет. CI pin в `.github/workflows/ci.yml:43` остаётся `830381697beea4106655965e10605cc13987199b`; это отдельная composition от локального Core Source d7, новый paired CI не заявляется.

## Собственный первичный допуск

Первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T20-16-00-01a1a269-1e58-7e52-805f-d93c2a9cd8ef.jsonl`: `session_meta` строка1 подтверждает chat `01a1a269-1e58-7e52-805f-d93c2a9cd8ef`, parent `01a1a142-7adc-7371-9765-4871d807dfcd` и agent path `/root/review_pl_materialization_types`. Прочитаны все собственные turn_context записи: строка8 прежнего интервала и строка118 текущего, turn `01a1a2a4-41d5-7ac3-9092-2d28bb498f44`. Обе имеют top-level `model=gpt-6.1-sol`, `effort=high` и вложенные `settings.model=gpt-6.1-sol`, `reasoning_effort=high`; расхождений нет. Текущий допуск относится к новому интервалу строки118. Standard prescribed; primary запись не удостоверяет серверный service tier. Глобальные и нормативные settings не менялись.

Единственная запись рецензента в этой партии — данный русский report вне PL Source repo.
