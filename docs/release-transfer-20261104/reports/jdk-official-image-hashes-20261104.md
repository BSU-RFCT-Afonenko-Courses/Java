# Official image: scoped byte-SHA candidate

База Platform `6491517b896b17119fd2ab44db1a2de2ff9c93b1`, до edits clean; применимые AGENTS перечитаны. Выполнен ранее принятый [bounded Source plan](jdk-official-image-hashes-next-20261104.md). Candidate заморожен после контроля; commit/push/CI/deploy/mainstate отсутствуют.

## Дельта и изменение поведения

Четыре файла, `+47/−9`:

- `images/java25-grader/grade.py`: публичный `preflight(runtime, registry=None)` всегда вызывает private `_preflight(..., verify_bytes=True)`. Только `grade()`, **после** successful existing `_admit_java_job()`, при `official and registry is None` выбирает private byte-skip. Host, direct preflight и явно переданный registry остаются с прежними checksum refusals. Public bypass flag не добавлен.
- В private helper пропускаются только full read/SHA runner, bundle files и shared files. Existing runner pin lookup, protocol/recipe membership, bundle membership и каждый `_files_any` path/file check остаются. Helper verification, роли, admission, version commands и diagnostics не изменены. Job-local source/config/copied-input/artifact checks не затронуты; это границы scope, не новое обоснование необходимости каждого check.
- `tools/prepare_native_toolchain.py` и `runtime-profiles.json`: только существующий RUNNER и две Java runnerSha256 pins. Native третий профиль по-прежнему производит existing baked view. Единожды вычисленный SHA final grade.py: `16f59402b9be796150209f48dcac83e8b3e61a8eb697c6a1e8b33103de2e2ff9`. Published image refs, history и fish pins не изменены.
- `tests/test_gradle_review_fixes.py`: расширен **один существующий** `RecipeMembershipTests.test_complete_native_pins_and_plain_profiles_remain_admissible`; нового framework/case/call-count test нет.

Официальный admitted job больше не обнаруживает изменение fixed-image bytes именно этими тремя SHA-проходами. Снимается прежняя гарантия раннего обнаружения operator/root-side patch при неизменённых pins; arbitrary official environment вне контролируемой image composition не квалифицируется. Формат/построение `executionEvidence` не изменены: это declared build-selected closure, теперь без данного per-job byte revalidation. Нельзя описывать её как новое свежее byte-attestation. Другие consumers `runtime_evidence` не изменены; performance benefit по времени/памяти UNKNOWN.

## Один pure control: реальный RED → GREEN

Команда обоих изменённых этапов:

```sh
PYTHONPATH=tests PYTHONDONTWRITEBYTECODE=1 python3 -m unittest test_gradle_review_fixes.RecipeMembershipTests.test_complete_native_pins_and_plain_profiles_remain_admissible -v
```

Сначала расширен existing case, до production edits: **RED**, exit1, outer **0.214763 s**. Три subcases runner/bundle/shared получили соответствующие прежние checksum mismatches в official `grade(registry=None)`. Это ожидаемый behavioral отказ, не preparation failure. [RED JSON](jdk-official-image-hashes-red-20261104.json), [полный log](jdk-official-image-hashes-red-20261104.log).

После final Source/pins: **PASS**, exit0, outer **0.264687 s**; unittest `Ran 1 test`, **0.110 s**. Суммарный RED+GREEN outer **0.479449 s**, общий cap10 s, retry0. [GREEN JSON](jdk-official-image-hashes-green-20261104.json), [полный log](jdk-official-image-hashes-green-20261104.log).

Для каждого из трёх stale pins existing control теперь проверяет actual `grade` routing: official + synthetic successful admission + registryNone достигает existing missing-student-source result `unsafe-submission` и сохраняет exact declared `dependencyHashes`; official с explicit registry, direct preflight и host с registryNone дают checksum refusal. Это не только проверка private helper. Existing checks трёх plain/native profiles и host compiler diagnostic также сохранились.

В unit fixture `_run`/helper/admission/identity-remapping mocked явно; registry читается через synthetic read port, student source удалён из disposable job, чтобы existing consumer завершился **до compilation**. Control доказывает routing и declared evidence, **не** реальный image, ownership/role enforcement или JDK/Gradle работоспособность. JVM/Gradle/Docker/network не запускались. Runtime image qualification и actual production mounts остаются UNKNOWN. Новый benchmark, suite/matrix или checker не добавлялись.

Final `git diff --check` PASS. [Exact four-file patch](jdk-official-image-hashes-20261104.patch). Source после PASS не менялся; требуется independent root review, а не автоматическое принятие/выпуск image.

Own primary origin `01a1a25f-b0fb-7fd2-b244-83933af77834`: все 44 доступных `turn_context` собственного полного интервала top/nested — `gpt-6.1-sol/high`. Прочитаны только model/effort metadata; env/secrets не выводились. Standard назначен; скрытый service tier не утверждается. Глобальные настройки не менялись.
