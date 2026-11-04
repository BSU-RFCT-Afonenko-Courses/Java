# Редакционная очистка учебных контрактов

Собственный первичный допуск: чат `01a1a518-a1da-7f91-a440-807c1b6889c2`, агент `/root/java_j25_doc_cleanup`, исходный ход `01a1a518-a20b-7540-bb6a-470634b64d9c`. Ожидание и факт: `gpt-6.1-sol` / `low`; верхнеуровневые и вложенные параметры совпали во всём доступном интервале от исходного хода. Источник: `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T08-46-57-01a1a518-a1da-7f91-a440-807c1b6889c2.jsonl` (`session_meta`, все `turn_context`).

Удалены устаревшие обозначения из перечисленных учебных Markdown-файлов. Исторические commit, номера строк и evidence сохранены; текущие пути обозначены явно и проверены по наличию. Значения SHA256 не пересчитывались и не изменялись.

Изменённые пути относительно Java:

- `tasks/projects/objects/car-selection/contract.md`
- `tasks/projects/objects/character-stack/contract.md`
- `tasks/projects/objects/complex-values/contract.md`
- `tasks/projects/objects/customer-registry/contract.md`
- `tasks/projects/objects/patient-registry/contract.md`
- `tasks/projects/objects/rational-fractions/contract.md`
- `tasks/projects/objects/recursive-string-reversal/contract.md`
- `tasks/projects/objects/swap-object-fields/contract.md`
- `tasks/projects/objects/variadic-sum/contract.md`
- `tasks/projects/numbers/argument-sum-product/historical-contract.md`
- `tasks/projects/numbers/binary-truth-table/historical-contract.md`
- `tasks/projects/numbers/boolean-truth-table/contract.md`
- `tasks/projects/numbers/console-greeting/contract.md`
- `tasks/projects/numbers/gallon-conversion/contract.md`
- `tasks/projects/numbers/interval-membership/contract.md`
- `tasks/projects/numbers/lightning-distance/contract.md`
- `tasks/projects/numbers/lunar-weight/contract.md`
- `tasks/projects/numbers/quadratic-roots/historical-contract.md`
- `tasks/projects/numbers/radix-conversion/historical-contract.md`
- `tasks/projects/numbers/random-console-values/historical-contract.md`
- `tasks/projects/numbers/short-circuit-and/contract.md`
- `tasks/projects/control/distinct-three-digits/subject-contract.md`
- `tasks/projects/control/increasing-digits/subject-contract.md`
- `tasks/projects/control/numeric-palindromes/subject-contract.md`
- `tasks/projects/control/primes-to-hundred/subject-contract.md`
- `tasks/projects/collections/balanced-brackets/contract.md`
- `tasks/projects/collections/object-hash/contract.md`
- `tasks/projects/collections/polynomial-map-addition/contract.md`
- `tasks/projects/collections/set-union-intersection/contract.md`
- `tasks/projects/collections/views-snapshots/contract.md`
- `tasks/test-support/shared/09a0048d487f/public-scenarios.md`

Границы: только перечисленные Markdown-файлы; QMD, YAML, UUID, результаты, исходные evidence и Git history не изменялись. Сборка, тесты, render, inspect, компилятор, сеть и хеширование не запускались. Проверка: ограниченный поиск по заданным Markdown и просмотр diff. Файл `docs/release-cycle-20261110.md` не редактировался. Коммит и push не выполнялись.

Итог ограниченного `rg -n -i j25` по согласованной области: совпадений нет. Diff всех 31 файлов просмотрен; исправлены также пробелы в пяти изменённых строках.

Независимое root review (Sol6.1/high): APPROVE. Просмотрены все31diff: удалены прежние literal ID, исторические commit/evidence/границы не выданы за новый источник; currentпути явно отделены. Изменены только MD, контракты/баллы/UUID/код/данные прежние. Новых tests/render/hash не требуется для этой редакционной замены.
