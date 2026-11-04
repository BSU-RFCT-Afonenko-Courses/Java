# Download: согласованный Core5f/PL0ebb CI companion

Готов workflow-only diff для root review. Base Download `6433918c1dbce365e440efd3b4f4e9a5169689be` уже содержит отдельно принятый ownership source snapshot; на входе clean. Изменён только `.github/workflows/check.yml`, **+4/-4**.

| Existing pair | Старый ref | Новый checkout и тот же exact assertion |
| --- | --- | --- |
| Core |4a2903e6d03d1dd489b6922d113100826913cf67|`5f4590aa4eb524830715f8020c8ff3c504197f73`|
| PL |df8bd4aad274d618a782265b1db9c63e9b9a40f9|`0ebb9f4e49201edede210b211d7f0e3ebf067412`|

[Exact patch](download-core5f-pl0ebb-paired-ci-pin-20261104.patch). На двух checkout строках также уточнены существующие комментарии: прежние «полный hosted CI PASS»/«published» не переносятся на новый ref до его фактической приёмки/публикации. Комментарии не добавляют gates или qualification claims.

QRC798, checkout actions/paths, source-head assertion, Quarto1.11.5, single existing check job/matrix и все команды/suites/env сохранены. Новый PL pin включает принятый test-only publication alignment без obsolete whole-owner DRIFT assertions, поэтому existing imported `runPublicationCases` берётся из исправленного Source вместо старого df8. Fixture не копируется/не патчится при checkout; новый runner/test не вводится. Product runtime/reader/ownership guarantee не менялись в этой pin партии.

Порядок неизменен: Core hosted результат ожидает root; PL0ebb на момент handoff не pushed. **Download push строго после подтверждённого фактического PL push**, чтобы checkout точного immutable commit был доступен. Root решает приёмку и один согласованный push/CI. Ни remote наличие, ни Core/PL/Download paired SUCCESS эта роль не проверяла и не заявляет.

Diff прочитан, `git diff --check` PASS. Source/compiler/runtime/Native/CI/hash/network/install не запускались для SHA companion. Source состояние/установленные курсы/outputs/UUID/результаты/основной state не тронуты. No commit/push.

Own primary full-origin admission: 39 turn_context, строки8–4662, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5c9-0dc1-7523-bea6-9d64848b14f8. Globals не менялись; hidden serving tier не удостоверяется.
