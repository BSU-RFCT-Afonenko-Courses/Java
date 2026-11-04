# Начать продолжение здесь

Пользователь разрешил сохранить все результаты в GitHub для другого устройства. Это checkpoint, не выпуск и не приёмка production. Все расписания на прежнем устройстве PAUSED. Последнее сообщение — продолжаем обсуждение списка ночных работ; автоматически реализацию не начинать.

## Последние решения

- Выбран узкий собственный загрузчик только course-project: сохраняем компактные иерархические YAML, каждый contributing file читается один раз за операцию. Без копии курса, scalar markers, origin DTO, фиктивных QMD и per-project inspect. Реализация пока не начата. Batch-патч Quarto НЕ выбран.
- К10ноября09:00Minsk должны быть опубликованы и доступны ВСЕ128задач банка. Десять лабораторных явно ссылаются на состав;41задача входит в них,87остальных текущийPLrouteнепубликует. Банк12тем, минимум3вопроса.87navigation wrappers — лишняя навигация, ещё не удалены.
- Максимально перейти на редактор с настоящим именем и кодом primary starter.75fixed-single ещё upload; multi-editor уже существует; free-primary требует согласованной доработки. Не переименовывать все файлы вMain.java.
- Согласованно удалить собственные SHA/snapshots/inventories/freshness fences и старые branches producer/consumer. Совместимость старых собственных API не нужна. Единица релиза/обновления — весь курс.
- Gateway должен стать малым адаптером Moodle входа. Реальные assignments/approval/capacity/AGS функции и отдельный собственный NodeBridge разобраны, окончательный продуктовый объём ещё обсуждается.
- Обратная связь тестов НЕ закрыта: public контекст теряется при перезаписи leaf карточек, часть assertions без expected/actual, transpose helpers дают ложные tool failures, установленный UI не принят. Старые исправления delta и MethodSource status уже есть, не повторять.
- Сверить ночь с Tuesday exactdd5b76a: ZIP/README/code downloads/resources/links, обаSPtransports+Workspace lifecycle, обычныеcache/freeze/executablecells, actualPerformancephasecoverage. Новаяметодика/лекция9/Print/Cloud внепакета; баллы вторичны, старуюисторию/VM/studentdata сохранять.

## Актуальные checkout

| Каталог | Репозиторий | Ветка/коммит | Где сохранён |
|---|---|---|---|
|core-native-bridge|Afonenko-Course-Tools/quarto-course|codex/core-native-bridge-20261110 /010ba917909fa7d44179d4a1a05291f88f061775|Точный Git bundle+shallow и tracked archive ниже: обычный push блокируется транспортом|
|prairielearn|Afonenko-Course-Tools/quarto-course-prairielearn|codex/java-release-20261110 /1ad5516e543d07804aa09e426302413c21542e3e|PR36|
|download|Afonenko-Course-Tools/quarto-project-download|codex/java-release-20261110 /09e797a1f56bdeb31a66c3e2afa7b8aba7f90321|PR19|
|qrc|Afonenko-Course-Tools/quarto-reference-catalog|codex/java-release-20261110 /541a44836cf5ec934a101059cbee77c9ff47d134|draftPR22|
|site-native-audience|Afonenko-Course-Tools/quarto-project-publish|codex/site-native-audience-20261110 /2b397815cfdbf0e860405559de68067fe20204b4|Точный Git bundle+shallow и tracked archive ниже: push блокируется транспортом/workflow scope|
|system-practice|BSU-RFCT-Afonenko-Courses/System-Practice|codex/java-release-20261110 /d30345098dcfb1da1530eca5021319bf91fe0f75|draftPR10|
|platform|Afonenko-Course-Tools/prairielearn-platform|codex/java-release-20261110 /6616222206d3e0843ebc560af38c2dc3519557f0|draftPR35, WIP: full review/Docker не приняты|
|performance-lifecycle|Afonenko-Course-Tools/quarto-course-performance|codex/performance-lifecycle-20261110 /7a1645936924ba0dd710c9121a8871a13d67a84b|draftPR6, summaryboundedPASS/compilerblocked|
|gateway|Afonenko-Course-Tools/moodle-prairielearn-gateway|codex/java-release-20261110 /901624527dce2d37a62fedaa9b768cbbbfd1e366|remoteHEAD подтверждён, branch==main, отдельногоdiff/PRнет|

Java берётся из PR этого checkpoint; сохраняет весь source, docs и этот пакет. Для чтения соседних старых относительных ссылок можно положить checkout как <root>/java, а reports и briefs этого пакета скопировать в <root>/reports и <root>/briefs.

## Восстановление blocked Source и архивных WIP

[Git bundles, сохранённые shallow границы, архивы и точные команды](reports/transfer-source-bundles-20261104/README.md). Восстановление всех четырёх комплектов в пустые bare repositories проверено. Голый bundle без его shallow sidecar недостаточен: следовать README. Core010ba иSite2b — актуальные.

Архивные, НЕПРИНЯТЫЕ направления отдельно: oldCore d8cf30e (base46b3590, remotecheckpointcodex/checkpoint-core-frozen-20261104); oldSiteSP dcb59dd(basee0efd6b); failedPerformanceTERM111f09dfa54cdf76a97c9294aeb0fdae88bb8e78 вdraftPR7. Не применять их поверх актуальных веток.

## Что читать

Сначала ../release-cycle-20261110.md и ../release-evening-20261104.md, затем reports/hierarchical-yaml-single-pass-options-20261104.md, editor-test-feedback-status-20261104.md, tuesday-requirements-night-reconcile-20261104.md, sha-removal-closure-20261104.md, gateway-responsibilities-human-20261104.md. Главный файл состояния содержит более подробные границы/историю. Все старые абсолютные/tmpпути — свидетельства прошлой машины, не обязательные пути нового runtime. Сохранённые PASS переиспользовать, не запускать build/render/inspect/hash ради статуса.

Sol6.1 Standard, усилие соразмерно: medium простое/координация, high сложное/review. ПостоянныйUltra отменён. Глобальную конфигурацию не менять. Production/VM/DB/deploy не переключать без итогового просмотра. Полная выдача обоих курсов, UI, Docker, межпрофильная cache приватность иTERMrecovery не приняты.
