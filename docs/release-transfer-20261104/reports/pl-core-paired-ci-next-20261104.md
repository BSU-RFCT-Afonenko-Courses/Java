# PL: минимальный следующий paired CI с reviewed Core

**Source-only план. Никаких product/CI edits или запусков не выполнено.**
До успешного Core CI на том же reviewed exact SHA текущие pins не менять.
R/knitr в hosted PL runner по прочитанному repository Source — **UNKNOWN**;
отсутствие явного install не доказывает ни отсутствие, ни наличие engine.

## Точные входы и gate

| Компонент | Проверенный локальный Source SHA | Роль |
| --- | --- | --- |
| PL | `d8efb030ee6435f3c1f7d242e7388803b55b3299` | Текущий source с существующим computed fixture |
| Core | `4a2903e6d03d1dd489b6922d113100826913cf67` | Текущий reviewed test-alignment tip; Core hosted PASS ещё не удостоверен этой задачей |
| Download | `944ca76dbdef5bb4842116950621f82449b631b3` | Настоящий sibling extension, использованный сохранённым local computed consumer PASS |

SHA прочитаны через локальные Git commit metadata, без network/status polling,
новых hashes или CI queries. Ни ветка, ни tag не предлагаются как pin.
Если Core tip изменится, использовать новый reviewed actual commit SHA только
после review и Core CI **того же** commit; не подставлять предполагаемый future tip.

Прочитаны PL `AGENTS.md`, `spec/index.md`, `.github/workflows/ci.yml`,
`tests/check.ts`, `tests/native-core-typecheck.ts`,
`tools/native-core-import-map.ts`, relevant type entries и
`tests/native-a1-builder-integration.ts`; сопоставлены сохранённые local evidence
`pl-computed-consumer-integration-20261104.md`. Этот план не повторяет их запуск.

## Минимальная pin-дельта после Core gate

В `.github/workflows/ci.yml` текущий Core
`830381697beea4106655965e10605cc13987199b` встречается ровно в четырёх местах:

- line 43: Core checkout `check`;
- line 49: exact assertion того же checkout;
- line 113: Core checkout `java-delivery`;
- line 119: exact assertion того же checkout.

После exact Core CI PASS все четыре литерала должны стать
`4a2903e6d03d1dd489b6922d113100826913cf67` в одной reviewable дельте.
`EXPECTED_SOURCE_SHA` PL продолжает связывать собственный checkout с
`github.event.pull_request.head.sha || github.sha`; его механизм менять не нужно.
QRC/Site pins относятся к другим dependencies и не входят в эту ограниченную
pin-дельту. План не удостоверяет их новую композиционную совместимость.

## Где уже есть compiler batches

1. `tests/native-core-typecheck.ts:19–50` имеет **один positive `Deno.Command`
   compiler batch** с несколькими entrypoints и actual Core map, создаваемой
   existing `writeNativeCoreImportMap`. После него helper отдельно выполняет
   ожидаемые negative compiler cases. Полный helper не равен одному compiler
   process; `--course-export` и `--legacy-boundary` меняют состав по existing rules.
2. Этот helper **не зарегистрирован** в текущем `.github/workflows/ci.yml`
   и не вызывается `tests/check.ts`. Наличие файла нельзя выдать за hosted type
   coverage его специальных `native-*-types.ts` assertions.
3. `tests/check.ts:8–31` уже собирает все `*.test.ts` и передаёт их одним
   `deno test` batch с actual import map. Default typecheck этого batch охватывает
   их импортируемый product graph, но не отдельный файл
   `native-a1-builder-integration.ts` и не все standalone type assertion entries.
   В конце отдельно вызывается existing genuine ordinary
   `native-scope-integration.ts`.
4. `java-delivery` уже содержит собственные фиксированные `deno test` batches;
   `native-core-import-map.ts` вызывается в workflow:124 до них. Это не новая
   матрица и не регистрация computed fixture.

Для одного affected compiler до будущего commit следует использовать **тот же
existing map generator** и один общий `deno check` argument list, включив
computed fixture и затронутые typed entries вместе. Не запускать compiler по
одному файлу. Если решено расширять hosted type coverage existing standalone
helper, fixture можно добавить в его positive batch один раз; регистрация
полного helper с его negative processes — отдельная явно reviewable дельта,
а не скрытая обязательная часть минимального runtime registration. Новый
квалификационный job/матрица для этого не требуются и здесь не предлагаются.

## Один existing fixture, одна existing job

`tests/native-a1-builder-integration.ts` не содержит `Deno.test`, не имеет
суффикса `.test.ts`, не вызывается из `tests/check.ts` и прямо не зарегистрирован
в workflow. Следовательно, current `tests/check.ts` не подхватывает его автоматически.

Минимальное место однократной runtime-регистрации — **existing `java-delivery`**:
в ней уже есть Quarto 1.11.5, Deno 2.7.14 и actual Core import map. Не добавлять
тот же fixture одновременно в `check`, в glob и в новый job. В `check` сейчас
нет отдельного generated map step, поэтому перенос туда породил бы лишнюю
подготовку при уже пригодной existing job.

При подтверждённом engine добавить ровно:

1. Checkout `Afonenko-Course-Tools/quarto-project-download` existing pinned
   `actions/checkout@3d3c42e5aac5ba805825da76410c181273ba90b1`,
   `ref: 944ca76dbdef5bb4842116950621f82449b631b3`,
   `path: .ci-deps/download`, `persist-credentials: false`, только в
   `java-delivery`. Repository identity подтверждена локальным Git remote
   `github.com/Afonenko-Course-Tools/quarto-project-download.git` (без сетевого обращения).
2. В existing dependency assertion step проверить
   `git -C .ci-deps/download rev-parse HEAD` на тот же exact SHA. Не добавлять
   download/package install или отдельную qualification matrix.
3. После existing map/Deno setup (можно после toolchain setup и до текущих
   Java/PL checks) один именованный runtime step:

```sh
deno run --no-check --no-config --no-lock \
  --import-map .ci-deps/pl-native-imports.json \
  --allow-read --allow-write --allow-env --allow-run \
  tests/native-a1-builder-integration.ts \
  .ci-deps/core .ci-deps/download/_extensions/project-download
```

Это план команды, **не выполненный запуск**. Не копировать локальные `/tmp`
map/cache paths в hosted job. Не задавать `--deny-import` без доказанного
предварительного наполнения hosted cache: текущий CI resolver/cache режим
не равен локальному `--deny-import` evidence. Нового resolver, глобальных env
изменений или engine install этот план не вводит.

Первый аргумент fixture — **Core repository root** (`:16` добавляет
`_extensions/course-core`), второй — **Download extension directory** (`:196–210`
копирует его рядом с соответствующим Core и динамически импортирует `public.ts`).
Передача Download repo root вместо extension даст неверную module composition.
Sibling layout — часть actual existing fixture, а не новый import cache/protocol.

Fixture проверяет ordinary Native capture → genuine standalone A1 → PL
materialization/HTML/clientFiles → README/ZIP, Table/Image/inline42, resource bytes,
UUID/grading selection и privacy. Он не становится authenticated assignment,
полным course export или запуском JVM grading только из-за нового CI step.
Download944 пакует переданные bytes; новая SHA verification authority ему не
приписывается. Данный fixture сам по себе не повторяет внешний saved ZIP CRC
верификатор из локального execution report: если этот отдельный факт нужен в CI,
его следует явно определить до implementation, а не назвать уже покрытым.

## R/knitr: честный предел Source-only плана

Fixture содержит executable `{r}` cell (`:144–154`): R вычисляет42, печатает
Markdown table, рисует PNG через base `plot` и использует inline R. Нужен
работающий R/knitr engine, а не только наличие `quarto` executable.

В текущем workflow указаны `ubuntu-latest`, Quarto setup, Deno и Java/Gradle.
В repository Source не найдено explicit `setup-r`, установки `r-base`/knitr,
engine image pin или R version/package assertion. Внешнюю реализацию setup action
и текущий hosted image inventory задача не читала (network запрещён).
Поэтому **наличие R/knitr UNKNOWN**, включая его версию. Успешный local запуск
с knitr1.51 удостоверяет другую среду и не закрывает hosted вопрос.

Не добавлять молча CRAN/apt install, новый engine или новый job. Две явные ветви:

- Если trusted existing runner evidence подтверждает пригодный R/knitr на
  выбранном exact workflow runtime, один existing fixture можно зарегистрировать
  указанным способом без новой матрицы. Engine availability не выводить из
  отсутствия install lines и не обходить через `if missing then skip`.
- Пока такого evidence нет, допустим **pin-only paired CI** после Core gate:
  обновить четыре Core literals, сохранить существующие ordinary Native/PL
  source-contract tests и не включать требующий неподтверждённый engine fixture.
  Computed hosted coverage остаётся открытой; local computed PASS сохраняет свой
  ограниченный статус. Если требуется именно automated computed hosted proof,
  явная отдельная authorization/qualification existing engine environment должна
  предшествовать регистрации.

Альтернатива без нового engine может сохранить только source-contract/обычную
Native AST delivery границу: existing `native-scope-integration.ts` и product
`.test.ts` batches уже используют current Markdown/Quarto путь. Подмена R cell
статическими Table/Image/42 или synthetic AST снизит доказательство до доставки
заранее заданного body; это **не equivalent computed test** и не предлагается
как скрытая замена. Новая engine-free computed fixture требует отдельного
осмысленного договора и review, не относится к минимальной pin-дельте.

## Собственный первичный допуск и отсутствие исполнения

Agent `/root/core_yaml_types`, собственный chat
`01a1a54c-ab39-7a83-808d-a190428ff628`, неизменённый origin turn
`01a1a54c-ab6a-7412-894c-270be9b9aac3`, назначение `gpt-6.1-sol/medium`.
Standard назначен, hidden routing не утверждается. Весь исходный интервал
проверен по собственным primary session_meta/turn_context и вложенным settings;
свидетельство рядом:
[pl-core-paired-ci-next-admission-20261104.json](pl-core-paired-ci-next-admission-20261104.json).

No product/CI edits, CI runs/polls, compiler/build/test/render/inspect/hash,
network/install, commit/push/mainstate. Созданы только этот план и свидетельство
допуска в release reports.
