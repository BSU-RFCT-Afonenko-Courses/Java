# Core: первый Native-effective physical-path contract

**Предлагаю первый breaking semantic slice: удалить scalar-marker/CST origin qualification из выбора project paths; оставить Native effective metadata единственным источником merge/rebase.** Сохранять прежний `NativeOrigin` с фиктивными filename/raw/range нельзя. Этот документ — Source proposal, не реализация или Native acceptance.

База Core подтверждена: `f90713ff3480d5c77deccef4979e98e09279b099` в `core-native-bridge`. Переиспользован [necessity report](core-origin-contract-necessity-20261104.md); YAML/vendor archaeology и новый аудит не проводились. Применимые AGENTS/spec и русское языковое правило прежние.

## Какой договор нужен вместо origin

`contracts/project.ts` уже выводит `ProjectContract` из `ProjectContractSchema`; внешний `formats[format].metadata["course-project"]` остаётся unknown до существующего `decodeProjectValue`. После него один producer в `infrastructure/project-context.ts` перечисляет **только path-valued поля этого contract**, нормализует Native effective value через `resolve(dirname(input), effectivePath)` и применяет прежние owner-containment/cache/physical existence/type/lifetime проверки. Ни YAML merge, ни восстановление lexical raw, ни filename эвристики ему не нужны.

В current контекстах `origins` заменяется таблицей `physicalPaths`: field key → нормализованный абсолютный physical source. Её тип выводится из фактического Core builder; экспорт принадлежит Core. `ProjectContext` сохраняет unknown extras effective metadata, `ProjectContractContext` — уже разобранный contract. Private generation/lifetime binding остаётся authority; таблица не становится сериализованным proof. Boolean dependency/selection поля больше не требуют scalar origin.

`artifacts/plan.ts:197–268` читает physicalPaths вместо `origins[field].resolvedPath`. `assignment-roles.ts:165–216` назначает те же роли по field и selected testsets/components: starter/build/instructions публичны, solutions приватны, testsets/components публичны только в student selection. Physical `{path,identity}` и alias checks сохраняются из existing `projectContextSourceIdentities`; удалять их вместе с origin нельзя. Selected files, destination targets и student/full partition не меняются самим новым DTO.

`artifacts/project.ts:326–335` использует фактический instruction fragment source. `MaterializationClosure.baseKind` удаляется вместе с его schema/argument в `artifacts/contracts.ts:206–223`: scoped поиск в Core artifacts/PL/Download не нашёл business reader этого поля. Links/resources уже разрешаются относительно actual fragment source. Новое слово `definition` не будет выдаваться за доказанный Native provenance.

## Существенная поправка к простому «effective + input»

Installed primary Source `/opt/quarto/bin/quarto.js:76858–76915`, `toInputRelativePaths`, перебазирует строку **только если существует `baseDir/value`**; иначе сохраняет value. Поэтому existing inherited `tests/native-context-a1.ts` сам по себе ещё не доказывает смену пути: он не создаёт общий `projects/tests/demo`.

Конкретный будущий differentiator того же existing fixture: parent `projects/_metadata.yml` задаёт `testsets.demo.path: tests/demo`; существуют local `projects/a/tests/demo`, `projects/b/tests/demo` **и shared/decoy `projects/tests/demo`**. Старый raw+project выбор — local a/b; Native rebasing при существующем shared пути выберет shared. Этот case сейчас **не запускался**. Нельзя исправлять это stripping `../` или basename — нужен authored migration.

Второй предел: definition-relative parent path отсутствует, но одноимённый input-local decoy существует. Native может оставить строку и выбрать decoy, тогда как old origin дал бы missing declaration path. Новый договор не обещает прежние missing-source diagnostics/selection во всех таких случаях. Это конкретная нерешённость для немигрированных inherited declarations, не повод вернуть origin resolver.

## Конкретная authored migration

Для inherited `starter.path`, `testsets.*.path`, `solutions.*.path`, `instructions.local.path` перенести **path declarations** из shared parent metadata в `_metadata.yml` каждого соответствующего code-project. Оставить dependency/selection boolean maps общими. Пример для existing a/b fixture:

```yaml
# projects/a/_metadata.yml и projects/b/_metadata.yml
course-project:
  starter: {path: starter}
  testsets:
    demo: {path: tests/demo}
  solutions:
    reference: {path: reference}
  instructions:
    local: {path: README.local.md}
```

Parent больше не задаёт эти paths. Файлы не переносятся. Declaring directory теперь совпадает с input directory: Native offset нулевой, и существование shared decoy не меняет выбранные local physical paths. Это migration declarations, без нового path-base flag/DSL. Definition-relative `components/build.files/instructions.gradle` допускают Native rebase; их все missing/decoy случаи эквивалентными не объявляются. Если конкретному authored случаю нужна строгая прежняя цель, её также явно задают в leaf metadata относительным путём к прежнему physical source.

Адресные actual examples, без whole-course inventory:

- Java `tasks/projects/numbers/{console-greeting,image-size}/_metadata.yml` и `tasks/projects/arrays/reverse-array/_metadata.yml` уже leaf-local: starter, api/demo/checks, reference/alternatives, local README; common components/Gradle paths также записаны в этом leaf. Для input **в той же директории** offset нулевой, старый и новый physical selection совпадают по Source. Authored файлы менять для этих трёх примеров не требуется. Ref bindings прочитаны в `tasks/bank/{numbers,arrays}.qmd`.
- SP actual checkout `system-practice`, HEAD `9a2f8120a37d1b42384e2f008b3e0a3a34247008`: `tasks/projects/work-environment/file-state/_metadata.yml`, `version-control/github-ssh/_metadata.yml`, `data-processing/two-result-pipeline/_metadata.yml`. Starter `student`, local `student/README.md` уже leaf-local; file-state build sources `../../../common/work-environment/count.{fish,ps1}` также объявлены там. Та же Source эквивалентность для same-directory input. Это несколько examples, не утверждение обо всех Java/SP проектах/profiles.

Критерий сохранности первого slice: для каждого выбранного fixture field/student/full plan совпадают actual normalized physical root, выбранные file names/targets, роли и physical-alias refusal; instruction source и его link/resource base прежние. Не YAML filename/raw offsets и не новый hash/certificate. UUID, grade/test weights, teacher-private bytes и учебные условия не мигрируют.

## Authored input и synthetic input — разные задачи

В прочитанных Java console-greeting и двух SP leaf примерах `native-input.qmd` **отсутствует**. Download/current owner requests добавляют его как synthetic path; existing A1 fixture тоже создаёт такой отсутствующий input вне book chapters. Native acquisition для отсутствующего/исключённого original input без copy не доказана.

Поэтому первый semantic diff сохраняет existing disposable owner acquisition и source identities. Он удаляет `instrument`/`collectOrigins`, marker tree/collision/shape checks, origin-only alias/tag/warning/format/metadata-file qualification и NativeOrigin exports. **Не обещает удалить copy целиком.** Остаточный `probeConfiguration` CST rewrite authored `project.render` относится к техническому selector/synthetic route; его parser dependency нельзя назвать удалённой вместе со scalar transport. Direct original acquisition для реально existing authored selected input — отдельный следующий вопрос, без fallback/shim в новом physical-path contract. Нового parser/resolver вместо Native нет.

## Первая конкретная задача

Один согласованный Core slice: `contracts/project.ts`, `infrastructure/project-context.ts`, `artifacts/plan.ts`, `artifacts/project.ts`, `artifacts/contracts.ts`, `runtime/assignment-roles.ts`, `public.ts`; затем current A1/role/closure expectations и docs Native context на новом contract. Inherited fixture migration выполняется в authoring setup этого же existing case, включая shared decoy; не реальный all128 rollout. Existing private state/copy/currentness остаются остатком, не новым механизмом.

Предметный результат: selection получает реальные Native effective paths, старые lexical origin fields исчезают из API/планирования/roles. Physical guards/roles/lifetime сохраняются. Source-equivalence не заявляется для всех немигрированных inherited/missing/profile cases. Будущие проверки/лимиты назначаются отдельно root; здесь compiler/tests/Native/render/inspect/hash/build/network/DB/product edits/commits/new agents —0. Только этот report. Performance accepted9ba и его прежний budget не затронуты; own high admission/globals прежние, mainstate/audit root-only.
