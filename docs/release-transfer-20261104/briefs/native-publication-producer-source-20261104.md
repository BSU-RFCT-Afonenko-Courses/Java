# Native publication: Core/PL Source owner

## Authority/spec

User4ноября: продолжать после отчёта, без ограничения15:00; глубокая переделка собственных contracts без обратной совместимости, главное чистая архитектура/согласованные интерфейсы. Spec — [approved design](../reports/native-publication-clean-contract-20261104.md), включая finaldelta; [final independent DESIGN APPROVE](../reports/native-publication-clean-contract-review-20261104.md). Предыдущие3findings+CLIrename закрыты вdesign; implementation должен подтвердить смысл, не переименовать proof. Учебные цели/баллы, UUID/nativeIDs/results/student files и production не меняются. Full PLcourse delivery/Platform/Gateway, broad Core currentness и Native cache policy вне выбранного slice.

## Изоляция/владелец

Single high Source writer `/root/pl_typed_materialization`, Sol6.1/high, Standard назначен; собственный первоначальный admission сохранять, globals не менять, не делегировать. Existing isolated Core `/home/tolya/JavaCodex/java-release-20261110/core-native-bridge` exact5f4590aa4eb524830715f8020c8ff3c504197f73 (linked worktree), clean приrootпроверке. Existing release PL `/home/tolya/JavaCodex/java-release-20261110/prairielearn` exact4d39a6648d2816bab9a372877deded3320ff35e1, clean. ПодтвердитьHEADпередSource; originalCore dirtyreject и Platform4dirty не трогать. Новый worktree не нужен. applicableAGENTS/spec/русскоеязыковоеправило применяются; latest user supersedes compatibility.

## Source scope и порядок

Только Core+PL production/API/docs/существующие affectedfixtures из таблицы design. Core: release/validate.ts semantic common extraction, release/publication.ts actual typed owned view/data builder, runtime/operation.ts shared existing lifetime/acquisition, public.ts и current docs/spec. PL: native-publication.ts, native-input.ts, native-business.ts + существующие consumed declarations guards/parsers, public.ts; CLI breaking rename inspect-grading→inspect-publication, descriptor key/currentdocs/callers/существующие relevant fixtures. Ни Download/Site/Performance/курсовыеSource/state/audit не править.

Сначала выведи реальные canonical Core interface/type exports и typed PL signature без temporarystubs/handwritten fake schemas. Запиши короткий API handoff `reports/native-publication-api-handoff-20261104.md`: names, signatures, actual sourcepaths и dataownership/lifetime; send root + consumer owner после назначения. Это actualtypedexports, не второй независимый DTO. Затем реализация semantic validation/direct business inspection с закрытой type chain (assessment actualschema, rawconfigunknown до existingparse, extra metadata сохранены). No any/fake casts/ts-ignore, ReturnType<any> не покрытие. Ни legacy ordinary overload, ни новая proof/adapterregistry/cache framework не нужны. Full course-export API остаётся только для своего реального отдельного режима.

Source timebox90min. Пофайловые задания design покрывают реальные API/CLI/docs/tests. Если actualscope выходит за оговорённый semantic slice — сообщить точныйgap, не молча менять педагогические/data contracts. Другая команда реализует Download/Site и читает actualexports; своевременно предупреждать об изменениях API, не создавать несовпадающие копии типов.

## Стоимость/выход

Ни compiler/tests/Native/render/inspect/hash/build/install/network/DB/production сейчас не запускать. Root организует ОДИН combined affected compiler cap20s/retry0 только после заморозки обоих Source owners и отдельную ограниченную полезнуюприёмку. Не повторять oldCI/R0–R6/wholecourse. Можно адаптировать существующие useful fixtures; proof/receipt/count-only assertions удалить без replacement counters. Сохранять historicalfailedcaptures/docs, не переписывать evidence.

До независимого Source review/приёмки не stage/commit/push. Отчёт `reports/native-publication-producer-source-20261104.md`, patch/evidence; exact changedfiles, смысл реально удалённых gates и честные оставшиеся costs/limits. Mainstate/audit пишет root. Каждые10–15min только существенный milestone/развилка; Source preliminary не объявлять готовым coherent stack без consumers.
