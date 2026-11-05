# План Core/YAML, 5 ноября

> Исполнение: superpowers:executing-plans; явное разрешение реализации уже дано полным договором.

Цель: заменить acquisition code-project единственным чтением участвующих YAML; сохранить функциональные пути/роли, убрать origin и собственные immutable source fences.
Спецификация: /tmp/java-release-discussion-20261105/EXECUTION-BRIEF-20261105.txt; договор потребителей: coordination/core.json.
Изоляция: /tmp/java-release-work-20261105/core, exact base010ba917.
Ограничения: русский текст; profiles только actual composition; Native реальные QMD; никаких treecopy/synthetic/markers/fakeSHA; один writer файла.

### Задача 1: loader и договор путей
- [ ] RED tests/course-project-loader.ts: два code leaf, inherited local starter/tests/components и explicit shared config, atomic override без base, selected profile precedence/.local/format/include, missing при наличии decoy, каждый YAML один read.
- [ ] Запуск: env XDG_CACHE_HOME=$PWD/.cache/codex-tools QUARTO_RUN_NO_NETWORK=true quarto run tests/course-project-loader.ts; сначала ожидается отказ старого request/API.
- [ ] Реализация contracts/project.ts, infrastructure/project-context.ts, artifacts/plan.ts/project.ts, runtime/assignment-roles.ts. Выходной contract содержит только resolved physical paths; callbacks и role privacy сохраняются.
- [ ] Проверка того же regression + compiler affected graph, commit явных файлов.

### Задача 2: runtime cleanup
- [ ] Отдельный исполнитель владеет runtime/operation.ts, source-snapshot.ts, assignment-admission.ts, assignment-execution.ts, assignment-provenance.ts, infrastructure/invocation.ts, contract-carrier.ts и собственными tests.
- [ ] Удалить whole source tree snapshots/fences и invocation/implementation signatures; сохранить actual owner source inputs/native lifecycle/resources/private roles.
- [ ] Интеграция loader request directories и provenance без sourceRows; compiler, различающие проверки, commit.

### Задача 3: замыкание файлов и независимая проверка
- [ ] Уточнить единственного writer Download и согласовать Core→PL→Download contract; при отдельном широком graph передать интегратору без смешивания metadata task.
- [ ] Ограниченная карта прямых/косвенных вызовов и измерение affected loader (YAML reads, inspect, copy, wall); не объявлять ускорение курса.
- [ ] Независимый Sol6.1/high review exact diff; исправить важные findings, проверка полного первичного исходного модельного интервала, передача commits/checks/limits.

Фокус review: selected profiles без самовольных defaults; override path сбрасывает config base; includes собственная база; приватные physical aliases/hardlinks; actual Quarto metadata unaffected.
