# Исправление устаревших ожиданий A3

Рабочая область: `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge`; исходный head поручения `5a28314fc8b704ef7171f33063789d7cc8ec8e79`. Изменён только `tests/native-operation-a3.ts`; production-файлы не менялись.

Основание: hosted CI run `41785524943` впервые отказал на canonical 022 из-за ожидания whole-source fence после изменения `shared/unrelated.txt`. Действующий `docs/native-operation-ts.md` явно сохраняет guards выбранных raw QMD и не обещает неизменность соседних файлов, ancestor membership или supporting scratch/include. Прочитаны весь fixture setup, соседняя последовательность и реализация complete-operation guard.

Изменения:

- Изменение `shared/unrelated.txt` теперь положительный случай. Проверяются точные ранее наблюдённые `VISIBLE_BYTES\n`, успешный возврат callback через final fence и возможность следующей current operation. Удалены два устаревших ожидания `OPERATION_CHANGED` для независимого файла; test name больше не обещает full-source fence.
- Последующее изменение supporting include `sections/statement.qmd` больше не требует blanket отказа при чтении неизменного наблюдённого `visible.txt`. Вместо этого проверяются точные captured bytes. Никаких утверждений о повторном Native capture изменённого include не добавлено.

Сохранены: отказ при изменении environment; lifecycle/expired capability; incomplete capture; поздняя подмена generated resource с отказом; последующий fresh render в исходной последовательности; отказ после изменения выбранного `index.qmd`; serialized-condition authority refusal и отказ следующей current operation после selected-QMD drift. Эти участки production guards не ослаблялись. Fixture input selection перечисляет `index.qmd`, `other.qmd`, `project/input.qmd`; supporting include не объявлен отдельным выбранным raw QMD.

Проверка ограничена статическим просмотром итогового diff. A3 — интеграционный top-level сценарий с настоящими Quarto render; отдельного применимого pure теста для этих двух ожиданий нет. Native, render, typecheck и зелёные canonical 001–021 не запускались. Результат исполнения изменённой последовательности пока не подтверждён; следующая проверка — согласованный общий CI после независимого review координатора. Коммит, push, PR-комментарии и state не выполнялись.

Собственный первичный допуск: чат `01a1a518-a1da-7f91-a440-807c1b6889c2`, агент `/root/java_j25_doc_cleanup`, исходный ход `01a1a518-a20b-7540-bb6a-470634b64d9c`. Пара `gpt-6.1-sol` / `low` подтверждена до правки и перед отчётом по всем доступным turn_context, включая вложенные настройки, от того же исходного хода. Источник `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-46-57-01a1a518-a1da-7f91-a440-807c1b6889c2.jsonl`; свидетельство `model-evidence/core-ci-obsolete-fence-fix-20261104.json` во внешней рабочей области.

## Дополнительный адресный просмотр

В оставшихся `tests/native-operation*.ts` и canonical runner аналогичных устаревших whole-source/same-S assertions не найдено. Адресный поиск охватил `tests/native-operation-pointer-race-a4c.ts`, уже рассмотренный `tests/native-operation-a3.ts`, `tests/helpers/native-operation-a3.ts`, `tools/check-canonical.sh` и вызываемый им `tools/check-core-operation-ts.sh`.

Единственный дополнительный `OPERATION_CHANGED` находится в `native-operation-pointer-race-a4c.ts:46`: wrapper подменяет private active-operation pointer во время Native inspect; тест требует отказа и сохранения чужого нового pointer. Это действующая гарантия lifecycle/operation ownership, не source-tree freeze; менять её не нужно. Helper не содержит ожиданий source mutation. Canonical runner и operation runner только запускают существующие suites и проверяют exit code; собственных same-S/whole-source утверждений в них нет. Остальные перечисленные runner suites данным узким просмотром содержательно не проверялись.

Новых правок кода не сделано; Native и другие проверки не запускались. Перед дополнением повторно подтверждён полный доступный исходный интервал Sol 6.1/low без изменения baseline.

## Приёмка координатора

Root независимо прочитал diff и соседнюю последовательность606–720: положительные случаи относятся к captured bytes, не обещают повторный capture изменённого include. Отказы selected index.qmd, поздних ресурсов, environment, private authority и lifetime сохранены. Source review APPROVE; новых запусков reviewer не делал. Commit d348213 объединён с independently approved A1 typing d8b4929 в0cf20ccc0a4853b704544c7252fcaf3ccb84a518 и отправлен в существующий PR70. Единственный снимок нового CI41787183453: in_progress на09:43, conclusion неизвестен. Это ещё не исполненный PASS.
