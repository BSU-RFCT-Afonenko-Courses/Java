# Готовность QRC reference bindings для SP

3 ноября 2026. Read-only диагноз после единственного bookURL attempt exit1/23.466347102s. Source/config/owner artifacts не менялись; нового capture/render/inspect/hash/tests/fetch/download не было. Единственная запись — этот отчёт.

**Первый cause — отсутствие foreign catalogs в standalone tasks binding, не неверный ID и не доказанный producer bug.** Ссылка происходит из `system-practice/tasks/labs/work-environment.qmd:125`: `xref system-practice-theory sec-work-environment`. Реальный target существует в текущей Source `theory/work-environment.qmd:1` (`# Рабочая среда {#sec-work-environment}`), включён в книгу `theory/_quarto.yml:49`; namespace producer `system-practice-theory` задан там же:39. Переименование ID/namespace не требуется.

Actual catalog, использованный PL: `tasks/_generated/prairielearn-native-render/reference-catalog-local.json`. Прочитанный JSON имеет только namespace `system-practice-tasks` и не содержит ключ `system-practice-theory:sec-work-environment`. Это свежий результат native child, не выбранный старый theory catalog. QRC `entrypoints/post.ts:15` запускает publish в scope local; `infrastructure/publish.ts:100–101` записывает local catalog из реально экспортированных HTML targets. `infrastructure/linker.ts:29–36` в local режиме помечает неизвестные foreign ссылки deferred. Поэтому27отложенных ссылок в логе не означают их разрешение для последующего Body export.

Consumer PL `application/native-materialize.ts:67–70` читает именно этот local catalog; :84–92 при наличии `reference-catalog.imports` вызывает QRC.loadBodyLinks, иначе createBodyLinks только local catalog. В `tasks/_quarto.yml:45–46` задан лишь namespace, imports отсутствуют. Следовательно theory catalog при этой попытке вообще не читался, stale imported catalog не является установленной причиной. `QRC.entrypoints/body-links.ts:12–18` собирает map из local+явных imported targets; :24 вызывает строгий `domain/catalog.ts:12–14`, который и выдал TARGET_UNKNOWN.

Наличие actual current/published target в готовом theory catalog локально подтвердить нельзя: `theory/_output/reference-catalog-local.json`, `practice/_output/reference-catalog-local.json`, `handbook/_output/reference-catalog-local.json` и общий `_site/reference-catalog.json` отсутствуют. Имена и пути выведены из existing конфигов, а не созданы: theory `project.output-dir: _output` (:3) + QRC post (:22), local filename в QRC publish:101. Root `project.output-dir: _site` (`_quarto.yml:3`); Site compose:637 читает итоговый `reference-catalog.json`. Сохранённые R0 report/source-map подтверждают provider provenance, но не содержат текущего SP theory target/catalog content. Удалённые опубликованные каталоги не запрашивались; их current readiness не заявляется.

**Правильный кандидатный локальный путь для theory:** `/home/tolya/JavaCodex/java-release-20261110/system-practice/theory/_output/reference-catalog-local.json`; относительно tasks QRC root — `../theory/_output/reference-catalog-local.json`. Он должен появиться из настоящего native theory render, с реальными targets/labels/page fragments, без stub. QRC `infrastructure/import-config.ts:55–60,100–110,123–135` поддерживает existing imports mapping: alias namespace `system-practice-theory`, source этого файла, source namespace `system-practice-theory`, base-url реальной публикации. QRC catalog-source:6–11 допускает этот внешний локальный путь; imports.ts:54–56 выбирает настоящий sourceNamespace, не синтезирует target. До согласования реального publication base URL theory и получения producer artifact ничего не добавлено. Portal root URL и mount theory позволяют предложить адрес, но не заменяют подтверждение готового published content.

**Следующий ограниченный шаг:** root может назначить одну настоящую сборку текущей книги theory (9configured chapters, `theory/_quarto.yml:48–56`) штатным Quarto render с одним выбранным release profile, с timeout/логом, без повторного standalone inspect и без PLcapture. Она даёт полезные release HTML и QRC local catalog; непосредственно после неё чтением готового JSON проверить точный key `system-practice-theory:sec-work-environment`, page/fragment/labels и provenance source SHA. Измеренного времени theory здесь нет:23.466s относится к7QMD tasks capture и не является прогнозом9theory QMD; бюджет должен быть ограничен root, первый отказ сохраняется без retry. Source не требуется менять ради генерации catalog.

Одна theory сборка закроет только данный namespace. Уже текущие tasks содержат ссылки также в `system-practice-practice` и `system-practice-handbook` (`labs/work-environment.qmd:126–127`, `labs/data-processing.qmd:182–184`, `labs/version-control.qmd:150–152`, `optional/project-maintenance.qmd:29–31`). Поэтому повторять полный PLcapture после одной theory сборки преждевременно. До следующего capture нужен готовый согласованный набор реальных foreign catalogs и ограниченный review imports bindings по public QRC contract, либо настоящий полный Site release catalog с mounts; никакой из вариантов здесь не выполнен. Реальный consumer — PL native materialization/QRC Body resolver, а не отдельный синтетический probe.

**Стоимость:** отказ выявлен поздно, после23.466347102s и полного7QMDcapture. Existing export CLI не поддерживает consume/resume (native-entry:180–184 отказывает existing mode); reuse выполненной generation через CLI отсутствует. Это cost finding и основание подготовить dependencies до следующего capture. Новый protocol/wrapper/fake capsule не предлагается; callbacks внутри Source не выдаются за разрешённый reuse interface.

Собственный полный исходный model interval от baseline01a1a261-9811-7ca3-8a48-e4482c3beafc повторно проверен: turn_context [8, 155, 244, 342, 393, 435], top+nested Sol6.1/medium PASS. Standard prescribed, globals untouched.

## План готовности всех трёх foreign catalogs — только read-only

План подготовлен по поручению root; actual artifact builds, config правки и PLcapture не выполнялись.

Минимальное расширение существующей mapping `system-practice/tasks/_quarto.yml:45` после отдельного подтверждения root:

```yaml
reference-catalog:
  namespace: system-practice-tasks
  imports:
    system-practice-theory:
      source: ../theory/_output/reference-catalog-local.json
      namespace: system-practice-theory
      base-url: https://bsu-rfct-afonenko-courses.github.io/System-Practice/theory/
    system-practice-practice:
      source: ../practice/_output/reference-catalog-local.json
      namespace: system-practice-practice
      base-url: https://bsu-rfct-afonenko-courses.github.io/System-Practice/practice/
    system-practice-handbook:
      source: ../handbook/_output/reference-catalog-local.json
      namespace: system-practice-handbook
      base-url: https://bsu-rfct-afonenko-courses.github.io/System-Practice/handbook/
```

Все значения следуют существующим course facts: root URL `_quarto.yml:51`, mounts/subprojects :81–86, namespaces theory:39/practice:51/handbook:39. Для local catalog page `work-environment.html` нужен scope base URL `/theory/`, а не root URL: QRC `domain/urls.ts:4–6` разрешает target.page относительно импортного baseUrl. Это та же существующая формула композиции Site `application/compose.ts:646–647`. Адреса не обозначают выполненную публикацию новых HTML.

Рабочий API-пример уже есть в Java `tasks/_quarto.yml:223–235`: imports practice/theory/handbook с source/namespace/base-url; там общий `provenance/course-public-catalog.json` содержит страницы с mounts, поэтому baseURL — корень Java. SP план использует тот же публичный contract, но local scope catalogs без mount prefixes. QRC parseImports/readCatalogSource/importTargets/loadBodyLinks уже поддерживают relative files и namespace selection; новых API/profiles/protocols не нужно. Native PL `native-materialize.ts:84–90` сам выберет loadBodyLinks после наличия imports.

**Fresh ordinary CI:** постоянные local relative imports не требуют заранее построенных sibling catalogs для обычного tasks render. QRC `infrastructure/publish.ts:65` при scope local вызывает importTargets с allowMissingLocal=true. `imports.ts:43–46` подавляет только missing local file, :59 также допускает отсутствие выбранного namespace в local режиме; `linker.ts:29–36` оставляет foreign xrefs deferred. QRC entrypoint post:15 всегда использует local scope. Это сохраняет fresh root build/CI, в том числе нынешний порядок subprojects theory→lectures→tasks→practice→handbook. Корневой Site после рендеров объединяет текущие members, и tasks local catalog экспортирует только свои targets (`publish.ts:73–78,100`), не копии imports. Дублирование foreign targets в own catalog не вводится.

Существующий файл с malformed JSON/schema или невалидным imports config не подавляется: это правильный отказ, не повод очищать artifact. Существующие старые catalogs могут разрешать local ссылки до новой sibling сборки; поэтому standalone PL readiness нельзя устанавливать одной только успешностью ordinary render. Перед PL attempt должны быть все три реальные свежие artifacts из одной согласованной Source редакции и подтверждённые target keys. В strict `loadBodyLinks` (`entrypoints/body-links.ts:39–41`) allowMissingLocal не передаётся: PL экспорт закономерно откажет, если dependency ещё не готова.

**Один ограниченный пакет полезных release artifacts:** sequential обычный Quarto render каждого scope ровно один раз, profile student, fail-if-warnings, retry0. Теория —9QMD (`theory/_quarto.yml:48–56`), практика —5QMD (`practice/_quarto.yml:26–31`), справочник —6QMD (`handbook/_quarto.yml:48–53`), всего20configured QMD. Практика имеет type website и default revealjs, это5введений/презентаций, её нельзя называть книгой или добавлять новый `--to html`. Native .html результаты пригодны для QRC; сохраняется установленный format. Теория/справочник — typebook. Обычные outputs каждого проекта `_output` уже заданы; их не перенаправлять в новые synthetic dirs. Продукт пакета — полезные student release HTML/слайды, ресурсы и три real local catalogs, не probe-only exports.

Предлагаемые штатные команды из корня SP:

```sh
/opt/quarto/bin/quarto render theory --profile student --fail-if-warnings
/opt/quarto/bin/quarto render practice --profile student --fail-if-warnings
/opt/quarto/bin/quarto render handbook --profile student --fail-if-warnings
```

Внешний shared wall budget180s на всю последовательность, TERM grace5s, при первом ненулевом exit/исчерпании общего остатка немедленный stop/retry0; оставшиеся команды не запускать. Следующей команде дать только остаток общего180s, не180s заново. Нет отдельного standalone inspect, вторых full/student прогонов, PLcapture или нового hash walk. Время этих20QMD ещё не измерено; нельзя обещать укладывание по23.466s tasks capture, а практический revealjs/resources может быть дороже обычной главы. Сохранять command/cwd/env/time/exit/log каждой фактически начатой команды и cumulative budget; partial outputs не чистить после отказа.

После успешного пакета достаточна адресная проверка готовых трех JSON: реальные namespaces/нужные IDs/page-fragment/labels, включая первый `system-practice-theory:sec-work-environment`, и реальные output HTML. Сопоставить текущие foreign xref keys с catalog targets без рендера или hashwalk; документировать actual readiness, затем отдельный небольшой config commit imports и независимый read-only review. Только после этого root назначает следующую одну native PL попытку. Scope IDs/UUID/target names не меняются, публикации нет. Данный план не закрывает runtime images/deployment и не вводит fake reuse уже прошедшего capture.

Перед передачей плана весь прежний собственный model interval перепроверен: turn_context [8, 155, 244, 342, 393, 435, 484], top+nested Sol6.1/medium PASS; Standard prescribed, globals untouched.

## Исполненный разрешённый student artifact пакет и imports commit

Root утвердил технический план и один полезный artifact пакет. Все три обычных render завершились exit0/retry0 в общем wall67.388199562s, shared budget180s/TERM grace5s не достигнут. Порядок и wall: theory28.169557793s (9QMD), practice18.669604361s (5QMD/default revealjs), handbook20.548554150s (6QMD). SHA Source/config на момент сборки `1626aa0792301f6c91cf2a04bd4d242f89736578`, installed Core830/PLda. Параллельный dirty Core Source не использовался. Полные exact commands/cwd/env/UTC/timeout remaining/exit в [sp-qrc-student-build-execution.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-qrc-student-build-execution.json); логи [sp-qrc-theory-student.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-qrc-theory-student.log), [sp-qrc-practice-student.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-qrc-practice-student.log), [sp-qrc-handbook-student.log](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-qrc-handbook-student.log).

Actual profile override уточнил путь предварительного плана: все три `_quarto-student.yml:2` задают `_output/student`. Полезные outputs сохранены именно там; каталоги не переносились и не копировались. Поэтому окончательные imports используют `../theory/_output/student/reference-catalog-local.json` и аналогичные practice/handbook. Базовые `_output/reference-catalog-local.json` из предварительного плана не использованы. Никакого нового profile/API/output layout нет.

Чтение real JSON подтвердило theory namespace system-practice-theory/74targets, practice namespace system-practice-practice/5targets, handbook namespace system-practice-handbook/43targets. Из семи сохранённых tasks native HTML извлечено27foreign reference occurrences/24unique keys; все24keys найдены в реальных трёх catalogs. Existing current tasks Source содержит те же27occurrences/24keys; HTML и Source key sets совпали. Target page каждого foreign reference существует в actual sibling student HTML output. Это количества QRCtargets/references, они не подменяют количество PLquestions/projects/transports.

Разрешённая узкая readiness через существующий public QRC API завершилась exit0: `/opt/quarto/bin/quarto run /tmp/sp-qrc-known-refs-readiness.ts`, max20s/TERM5s, persistent XDG/no-network env. Временный script только вызывает installed `reference-catalog/public.ts` `loadBodyLinks` на tasks localcatalog с тремя in-memory imports и `resolve` для27реальныхforeignrefs; нового framework или synthetic catalog нет. Полные resolved href/labels/source/style в [sp-qrc-known-refs-readiness.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-qrc-known-refs-readiness.json), статус `PASS_EXISTING_QRC_RESOLVE`. Parsed catalog/source evidence и proposed/actual imports в [sp-qrc-student-catalog-readiness.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/sp-qrc-student-catalog-readiness.json). Первый cause `system-practice-theory:sec-work-environment` разрешён существующим resolver к реальной theory HTML странице/fragment.

Только после3success и readinessPASS применена одна YAML config правка: `tasks/_quarto.yml`, добавлены13строк imports без изменения existing namespace/URL/IDs/UUID/целей. Все source paths указывают на реальные student catalogs, base-url по root+mount. PyYAML exact semantic delta (только imports) PASS, concrete diff прочитан, unstaged/cached diff-check PASS. Config commit `babe83d1a6c15289b4dd457fe4132bebeac0894c` поверх1626; послеcommit SPstatusclean. Java/rootdocs/Sourceproviders/studentdata/production не менялись; push/PR/PLcapture/standaloneinspect/hashwalk не выполнялись. Старые partialoutputs сохранены.

Передача на независимый read-only delta review: exact `1626aa0792301f6c91cf2a04bd4d242f89736578..babe83d1a6c15289b4dd457fe4132bebeac0894c`, файл `tasks/_quarto.yml`, real evidence выше. Следующая PLcapture не запускалась и ждёт rootreviewACCEPT. Real catalogs готовы на этой машине, но ignored generated outputs не становятся shipped files одним configcommit; fresh standalonePLexport на другой машине требует того же обычного producer build пакета. Обычный fresh courseCI продолжает использовать QRC local allowMissingLocal/deferred contract. Ссылка на student catalogs намеренно адресует public release labels/targets; full/student матрица не повторялась.

Полный прежний собственный admission interval повторно проверен перед передачей: turn_context [8, 155, 244, 342, 393, 435, 484, 524], top+nested Sol6.1/medium PASS, baseline неизменен; Standard prescribed, globals untouched.
