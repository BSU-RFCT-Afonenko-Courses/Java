# PL: не строить выбрасываемый обычный ZIP для Workspace

Технический исполнитель Sol6.1/medium, Standard. Собственный первичный допуск по `/home/tolya/JavaCodex/java-release-20261110/java/docs/model-admission.md`, запись в своём report, сам нормативный файл не менять. Repo `/home/tolya/JavaCodex/java-release-20261110/prairielearn`, branch `codex/java-release-20261110`, проверенный base `1c41f03f83b26adfe58415b4d89958a3663fabf2`. Ты единственный writer этого repo. Пользователь одобрил самостоятельную реализацию релизного пакета, не нужны повторные вопросы. Не создавать субагентов.

Read AGENTS.md и spec/index.md, соответствующий workspace/download договор. Точная причина уже установлена audit E03: `_extensions/course-prairielearn/application/native-materialize.ts:195` всегда вызывает Download.packageArtifact(final), а workspace/includeGit ветвь200–214 возвращает другой archive и files. Предыдущий archive и mapping final.files в этой ветви не используются.

Результат ограниченного diff: обычные packageArtifact и mapping final.files выполняются только там, где их результат действительно возвращается; Workspace/includeGit строит только свой конечный комплект. Upload/file ветка сохраняет прежние bytes/имя archive, checkpoint и selection semantics; workspace сохраняет свой настоящей seed/package admission. Не переносить этот дубль в новый validator, не менять materialize count, API, UUID, tests-схемы, scoring или version pins курса.

Посмотри, какие полезные guarantees packageArtifact выполнял побочно и уже ли они обеспечены workspaceArtifact; если есть реальная потеря, укажи её и выбери существующую границу без второго ZIP. Исследование не повторять.

Новый тест для этого небольшого обратимого перемещения не требуется. Используй существующую дешёвую адресную проверку workspace/file materialization или относящийся typecheck; не запускай full tests/check, whole render/course/CI, Docker, inventories. Рецензент переиспользует твой результат без повторного запуска. Report: `/home/tolya/JavaCodex/java-release-20261110/reports/pl-workspace-archive.md`, кратко cause/change/checks/limits. Адресный commit разрешён; push/PR после независимого обзора координатором.
