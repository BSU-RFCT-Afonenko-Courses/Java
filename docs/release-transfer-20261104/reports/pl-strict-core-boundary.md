# Строгая граница PL native-entry ↔ Core

3 ноября 2026. Выполнена первая ограниченная партия из `typescript-any-audit.md`.

## Исходники и собственный допуск

Единственный изменяемый Source — `/home/tolya/JavaCodex/java-release-20261110/prairielearn`, исходный HEAD `3a6c1c14254f1ca547c3bc2577d80007c83bdcf0`, исходное дерево чистое. Прочитаны `AGENTS.md`, `spec/index.md` и общий `core/docs/russian-language-policy.md`. Core `/home/tolya/JavaCodex/java-release-20261110/core` прочитан только адресно: HEAD `f10c4e6527b1834651d59a95a0ef4373357cd8f0`, дерево оставлено чистым.

Назначение и собственный первичный допуск: `gpt-6.1-sol` / `high`, agent `/root/pl_strict_core_boundary`, chat `01a1a251-0477-7f12-9024-5b333092fd7f`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Первичный файл `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T19-49-40-01a1a251-0477-7f12-9024-5b333092fd7f.jsonl`: строка1 `session_meta` подтверждает собственные ID/path и provenance `gpt-6.1-sol`; все `turn_context` собственного интервала (строка8, turn `01a1a251-04ad-7d61-a6b8-be0b5e0fc3f3`) имеют top-level `model=gpt-6.1-sol`, `effort=high` и вложенные `collaboration_mode.settings.model=gpt-6.1-sol`, `reasoning_effort=high`. Расхождений нет. Серверный service tier запись не удостоверяет. Глобальные и нормативные model-admission документы не менялись.

## Техническое решение

До правок координатору сообщён и им принят переносимый type-only import `course-core/public.ts` и `course-core/capture.ts`. Адресный checker разрешает namespace `course-core/` на настоящий выбранный публичный Core через временный Deno import map. Принимаются Source repo, installed owner с обычным или namespaced extension layout, либо сам Core extension directory. Абсолютного Source import в product-коде нет; типы не копируются в новый declaration/API contract.

Runtime path discovery и динамические импорты остаются прежними. Результат динамического импорта имеет явную аннотацию настоящего модуля, без `as typeof import(...)`, двойных casts и подавления диагностики. Это статическое описание уже выбранной composition: типы сами не доказывают runtime identity или live authority; прежние Core gates/parse/identity/currentness проверки продолжают отвечать за них. При проверке другой composition необходимо передать checker её фактический Core.

`PublicCore` теперь `Pick<typeof CorePublic, ...>` существующих экспортов. `NativeAdapterValidationInputSchema`, inventory, identity и prepared schema сохраняют настоящие output types. `prepareNativeAdapter` использует существующий pending expectation и нужную часть настоящего prepared lease. PL correlation request получил собственный точный тип со ссылками на Core identity/prepared inputs; wire-поля не изменены.

`native-entry` проверяется по реальной capture signature: reservation и assignment preparation, contextual scope/lease, descriptor, capture command, assert и complete operation. Проверенный descriptor удерживается в локальном конкретном типе, callback owner config остаётся `unknown`. В собственном publication helper сохранены generic operation/result и минимальный существующий publish/discard handle; это не второй Core contract и не источник authority.

В `native-input` убрана локальная стирающая output schema сигнатура. После прежнего parse сохраняется `NativeAdapterValidationInput`, а ещё не разобранная PL metadata проекция имеет `Record<string, unknown>`. Прежняя проверка закрытого набора metadata slots сохранена без cast.

## Изменённые Source-файлы

- `_extensions/course-prairielearn/application/native-entry.ts`;
- `_extensions/course-prairielearn/application/native-adapter.ts`;
- `_extensions/course-prairielearn/application/native-input.ts`;
- `tests/native-core-boundary-types.ts` — статическая совместимость реального producer и consumer, exact outputs с отдельным отказом `any`;
- `tests/native-core-typecheck.ts` — небольшой адресный `deno check` runner по существующему Quarto/Core pattern, четыре отрицательных compiler cases во временном каталоге.

## Проверки и результаты

Штатный bundled checker: Quarto1.11.5, Deno2.7.14 / TS5.9.2. Не запускались render/inspect/hash/build/full suite/install/push и существующие runtime suites.

1. Адресный `deno check --no-config --no-lock --import-map /tmp/pl-strict-core-map.json` для `native-entry.ts`, `native-adapter.ts`, `native-input.ts` с фактическим Source Core и штатным Quarto `DENO_DIR`: exit0.
2. `quarto run tests/native-core-typecheck.ts ../core`: exit0. Положительно проверены реальный product import graph от native-entry, exact parse-output types, producer→PublicCore assignment и сам checker.
3. `quarto run tests/native-core-typecheck.ts ../java/tasks`: exit0. Тот же runner выбран для одной existing installed composition Java/tasks с `_extensions/Afonenko-Course-Tools/course-core`; новых install/copy/pins нет. Положительный граф и все четыре отрицательных случая прошли в том же смысле, что для Source.
4. В каждом из двух запусков: `PublicCore.withPendngNativeOperation` → ожидаемый `TS2551`; `scope.withPreparedOperaton` → `TS2551`; assignment preparation с `assessmentId` вместо `id` → `TS2322`; prepared descriptor с `projection:'full'` вместо `student|author` → `TS2322`. Checker требует ненулевой exit и конкретную ожидаемую диагностику; это не подавления `ts-expect-error`.
5. `/opt/quarto/bin/tools/x86_64/deno run --no-config --no-lock _extensions/course-prairielearn/application/native-entry.ts`: exit0 **без import map и без IO/child permissions**. Вызов gate не выполнялся: это только новый контроль стирания type-only imports при inert load; runtime-потребителю namespace mapping не требуется.
6. `deno fmt --check` для пяти изменённых TS-файлов: exit0; `git diff --check`: exit0. Последний просмотр diff подтверждает прежние ordering/path/guard/callback lifetime.

Предварительные неуспешные попытки были исправлены до приёмки: bundled `deno check` не поддерживает `--cached-only`; прямому Deno нужен существующий Quarto cache, иначе недоступен JSR manifest. В первом черновике runner исправлено использование `Deno.FileInfo.isFile` как свойства; итоговый runner сам входит в положительный typecheck. Успешные runtime проверки не повторялись.

## Остатки и пределы

Это первая партия, не завершение типизации стека. Сохранены последующие отдельные границы аудита: candidate JSON/observed-owner decoder; `native-adapter` внутренний state и operation delivery methods; `native-course` handoff/materialization/export; checked selection/feedback descriptor; provider loaders. В частности `prepareNativeCourse` пока принимает собственный старый `any` input: эта партия доказывает точный объект Core/operation/descriptor на стороне native-entry, но не полную дальнейшую цепочку writer/export.

Core source и global/normative документы не редактировались; scoring, public wire, authority, lifetime и production selection не переключались. Релиз10ноября09:00Minsk остаётся под итоговым просмотром координатора.

## Source commit

Commit `f9c00aed6de610dce7d9b7cb6e50c28ebfb9573e` — «Сохранить строгие типы Core на native-границе PL». Ровно пять перечисленных Source-файлов, `275 insertions(+), 52 deletions(-)`. После commit PL status чистый, `git diff HEAD^ HEAD --check` exit0; Core остаётся `f10c4e6527b1834651d59a95a0ef4373357cd8f0` с чистым status. Этот отчёт находится вне PL Source repo.
