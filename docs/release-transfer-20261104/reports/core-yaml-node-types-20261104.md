# Core YAML nodes: остановка на подтверждении типов vendor

Результат: **Sourceplan; продуктовый код не изменён, компилятор не запускался**.
Независимая приёмка реализации не заявляется. По решению root вместо рукописного
`OriginYamlNode` сохранён пробел происхождения типов; существующий алгоритм не менялся.

## Вход и владение

- База: `0cf20ccc0a4853b704544c7252fcaf3ccb84a518`.
- Отдельный worktree: `/home/tolya/JavaCodex/java-release-20261110/core-yaml-types`.
- Ветка: `codex/core-yaml-types-20261110`.
- Scope: только `_extensions/course-core/infrastructure/project-context.ts`;
  целевые `tree(n: any)` и два `(p: any)` остаются открытыми.
- Существующие Core checkouts, mainstate, native/origins/copy/hash/roles/WeakMap/lifetime
  не изменялись. Commit, push и production-действия не выполнялись.

Прочитаны применимые Java `AGENTS.md`, нормативный вход и
`docs/model-admission.md`; в Core — `AGENTS.md`, `spec/index.md`,
`spec/plugin-architecture.md`, `docs/native-context-ts.md` и языковая политика.
Ограниченный source-пакет не требует повторного чтения всего аудита/R0.

## Первичный модельный допуск

Собственный агент: `/root/core_yaml_types`.
Собственный chat ID: `01a1a54c-ab39-7a83-808d-a190428ff628`.
Исходный turn ID: `01a1a54c-ab6a-7412-894c-270be9b9aac3`.
Назначение: `gpt-6.1-sol` / `medium`; Standard назначен.

Первичный источник:
`/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T09-43-47-01a1a54c-ab39-7a83-808d-a190428ff628.jsonl`.
Проверены собственные `session_meta.id/agent_path` и весь интервал `turn_context`
от того же исходного хода, включая вложенные `collaboration_mode.settings`:
1 запись, верхнеуровневые model/effort и вложенные model/reasoning_effort
совпадают с назначением. PASS допуска; начало интервала не сдвигалось.
Машинное свидетельство: `/tmp/core-yaml-types-admission.json`.
Отсутствие скрытой серверной маршрутизации не утверждается; globals не менялись.

## Точная source-находка

`project-context.ts` импортирует `isAlias` и `parseDocument` из
`../body-export/vendor/libraries.js`. Этот minified JS bundle экспортирует
`isAlias`, `parseDocument` и `v5`; соответствующих `.d.ts` и экспорта типов
YAML-узлов рядом нет.

`package.json`, `_extensions/course-core/package.json` и их lock-файлы
не закрепляют пакет `yaml`. Адресный поиск по `tools` и `.github`
не обнаружил manifest сборки `libraries.js` с exact YAML pin.
`yaml-LICENSE` подтверждает авторство/лицензию, но не версию.
В доступной Git history `git log --follow` для bundle показывает только
`a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8`
(`Merge pull request #67 from Afonenko-Course-Tools/perf/static-batch-checkpoint-934`);
это не доказательство точной upstream версии YAML.

Системные YAML declarations существуют у посторонних инструментов:

| Каталог | Версия из его package.json |
| --- | --- |
| `/usr/lib/node_modules/@typespec/compiler/node_modules/yaml` | `2.7.1` |
| `/usr/lib/node_modules/yaml-language-server/node_modules/yaml` | `2.8.3` |
| `/usr/lib/node_modules/ansible-language-server/node_modules/yaml` | `2.4.1` |

Эти разные версии не подтверждают происхождение Core bundle. Absolute type-import
с такого пути был бы непереносимым. Декларации другого пакета не подключались.
Не использовались `ReturnType<any>`, fake casts, `ts-ignore`, новая closed schema
или рукописная AST-форма для имитации типизации vendor boundary.

## Минимальный следующий шаг

1. Найти первичный build manifest/lock или другой проверяемый pin именно этого
   `libraries.js`, связать его с точной версией YAML. По текущим исходникам pin
   не установлен; совпадение API/имён и системная установка его не доказывают.
2. В отдельном разрешённом scope сохранить/связать переносимые declarations
   **той же версии** с существующим bundle и проверить соответствие его экспортов.
   Не добавлять зависимости или runtime guards только ради TypeScript.
3. После этого типизировать `tree` и pair callbacks реальными parsed-node/pair
   типами. Использовать существующие проверки alias/anchor/tag, map pairs,
   string key и source range; сохранить порядок обхода, ошибки, IDs и диапазоны.
   Если реальные типы требуют смены алгоритма, вынести точную дилемму на решение,
   а не расширять runtime-проверки автоматически.
4. Получить независимый root review финальной дельты и выполнить один разрешённый
   affected bundled compiler с лимитом 30 секунд, retry 0. На этой остановке
   попытка компилятора не потрачена.

## Diff и свидетельства

`git diff -- _extensions/course-core/infrastructure/project-context.ts` пуст:
продуктовых изменений **0**. Единственный новый файл worktree — этот отчёт.
Рабочее дерево от exact base сохранено для продолжения.

Не запускались runtime/Native/render/inspect/hash/full suite, установка или
скачивание dependencies/types. Компилятор не запускался, поэтому compiler PASS
не заявляется. Максимальная честная типизация остаётся открытой целью; данная
дельта не выдаёт неподтверждённую AST-форму за её достижение.
