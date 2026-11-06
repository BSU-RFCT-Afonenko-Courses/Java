# PL: pin-only paired CI с прошедшим Core

Изменены ровно четыре SHA literals в `.github/workflows/ci.yml`, source готов
к независимому root review. Compiler/runtime/CI checks не запускались.

## Основание и вход

По прямому свидетельству root exact Core
`4a2903e6d03d1dd489b6922d113100826913cf67` получил полный hosted CI SUCCESS,
run `41790570793`. Эта задача не опрашивала CI повторно и не расширяет это
свидетельство до PL paired PASS.

До edits PL HEAD:
`d8efb030ee6435f3c1f7d242e7388803b55b3299`.
`git status --short` был пуст; других writer по назначению root нет.

## Точная дельта

В jobs `check` и `java-delivery` согласованно заменены checkout ref и его
существующий exact SHA assertion:

| Место | Старый SHA | Новый SHA |
| --- | --- | --- |
| line43 Core checkout `check` | `830381697beea4106655965e10605cc13987199b` | `4a2903e6d03d1dd489b6922d113100826913cf67` |
| line49 exact assertion `check` | тот же | тот же |
| line113 Core checkout `java-delivery` | тот же | тот же |
| line119 exact assertion `java-delivery` | тот же | тот же |

`git diff --numstat` показывает `4 insertions / 4 deletions`, единственный
изменённый файл — `.github/workflows/ci.yml`. Diff прочитан целиком:
[pl-core-paired-ci-pin-20261104.patch](pl-core-paired-ci-pin-20261104.patch).
Собственный PL `EXPECTED_SOURCE_SHA` механизм не менялся.

Computed fixture не зарегистрирован в hosted CI: R/knitr hosted evidence
остаётся UNKNOWN. Новых engine probes/install/jobs/skips/Download checkout,
матриц или changes к tests/production/config нет. Это existing-contract paired
CI с явным computed-hosted gap, не утверждение hosted computed coverage.

## Проверки и передача

Выполнены только локальные HEAD/status reads и exact diff review.
Для простой SHA замены compiler/render/build/tests/Native/inspect/hash/network
не запускались. Source gate уже передан root; следующий шаг — root review и
один push existing PR36. Автор не делал commit/push/mainstate edits.

## Собственный модельный интервал

Agent `/root/core_yaml_types`, chat `01a1a54c-ab39-7a83-808d-a190428ff628`,
неизменённый origin turn `01a1a54c-ab6a-7412-894c-270be9b9aac3`.
Ожидаемая пара `gpt-6.1-sol/medium`; весь primary origin interval проверен:
**9 turn_context**, top-level model/effort и nested collaboration settings
соответствуют назначению — PASS. Standard назначен, hidden routing не утверждается;
globals не менялись.
[Первичное свидетельство](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/pl-core-paired-ci-pin-admission-20261104.json).
