# PL: span только внешнего canonical

Base Source `09064082b48b1bd60b7d3910098db30138d9fed6`, ветка `codex/java-release-20261110`; входное дерево чистое. Единственный изменённый production file — `_extensions/course-prairielearn/application/source-selection.ts` (5 additions / 2 removals). Installed Java/SP/PL остаются frozen; push не выполняется до независимого medium review.

Причина подтверждена прежним saved Performance чтением `pl-canonical-performance-reading.md`: 38 181 canonical spans включали 38 161 рекурсивный вызов и 20 внешних. Из-за reassignment exported binding в `wrapSync` рекурсия также входила в wrapper. Это не повторный sourceRows Map и не filesystem IO.

Изменение: прежнее тело перенесено в private `canonicalValue`, обе рекурсивные ссылки направлены на него; публичный `canonical(v:any):string` просто вызывает helper. Прежний operation ID / внешний `wrapSync` сохраняется. Наблюдается каждый внешний вызов, а не каждый вложенный элемент; число 20 относится только к прошлому capture, не является новым лимитом API. compareKeys/codepoint sorting, Object.entries/filter undefined, массивы/join и scalar JSON.stringify буквально сохранены. Cache/index/runtime guard/schema/API изменений нет; legacy DTO cleanup не смешан.

## Адресная проверка

1. До правки выполнен existing `tests/native-verification-inventory.test.ts`: native nested source/test/reference/descriptor/provenance/contract-case records; 1 passed / 0 failed (17ms).
2. До правки сохранён canonical golden для двух настоящих public nested records из прежнего saved run `b2b5cee6-0382-4326-83e3-457b113d4562`: `public/delivery.json`, `public/infoCourse.json`. Дополнительно один маленький edge-record содержит Unicode keys `𐀀` / `\uE000`, nested null, omitted object undefined, array undefined/hole; отдельный root undefined фиксирует и значение, и `typeof`. Временный driver `/tmp/pl-canonical-wire-golden.ts`, baseline `/tmp/pl-canonical-wire-before.json`.
3. После правки тот же driver дал `/tmp/pl-canonical-wire-after.json`; отдельная команда `cmp /tmp/pl-canonical-wire-before.json /tmp/pl-canonical-wire-after.json` — **exit0**, serialized bytes совпали. Это сравнение с результатом прежней реализации на реальных records, не зеркальный callcount test. Временные данные сохранены для review, product tests не добавлялись.
4. `/opt/quarto/bin/tools/x86_64/deno test --no-config --no-lock --allow-read --allow-env tests/native-verification-inventory.test.ts` после правки — **exit0**, 1 passed / 0 failed (16ms). Тот же executable использовался до правки и для golden driver (`run --no-config --no-lock --allow-read --allow-env`). Проверка сохраняет digest binding descriptor/scenarios/exact bytes и invalid-input отказы.
5. `/opt/quarto/bin/tools/x86_64/deno check --no-config --no-lock --import-map /tmp/pl-strict-core-map.json _extensions/course-prairielearn/application/source-selection.ts` — **exit0**, только изменённый module graph; использована уже существующая known map, без нового checker/framework.
6. `git diff --check` — exit0. Все проверки суммарно заняли существенно меньше 10s; Performance benchmark не проводился.

Граница: новый capture/скорость/число новых event rows не измерялись. По конструкции приватная рекурсия больше не проходит exported wrapper; сохранён внешний span. Проверки serialization/hash смыслов не являются performance proof. SP/Java/render/inspect/full hashes/off/CPU/CI replay не запускались. Product diff не вышел за один файл и не изменил external type/API. Других owners/SourcePerf не затрагивал.

## Собственный первичный допуск

Назначение `gpt-6.1-sol/medium`, Standard. Собственный chat `01a1a1f8-6b12-74e1-afa6-2f02c812ea9a`, agent path `/root/pl_source_selection_index`, исходный turn `01a1a1f8-6b3e-79e0-8e5b-ba2e70879141`, root turn `01a1a1ee-79d1-7653-865d-9c6e1955f926`. Primary источник `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-12-54-01a1a1f8-6b12-74e1-afa6-2f02c812ea9a.jsonl`, session_meta строка1 подтверждает собственные ID/path/model provenance.

Повторно проверен **весь** первоначальный интервал turn_context строк8,181,322,460,559,641; текущий turn `01a1a2c5-3027-7c53-b61f-929062ec2ceb`. Все верхние `model/effort` и вложенные `collaboration_mode.settings.model/reasoning_effort` равны `gpt-6.1-sol/medium`; расхождений нет. Service tier не записан; отдельное подтверждение серверного tier не заявляется. Globals/normative модельные документы не менялись.

Коммит: `9c62960ff345552dad492d8cc560dc6760787ec5`. После commit `git status --short` пуст. Независимый review и решение push — координатору.
