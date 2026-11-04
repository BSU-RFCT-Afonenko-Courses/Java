# Независимый review Core download defer

Диапазон: `0478458a71f3acf34ad6d9983ac11023783dde3d..830381697beea4106655965e10605cc13987199b`. Verdict: **ACCEPT**, actionable findings не обнаружены.

- `_extensions/course-core/runtime/assignment-execution.ts:165`: `render` по-прежнему требует live assignment scope, child token и точный owner render/output/profile argv; общий command guard остаётся на `:195–197`.
- `_extensions/course-core/runtime/assignment-execution.ts:198–212`: flag устанавливается в копии runtime environment после прежней проверки caller env. Caller override продолжает отвергаться; token/profile validation и `clearEnv: true` (`:215`) сохранены. Исходные env/argv не мутируются. Inspect/Pandoc и ordinary caller без assignment scope (`:136`) новую ветку не получают.
- `_extensions/course-core/runtime/assignment-execution.ts:212`: использован действующий точный signal `COURSE_SITE_DEFER_DOWNLOADS="1"`. В опубликованном Download `c81df7364dd1fbbd07a0e54d67714b3d66f9695e`, `_extensions/project-download/entrypoints/post.ts:3`, он завершает только post до `finish`. Pre/handler/late filters, ordinary author/student projection, complete/seal и публичные API этой дельтой не меняются; fake complete и подавления Core error нет.
- `tests/assignment-download-deferral.ts:24–85`: pure regression содержательно проверяет qualified render, nonrender/ordinary paths, отказ caller override и render без token, сохранение env/token/profile/clearEnv и выход из scope. Самостоятельно тест не запускался; RED/GREEN учитываются только как ранее предоставленные evidence автора.

Проверены diff и ближайший qualification/env код, существующий Download post и сохранённый lifetime diagnosis. Новых tests/check/render/inspect/hash/install/SP попыток не выполнялось; Source не менялся. Этот review подтверждает корректность ограниченной дельты; успех следующего actual seal/PL delivery ещё не установлен.

Допуск: собственное назначение reviewer — Sol6.1/medium, Standard по назначению root. Полный чужой primary interval не аттестован; серверный service tier не утверждается. Глобальные настройки и нормативные документы не менялись. Создан только этот отчёт.
