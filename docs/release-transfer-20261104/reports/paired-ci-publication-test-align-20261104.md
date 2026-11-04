# Paired CI: удалить устаревшее whole-owner DRIFT ожидание

**Готов test-only candidate для root review, без local runtime/compiler/CI повторения.** PL base `20f9a4f7b481c01f7720f5ff75e50e1c70564a39`; единственный изменённый файл `tests/native-publication-sources.ts`, **+2/-9**. [Exact patch](paired-ci-publication-test-align-20261104.patch). Product Source, schemas, hashes, guards и оба workflow pins не менялись. Commit/push оставлены root.

## Подтверждённая первая причина

Сохранённый [CI evidence](paired-ci-publication-failure-20261104.json): PL41800068684 failed на line121 `assert.rejects(inspect,/OPERATION_CHANGED/)`; отдельный Installed native questions and Java/Gradle job SUCCESS. Download41800150679 failed на imported PLdf8 line117 того же fixture, вызванного `tests/ordinary-publication.ts`. Новых CI queries/reruns не делалось.

Оба хвоста меняют bytes `common/visible.txt` после успешного ordinary capture. Это не выбранный raw QMD и не наблюдённый published resource `assets/public.txt`. Accepted Core4a больше не требует whole-owner same-S: operation.guard (`runtime/operation.ts:2280–2310`) передаёт `assertSources(...,false,selectedDocuments)`, а `source-snapshot.ts:155–236` возвращается после selected input signatures/physical ancestors до whole membership/equality. Даже full currentness guard теперь не означает сверку всех owner rows.

PL `native-publication.ts:16–45` передаёт common source в genuine adapter sources, Core `validateRelease:2850–2895` сохраняет captured membership/excluded/ownedPhysical/kind policy. Он не сравнивает bytes этого project/common path со старым whole-owner snapshot; actual project bytes/roles принадлежат post-child A1. Поэтому изменение существующего regular common file само по себе больше не обязано давать OPERATION_CHANGED. Это намеренно снятая собственная гарантия, не основание вернуть hash freeze, восстановить whole-owner checks либо ослабить actual publication/physical guard.

## Выбранный минимальный diff

По явному решению root удалён весь DRIFT/rejection-only хвост **обоих** вариантов:

- Download branch больше не сохраняет отдельный before-ZIP ради obsolete source mutation, не пишет DRIFT и не требует whole-owner OPERATION_CHANGED/byte preservation после такого отказа;
- PL-only branch с тем же DRIFT/expected refusal удалён;
- `staleSourceRefused:true` удалён из result marker;
- PASS marker переименован в `PASS_GENUINE_ORDINARY_PUBLICATION`, чтобы не выдавать удалённое ожидание за Source-guard coverage.

Новых common-edit acceptance/selected-QMD drift cases **не добавляли**. Selected-source/operation guards уже проверены owner Core4a; не дублируем их в каждом extension. Type annotations/casts/runtime facade не вводились.

Сохранены все остальные существующие проверки: missing-visible и missing-hidden common source →RELEASE.SOURCE_POLICY до projection, hidden statement privacy, genuine ordinary proof/schema/adapter/document coverage, ordinary-vs-assignment refusal, incomplete expectedSources, wrong profile binding, Download validationPasses/proof-operation binding и **реальное unzip public.txt с точными public bytes**. Фабрика проектов, declared UUID/ids/source files/Native capture пути и вариант no-pl не изменены. Existing fixture status не был входом production parser; Download wrapper только await-ит runPublicationCases, workflow проверяет command rc.

## Download stale fixture companion

Download по-прежнему clean на `e0bddeb9e8574710c02f282d216e63f8932b4b0c`; CI checkout/assert PL pin всё ещё `df8bd4aad274d618a782265b1db9c63e9b9a40f9`. Local `git show` подтвердил там тот же obsolete хвост. `tests/ordinary-publication.ts` импортирует функцию прямо из `$PL/tests/native-publication-sources.ts`, поэтому изменение PL20f само по себе не меняет imported pinned fixture.

После root review/commit данного PL test-only candidate отдельный companion должен заменить **две existing PL SHA literals** Download `.github/workflows/check.yml:51,55` на exact принятый PL commit с этим alignment. Core4a/QRC pins, suite/matrix/production API не менять. Не патчить imported checkout через sed, не копировать fixture, не вводить новую suite и не отключать checks. Новый PL Source должен быть доступен remote до соответствующего Download checkout. Точный будущий SHA не выдумывается; его задаст root commit. Этот companion пока **не реализован**.

## Проверка, пределы и сохранность

Source diff прочитан, `git diff --check` PASS. Сохранённый original hosted failure является причиной alignment; нового local PASS/Native/compiler не заявляется. Как выбрал root, один следующий hosted push PL проверит изменённый existing case; successful Installed Java/Gradle сейчас не перезапускался локально. Следующие hosted/pin шаги принадлежат root после review, не исполнялись этой ролью.

Source scope ровно1 testfile; production/download/Site/Core/installed/courses/state/DB/student results не менялись. Не запускались Native/render/inspect/hash/build/CI/network/liveness probes. Отдельный Site backup план завершён в [site-backup-pr-next-20261104.md](site-backup-pr-next-20261104.md), без смешивания Source пакетов.

Own full-origin admission: 34 turn_context, строки8–4328, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5b4-2d49-7a41-ab8a-2361d59c6e58. Globals не менялись, hidden serving tier не удостоверяется.
