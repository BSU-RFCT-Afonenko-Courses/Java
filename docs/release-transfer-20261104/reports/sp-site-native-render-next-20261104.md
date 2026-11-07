# SP Site: один native project child вместо собственного document resolver

## Допуск и Source граница

Исполнитель `/root/qrc_perf_companion`, назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a50e-ccbf-79e1-987c-e5a2fc9f6003`, origin полного интервала `01a1a50e-cceb-7e32-8be1-03b769ca00fb`, root `01a1a4dd-242d-7930-89b5-5da44195e538`. Текущий Source-only план получен в turn `01a1a522-7027-76f0-81a6-d58876627092`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-36-12-01a1a50e-ccbf-79e1-987c-e5a2fc9f6003.jsonl`: session_meta строка 1 подтверждает собственные chat/path; все turn_context от того же origin — строки 8,129,277,511 — имеют согласованные top-level и nested `gpt-6.1-sol/medium`. Вход PASS; Standard назначен, скрытая маршрутизация не удостоверяется, globals не менялись.

Прочитаны `reports/quarto-cache-boundary-20261104.md`, pattern-pass-export S1/S2/S6, Site/SP AGENTS и SP Site current contract из exact blob. Общее языковое правило и model-admission прочитаны в предыдущей части того же полного интервала. Местный SP AGENTS содержит старое high для code/fixtures; здесь прямое назначение root — medium и только Source-only план, без изменения курса/кода.

Точный [исторический отчёт](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/quarto-cache-boundary-20261104.md) сохраняет исходные результаты и ограничения этого упоминания.

SP Site — **только** `git show/git grep` существующего bare `/home/tolya/JavaCodex/java-release-20261110/provider-sources/quarto-project-publish` на exact `e0efd6b5428412500569d0da5a5e0b33d62a5663`. Java Site прочитан через `git show` exact `d16f2322c1da3c96c2e93d32225f5428ae347756`, composition runtime pin координатора `e8b345cec235c2c8438c479892f6bca2a4ce3ee7`. Авторский SP config прочитан в `system-practice` на HEAD `3bb0e0789545420b9947bdd85fac3d71715d0e36`. Ни checkout/clone, ни Source/provider/installed pins, ни курсы, ни state не изменялись. Создан только этот отчёт.

## Вывод и рекомендуемая граница

**Авторский SP не требует per-document rendering для mixed HTML/Reveal.** Его lectures/practice имеют HTML index и Reveal slides, что нативный `render . --to default` сохраняет по effective default **каждого документа**. Нельзя заменить его uniform `--to html` либо `--to revealjs`: первый превратит слайды в обычный HTML, второй превратит индексы в слайды. Root directory `.` сохраняет native project selection и возможность global freeze; список source-файлов в argv эту семантику меняет.

Наименьший Source пакет — **два production-файла SP Site**, config.ts и compose.ts. Для нынешних `subprojects: [strings]` задавать native `--to default` без нового config key, не вычислять document formats. Это перенос существующего Java **способа исполнения**, с сохранением SP authored shape. Сам публичный SP contract намеренно сужается/меняется: прежний автоматический выбор единственного web формата независимо от порядка заменяется native default. Это надо явно принять и описать; не выдавать изменение за полную обратную совместимость произвольного старого config.

У Java уже есть иной existing public shape `{path, to}` с `to: default|html|revealjs`. Его можно перенести отдельным согласованным compatibility шагом, но для текущего authored SP он **не обязателен** и является новым принимаемым shape именно относительно SP pin. Не строить третий format API/resolver ради этого плана.

## Что реально объявлено в SP

| Авторский путь | Выбранная структура | Последствие для одного native child |
| --- | --- | --- |
| `_quarto.yml:1–5,40–47,76–88` | Website root, render только index; subprojects строки theory/lectures/tasks/practice/handbook; default student/group full; root html | Root render остаётся отдельной native operation, дети — по одному root child каждый. Не расширять root selection на все QMD |
| `theory/_quarto.yml:1–3,25–31`, `handbook/_quarto.yml:1–3,25–31`, `tasks/_quarto.yml:3–5,30–36` | Три книги HTML, главы задаёт native book config; student профили добавляют html safety options | `--to default` сохраняет HTML и native book selection/navigation; не перечислять главы самостоятельно |
| `lectures/_quarto.yml:10–13,40+` | Website, selected index/command-interface/version-control, project revealjs | index (`index.qmd:2`) отдельно `format: html`; обе лекции собственный `format.revealjs`. Native default может быть различным по документам |
| `practice/_quarto.yml:26–33` | Website, пять выбранных документов, project revealjs | index (`index.qmd:2`) html; четыре introduction QMD (`:16–17`) revealjs. Это не конфликт и не причина запускать пять children |
| Root/child `_quarto-student.yml` и `_quarto-full.yml` | Отдельные `_site-student/full` и `_output/student/full`, student/author projection; у practice/student и книг/student есть html options, у lectures/student формат не добавлен | Оставить native merge/profiles/defaults/groups. Нельзя выводить effective doc format самодельным YAML merge только по этим файлам |

В адресно прочитанных selected root/child config и выбранных mixed QMD нет authored PDF/docx/typst/beamer выбора, execution cache/freeze override или engine setting, который требовал бы per-document argv. Это ограниченное чтение объявленного selected состава, не global course inventory. Действительная native effective metadata при всех includes/profiles в этой роли не вычислялась.

Из Source нельзя утверждать, что **текущий** SP обязательно уже попадает в `renderTo:null`: прежний code делает D×document inspect всегда, а fallback нужен лишь для более узкого сочетания formats. Факт смешанных html/revealjs сам по себе `null` не доказывает. Измерений количества фактически запущенных children текущего SP здесь нет.

## Зачем существовал fallback

SP `infrastructure/config.ts:339–434` сначала получает native project inspect, проверяет safe selected inputs (`:349–370`), затем делает отдельный inspect каждого документа (`:374–377`). Выбирает единственный web output-ext=html, даже если первым declared format является PDF (`:378–417`). Несколько web formats — SITE.FORMAT_AMBIGUOUS. `renderTo` выбирается так (`:421–428`):

- У всех документов один формат — root render без `--to`.
- Есть дополнительные formats, выбранный web всегда первый — root `--to default`.
- Web не всегда первый, но это book и у всех выбран один key — root `--to <key>`.
- Остальное — `null`, затем compose делает individual `render <document.source> --to <document.format>` (`compose.ts:378–382`).

Поэтому **реальное свойство, требующее старого fallback**, — разные selected web keys либо порядок web/nonweb, который нельзя выразить одним uniform `--to` и который автор не выразил native default. Это наша прежняя web-preference policy. Engine/freezer не требуют этой policy.

Existing `tests/site-effective-formats.ts` подтверждает назначение Source: mixed default `:21–30`; web-first html+pdf → default `:36–45`; pdf-first html+pdf у mixed website → null `:46–51`; custom-html → base html `:52–67`; multiweb ambiguity refusal `:68–75`. Эти тесты здесь **прочитаны, не запускались**. Custom format не обязан исчезнуть: native `--to default` разрешает его как default key; не вводить whitelist только html/revealjs вместо native default.

Pinned Quarto1.11.5 `/opt/quarto/bin/quarto.js:91239–91275` уже делает нужный выбор: отсутствующий `to` рендерит declared formats, `to === "default"` выбирает `formats[0]` после native included metadata/flags merge. Это per-document native rendering context. Java `compose.ts:459` вызывает именно `render . --to project.to`; Java `config.ts:346–405` получает только selectedSources/config/resources из project inspect. Здесь не исполнялся Quarto и не повторялся R3.

## Минимальный Source diff, без нового resolver

**1. SP infrastructure/config.ts.** Заменить тело `inspectDocuments` на native project inspect + безопасный selected source list + resources. Имя можно сохранить для меньшего caller diff либо адресно переименовать `inspectProject`, как у Java; поведенчески нужен именно один project read. Убрать DocumentFormat/DocumentPlan форматную проекцию, `documentFormat`, D×inspect, web ambiguity и renderTo. Сохранить containment/safePath/lstat **до cleanup**; использовать resolved path для lstat (старый `:359` использует input напрямую). Унаследованные native profiles/format defaults не разбирать своим кодом. Список selectedSources выводить из уже полученного project.files.input, без нового filesystem inventory. Old helper не экспортируется public.ts; production consumers по адресному git grep — только compose и сама config; tests обновляются по назначению.

**2. SP application/compose.ts.**

- `:291–331`: план содержит native selectedSources/config/resources; сохранить releaseQualification, output/audience/overlap preflight.
- `:375–473`: один `quarto(["render", ".", "--to", "default", ...profileArguments(ws.profiles)], project.path, existingEnv, true)`, один collection path и readCollection. Убрать calls loop, per-document captureInputs/withNativeCaptureScope и искусственное слияние нескольких collections. Сохраняется genuine один child native Core operation; не подменять её cloned owner/replayed record.
- Не записывать прежние expectedDocuments из собственной pre-inspect format карты. Сохранить selectedSources в существующем record; добавить проверку их соответствия actual public catalog sources наряду с existing actual output coverage (`catalogCoverage:179+`), по bounded Java `:206–238`. Для `to:default` **не** требовать одинаковый catalog.format у всех документов. `finalizeDownloads:220–238` перестаёт передавать исчезнувшую expectedDocuments; current operation/catalog и selected source/output validation остаются. Это согласованная миграция реальных consumers, не простое удаление одного guard.
- QRC `members` сейчас опирается на `documents.some(web)` (`:578–584`), navigation members — тоже (`:616–618`). Перевести их на **actual** record.nativeOutputs `.html`, как Java `:600–607,640–641`, не на guessed format metadata. Убрать `documents` из сохранённого state/records. Collection version/API менять для этого не требуется.

Это два production файла, не перенос всего Java runtime/progress/DTO/Download lifecycle. Новых any/casts, валидатора DTO, cachekey/hash/proof, общего scheduler или новой format schema не требуется. Честные inferred selected-record types лучше добавлять в существующем файле только по фактическим constructors; не копировать чужой public Core DTO. Runtime-specific private Core imports не вводить.

## Совместимость авторского config и existing explicit to

**Рекомендуемая ограниченная дельта для SP** сохраняет его string paths и означает native default. Текущий `_quarto.yml:81–86` менять не нужно. Existing selected SP HTML/Reveal композиция имеет естественный native default, но до исполнения это Source compatibility inference, не новое доказательство опубликованного DOM.

Есть три намеренных изменения старой общей policy:

| Старый допустимый/отвергаемый config | Новый native default | Действие автора при необходимости |
| --- | --- | --- |
| html/revealjs defaults разных документов | Каждый документ сохраняет свой native default | Для текущего SP дополнительных полей не нужно |
| pdf-first + html second при прежнем web preference | Выберется PDF, а не автоматически HTML | Поставить нужный web key первым в existing document/project/profile format, либо использовать штатный отдельный nonweb render; не сохранять per-document fallback тайно |
| html и revealjs одновременно | Native выберет первый; прежнего SITE.FORMAT_AMBIGUOUS больше нет | Указать нужный default обычным Quarto config. Не заменять старый refusal новым global engine guard |

Если root хочет именно **полностью такой же явный интерфейс, как Java**, reuse `{path, to: default|html|revealjs}` — готовое ограниченное решение без нового API design/config resolver. Тогда те же два SP production файла меняют validator/projectRecord (`SP config.ts:13–16,75–91,186`) на существующий Java shape; root author config мигрирует **пять строк** в `{path: ..., to: default}`. Это настоящее изменение SP accepted shape: старые strings будут отвергаться, пока не предусмотрена явно выбранная backward compatibility. Его нельзя сделать только переключением installed pin и назвать совместимым. Не надо поддерживать обе формы ради abstract compatibility без actual consumer; данная роль config не меняла.

При explicit `html`/`revealjs` параметр действует на **весь child**; текущие смешанные lectures/practice должны использовать `default`. Перенос Java allowlist не должен запрещать native custom default formats. Если нужно сохранить произвольное прежнее «найди единственный web не первым» поведение, одного existing `--to default` недостаточно: это отдельный неизбежный compatibility остаток; не изобретать новый per-file mapping API, чтобы притвориться, что гарантии одинаковы.

## Что сохраняется и чего план не обещает

Site source selection, path ownership, collection correlation, public source/catalog/output matching, resources copy, QRC mount/search and audience privacy — реальные обязанности. SP `collection.ts:109–168` связывает id/root/output/profiles с текущим child; `files.ts:198–235` защищает actual mounted copy. Они не являются запретом native computation и не удаляются ради одного child. Ресурсы/assets шире списка HTML documents, поэтому record.files нельзя заменить только nativeOutputs. Post получает только текущую collection; сохранённые HTML другой попытки не становятся автоматически current.

`cleanOutput` (`files.ts:126–149`) сохраняет protected `.quarto/_freeze/_extensions` границы. Не чистить caches, не добавлять `--execute`, `--cache-refresh`, `--no-cache`, `--use-freezer` или новый global execution lock. Root `render .` даёт Native возможность thaw/reuse, но не гарантирует cache hit, fresher external data или profile separation. R3 уже установил cross-profile freezer reuse; student/full output-dir сами по себе не изолируют execution results. Размеченные role AST и arbitrary printed full-dependent text — разные случаи. Site-only правка не квалифицирует computed consumer в Core и не устраняет отдельно описанную Core currentness/cache несовместимость.

S1 audience alternative/project inspect сохраняется там, где нужен фактический другой output до cleanup; цель этой дельты — D×document format inspect, не «никакого inspect». Native hook selected inputs/output применимы только в своём контексте; root hook не содержит готовый selected set каждого child. Отдельный child project inspect остаётся Native metadata источником.

S6 no-Download путь `ownerRelease/validateCourse` не заменять на PL.inspectPublication: full seed/grading/request correlation/currentness и no-PL-metadata fallback имеют другую семантику. Один root child не является разрешением ослабить этот route. Download completion/receipts/owned cleanup тоже отдельная область.

## Проверки следующей реализации, без запуска сейчас

В будущей дельте адаптировать existing `tests/site-effective-formats.ts`: убрать tests собственного format resolver, оставить native selected source/path и новые документированные selection ожидания. Existing `tests/site-formats.ts:53–94` уже проверяет actual mixed index HTML + Reveal/body/libs; не заменять его snapshot string порядка metadata. Для source/catalog mismatch использовать existing Java независимый control образца `tests/site-native-pipeline.ts:111–136`, адаптировав только реальное поле SP record, без нового mutation API.

Только после согласованного Source diff — одна существующая маленькая composed mixed fixture, где тот же global child сохраняет native freeze hit и поздний consumer/current collection; максимум60s, retry0, без полного курса, новых kernels и повторения R3 engine experiments. Existing privacy/audience/stale output negatives переиспользовать на затронутой границе. Это предложение будущего выполнения; никаких tests/compiler/render/inspect/hash/network/install/clone/commit/push/SSH/production действий в этой роли не было. Измеренное ускорение и installed compatibility **UNKNOWN**.

Перед передачей перечитан весь собственный primary интервал от origin: turn_context строки 8,129,277,511, согласованные top/nested Sol6.1/medium. Итоговый допуск PASS. Mainstate, курсы и installed pins не редактировались; решение об интерфейсе/приёмке следующей дельты остаётся у root.
