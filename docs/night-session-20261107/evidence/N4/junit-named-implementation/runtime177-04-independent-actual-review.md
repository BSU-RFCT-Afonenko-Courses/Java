# Независимый фактический review runtime177 attempt04

**CLOSED FAIL/PARTIAL. State11:1PASS+1FAIL+9ERROR; Authority3 и Named2 NOT_RUN. Composite200 не принят; route B HOLD.** Exact source binding5e5c и reuse прежних23compiler/initializer12 сохранены. Причина внутренних отказов пока не установлена: общий missingreports и три inner output_limit не заменяют отсутствующий producer stdout/stderr.

Own primary admission проверен от неизменных original chat `01a1b438-e54e-7811-b81a-698c524291cb` и turn `01a1b438-e592-7131-916b-86ead4aadf57`: все верхние/вложенные Sol/high, Standard; `runtime177-04-independent-actual-admission.json`.

## Полный actual outcome

Непосредственно прочитан весь combined unittest log19542B и metadata. Exit1,stop_reason=null,captured=observed19542,outercap1MiB не достигнут. Unittest `Ran 11 tests in 51.596s`, `FAILED (failures=1, errors=9)`. Process elapsed51.826725052s при effective409.910060335s≤420s; внешнего timeout/signal/outputcap нет.

| Method | Actual |
| --- | --- |
| test_diagnostic_array_quota_precedes_allocation_and_preserves_delegate | ERROR |
| test_diagnostic_finish_freezes_pending_commit | ERROR |
| test_diagnostic_finish_freezes_pending_end | ERROR |
| test_diagnostic_one_use_direct_foreign_and_revoked_grants | ERROR |
| test_diagnostic_record_quota_precedes_allocation | ERROR |
| test_hostile_records_are_bounded_without_user_code | ERROR |
| test_parent_scope_depth_and_revoked_ancestor | ERROR |
| test_real_parallel_workers_keep_unique_leaf_values | ERROR |
| test_stale_bridge_is_tool_failure_without_student_zero | FAIL |
| test_stale_projection_schema_guard_and_origin_refuse_before_junit | ok |
| test_stale_reader_callsite_never_exposes_record_fields | ERROR |

Три inner output_limit относятся hostile_records/scope/parallel. Все пять diagnostic cases и stale_reader завершились ERROR `JUnit execution did not produce complete reports`. Stale_bridge FAIL: ожидался `Trusted check raised an unexpected exception outside student code`, получен `JUnit execution did not produce complete reports`. Этот другой GraderError не принимается за ожидаемый negative.

Единственный PASS — stale_projection_schema_guard_and_origin_refuse_before_junit: exact frozen assertions306–318 проверяют три ожидаемые startup refusal strings (`Stale projection schema`, `Invalid projection admission`, `Stale trusted class`) и launchcount3. Это ограниченное положительное свидетельство исполненных assertions; оно не превращает generic missingreports остальных cases в PASS.

Authority3/delegates/expiry и Named2 вообще не запускались: нет их method logs/metadata/outcomes. Fouraudits, HMAC/UID/score, worker/API reporter9101/9201, inactiveleaffatal и пять diagnostic qualifications не приняты. FINISHED/RETIRED/wrongowner остаются structuralonly.

## Bindings, reuse и source-after

Прочитаны весь executor199lines, source diff03→04, final source peers/finalargv/budget/binding. SHA executor `1cb44c79956c156e70ecdaf09bda866db2462ab68ccf54522f122f61d82fb6ce`; binding `6f849e595c393620398ea5822ce753cf353fe0d19000b07a7e3664316bf4d74a`. Все18 bound artifact records SHA совпадают. Actual state argv/requested_unit/cwd/env/tool/input limits точно равны прежнему immutable runtime request и принятому remap snapshot; unit flags/assertions не менялись.

Reuse23 tuples буквально равны прежнему independent scoped report SHA `942742529c53d888df29d07d5da58c6f1ffb7b4225466e514e4cb43e266e733d`. После terminal независимо перепроверены их metadata/log/source inputs и все144classfiles с полными directory/root modes/membership/SHA/bytes. Initializer12 receipt/log immutable, повторов compiler/initializer нет. Прежние23static/initializer acceptance сохранены; новые runtime failures их не переименовывают и не доказывают runtime.

Postterminal independently прочитаны все349files каждого03snapshot,04snapshot и canonical live: SHA/bytes/modes точно5e5cd2 map; обаsnapshot имеют exact membership349 безextra. Source-manifest SHA `cf6c56f9f11437d1eed5a6cf046bed951121a271aa806c7e75a02ea696ca7305`. Current113R11zero/historical6=952 whole119FAIL остаются scoped strict, не wholePASS.

Primary N0 platform39462 terminal failed exit1 @06:16:56.496Z,duration52.280694475s;results end06:16:56.476906Z. Pinned executor finally196–197 drain/save остаётся под heavy.lock; accepted493 subreaper/reap/TERM15/KILL5/неотпускаемый HOLD прочитан, возврат primary подтверждает cleanup. Nonzero185 бросает до afterguard189/finalguard193: отсутствующий source-afterreceipt не придуман. Его gap закрыт независимым postterminal full349readback выше.

Budget:carry2133 послеconservativespent42,outer1000,groups430/235/310,cleanup20inside,global07:29:13Z. Фактический state процесс51.827s; весьпакет52.320s. Номинальные177runtimeJVM/18jobs и205cumulative — source-derived ceilings; per-child argv/counttelemetry не сохранена. Подтверждены11executed unittest methods,1pass/1fail/9errors и5unrun, не177measured JVM и не18successfulgradejobs.

## Граница diagnosis

Raw outer log содержит полные Python tracebacks, но внутренние producer stdout/stderr/HMAC/XML/private score и TemporaryDirectory job artifacts отдельно не сохранены. Нельзя по `missingreports` вывести причину в Java/JPMS/adapter/guard/recursion либо заявить прочитанные raw payloads. Отдельный approved capture может дать эти сведения; данный review новых запусков не назначает. Прежний initializerJSON4567B действительно сохранён в другом immutable receipt и здесь только reuse.

Рецензент записал только runtime177-04-independent-actual-review.md/json/admission, не менял product/controlauthor/refs/old03reports и не запускал checks/imports/AST/JVM/versions/Docker/network.
