# A2: независимое рассмотрение простого списка subprojects

**Решение: APPROVE для Source candidate после исправления текущей документации. Открытых замечаний к рассмотренному delta нет.** Compiler/Native приёмка этим review не подтверждается.

Рассмотрен замороженный Source candidate по `reports/simple-subprojects-evening-20261104.md`: Site на исходном HEAD `1ce0801b0244d0157481a3d00691a92b965d90da`, Java root `_quarto.yml` и System Practice root `_quarto.yml`. Reviewer эту реализацию не писал. Прочитаны актуальный brief A2, инструкции владельцев, действующие Site spec и полный текущий delta. `tasks/_quarto.yml`, A3 и чужие Platform/Gateway/Performance не изменялись.

## Закрытое замечание

**[P2, исправлено] Удалить обещание проверки parent `to` из текущего Native договора.** Первоначально `site-native-audience/docs/public-operation.md:7`, связанный как действующий из `spec/index.md`, ещё обещал сравнение catalog с HTML outputs «и явным `to` внутри callback», хотя поле уже удалено из actual `Project`/`NativeSiteRecord`, producer record и `catalogCoverage`. Автор исправил только этот doc и повторно заморозил Source. Reviewer прочитал фактический diff: теперь договор описывает сравнение selected sources и текущих HTML outputs и выбор формата конфигурацией, профилями и документами дочернего Quarto. README/spec согласованы. Историческое release описание с явной пометкой historical не является замечанием.

## Проверенный договор

На существующей config boundary `subprojects` читается как `unknown` и проверяется как непустой список непустых строк. Type predicate уточняет actual список; `Project` по-прежнему выводится из настоящего `projectRecord`, `Workspace` — из операции владельца. Новый ручной DTO, `any`, cast или compatibility converter не добавлены. Object `{path,to}` и прежний namespace `course-site` не принимаются. Сохранены нормализация путей, исходный порядок, checks каталогов, дублей, symlink, выходов и пересечений до cleanup.

Реальный consumer `compose.ts` запускает child `quarto render .` с прежней передачей профилей, без `--to`. Из actual `Project`, producer record, consumer `NativeSiteRecord` и coverage удалено одно и то же поле `to`. Независимые selected sources и текущие HTML outputs по-прежнему сравниваются с Core catalog. Новые format resolver, дополнительные inspect/render или freshness механизм не появились. Existing root/child audience и currentness boundaries не менялись.

В Java сохранён порядок `theory, tasks, lectures, practice, handbook`; в System Practice — `theory, lectures, tasks, practice, handbook`. Их delta касается только root списка. README, spec, diagnostics, публичный example и executable fixtures переведены на строки. Старое release описание явно помечено историческим и ссылается на новый договор.

`site-effective-formats.ts` теперь содержит одну настоящую композицию: child profile выбирает Revealjs, HTML document front matter выбирает HTML; проверяется содержимое обоих mounted результатов в `part/`. Это полезный публичный результат. Удалены старые parent override cases и private interception/counters/equality из native planning fixture; pipeline mutant `record.to` и его CI mode удалены согласованно. Остальные fixture правки меняют только authored subprojects, без подмены producer types заглушками.

## Проверки, допуск и остаток

Reviewer выполнил только Source/evidence чтение и `git diff --check` (rc 0). Compiler, tests, Native/runtime, network и исходники/state не менялись. Записаны только этот отчёт и собственное свидетельство допуска.

Первичные `session_meta` и все `turn_context` полного собственного интервала от исходного A1 turn подтверждают `gpt-6.1-sol/high` в верхних и вложенных settings. Собственный chat `01a1a6b8-1632-7922-b45e-18d30eeef7bd`, agent path `/root/a1_origin_review_evening`, неизменный исходный turn `01a1a6b8-1664-73c0-b014-463ea736f7df`; A2 assignment turn `01a1a6bc-434c-7e42-ab97-ee05eb3a236e`. [Собственное evidence](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/a1-a2-evening-independent-review-20261104-admission.json) содержит primary path и обе записи без сдвига начала. Первичный допуск A1 не был документирован до его первого verdict: подтверждение получено поздно, при A2; это ограничение сохранено честно. A2 evidence сохранено до окончательного verdict. Standard назначен brief; глобальные настройки не менялись, скрытая серверная маршрутизация metadata не удостоверяется.

Прежний отдельный writer compiler не является PASS: `results.json` подтверждает exit 1 за4.775117649 s с тремя TS2307 relative sibling imports Download и14 cascading TS7006. Финальный predicate после того запуска ещё не проверен. Координатор должен один раз проверить общий A1+A2 type graph с actual sibling import map, включая Site compose/fixtures и Core `release-semantics-a4a.ts`, `release-collection-a4a.ts`, `native-metadata-a4c.ts`; изменить соседние исходники ради карты не требуется. Остатки A1 compiler/Native этим reviewer не расходуются и не сбрасываются.

После настоящего Source approval остаётся один public `site-effective-formats.ts` run с пределом35s включая grace. Полный курс, suites и matrix для этого review не запускать; новое падение диагностировать по конкретной причине. Ни Source review, ни отсутствие Site diagnostics в неуспешном compiler не подтверждают готовность выпуска или installed course. После doc correction снова выполнен `git diff --check` (rc 0), полный собственный primary interval перечитан до окончательного verdict: те же две записи Sol6.1/high, новых turn_context нет.
