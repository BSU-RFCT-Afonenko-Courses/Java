# Парная адресная установка Core/PL candidate

3 ноября2026. Статус: **адресная установка принятых финальных candidates выполнена**. Подготовительная запись ниже сохранена как история; итог исполнения приведён в конце. На этапе подготовки root ещё не сообщил окончательный independently clean Core commit после двухP2; после явного разрешения установлен только332ea585. Промежуточный93310279 не установлен. PL `3a6c1c14254f1ca547c3bc2577d80007c83bdcf0` принят root и теперь установлен в обоих курсах; до разрешения installed PL оставался35496cdd. `render/inspect/assignment smoke`, materializer main, fullhash/inventory, повторUUID/Circle и субагенты не запускались.

## Primary admission

Норма `java/docs/model-admission.md` прочитана раньше, не изменяется. Ожидаемая роль `gpt-6.1-sol/high`, Standard; собственный chat `01a1a20e-59d5-7de3-a37c-f5294d684f36`, path `/root/sp_batch_adoption`, parent `01a1a142-7adc-7371-9765-4871d807dfcd`. Начало интервала сохраняется: turn `01a1a20e-5a0b-7371-bb72-1e5ac5965329`.

Первичный источник `/home/tolya/.codex/sessions/2026/11/03/rollout-2026-11-03T18-36-51-01a1a20e-59d5-7de3-a37c-f5294d684f36.jsonl`: session_meta строка1 устанавливает own ID/path; все четыре turn_context от исходного baseline прочитаны, верхние model/effort и вложенные collaboration_mode.settings.model/reasoning_effort согласованы Sol6.1/high:

| Строка | Turn |
| --- | --- |
|8|01a1a20e-5a0b-7371-bb72-1e5ac5965329|
|188|01a1a213-046a-7520-8845-04a221967e93|
|422|01a1a219-2578-7072-b372-97841717dbb8|
|471|01a1a238-d815-76a3-91b9-52e51b61c3a6|

Standard назначен root; поле service_tier отсутствует в первичных записях. Fast/Ultrafast и глобальная конфигурация не менялись. Допуск не утверждает скрытую серверную маршрутизацию.

## Существующее состояние

Адресно прочитаны manifests и сохранённые lock rows; vendor files не обходились. Java HEAD3acbb8a; единственное обнаруженное local изменение `docs/release-cycle-20261110.md` принадлежит root и не трогается. SP дерево clean после2659e8b5.

- Оба Core published base `a478d2cc0dfb19e0bebd99108815ac8ef1fe85a8`,9.3.4, без installed candidate; course-core lock297rows.
- Оба PL published base `1c41f03f83b26adfe58415b4d89958a3663fabf2`,9.0.7, installed candidate `35496cdd547d6ad2263fe171e451fb251c793c83`,270rows.
- Java Core provider дополнительно объявляет Presentation/Navigation (13/68rows) из того же Core9.3.4 release с собственными descriptor9.3.2. SP `core-ui` сохраняет04c189/v9.1.0 и прежние six scopes.
- PL target3a6c1c14 меняет только application/native-entry.ts относительно354 (functionalProfiles const). Точный Core target ждёт сообщения root; intermediate implementation/guard не переносились.

## Подготовленный разовый исполнитель

`reports/core-pl-candidate-installation.py` — внешний одноразовый скрипт, не продуктовый API/installer. Он импортирует существующие materializer функции `safe/current_expected/sha/tree_hash/valid_provider/atomic_text`; синтаксис проверен через ast.parse, скрипт не исполнен.

Команда после явного сообщения root с финальным clean полным SHA:

```sh
python3 -B /home/tolya/JavaCodex/java-release-20261110/reports/core-pl-candidate-installation.py --core-commit FINAL_CLEAN_CORE_SHA --write
```

До этого команду не запускать, включая preflight: не выбирать промежуточный Core лишь ради dry-run. `--write` за один проход сначала готовит и проверяет всё в обоих курсах до первой installed записи, затем пишет адресные изменения; отдельный preflight с повторным чтением изменяемых файлов не нужен.

Алгоритм ограничен course-core в6scopes каждого курса и PL вtasks каждого курса (14package scopes):

1. По текущему manifest устанавливает old installed ref (candidate.commit либо published commit). Git diff `--no-renames --name-status -z` old→target перечисляет реальные A/M/D только соответствующего `_extensions/<package>`; Git object bytes/mode берутся только для новых/изменённых paths. Изменение других declared packages Core запрещено.
2. Проверяет только затрагиваемые old installed paths против current lock/current_expected, regular type/mode/hash и safe ancestor paths. Неожиданно существующий added path, изменённый tracked patch или несовпадение lock останавливает поставку до первой записи. Локальные изменения сохраняются; blanket reset/reinstall не используется.
3. Копирует прежние lock rows в памяти, заменяет/добавляет/удаляет только delta rows. Полный package hash получается existing tree_hash по этим rows, без file walk. В Java Core candidate.packageHashes включает все объявленные packages: unchanged UI hashes берутся из прежних lock rows, UI bytes не читаются/не пишутся. SP core-ui вообще не затрагивается.
4. Candidate patch cumulative published base→final, в `tools/provider-patches/core-release-20261110.patch` и прежнем `pl-release-20261110.patch`. Published base/tag/version/sourceZip остаются; candidate.status=local-unpublished, baseCommit, commit, patchSha256 и packageHashes явные. Installed candidate metadata обновляется во всех declared packages соответствующего provider, включая unchanged Java UI lock metadata — так требует существующий contract `--check`; их package file rows сохраняются.
5. Все changed targets в обоих курсах проверяются до первой записи. Изменённые/добавленные regular files пишутся tempfile+os.replace с Git mode, чтобы hardlink не менял другой файл; удаляются только проверенные paths. После записи сверяются только затронутые new bytes/mode/deletions. Manifest/lock не должны измениться параллельно. Патчи/JSON пишутся existing atomic_text.
6. Result JSON сохраняет exact final refs, paths/status/scopes/count и candidate hashes. Затем читается конкретный owner diff, выполняется diff-check, свои manifest/lock/vendor/patch commits отдельно в каждом курсе; root state/docs не stage/commit. Не запускать общий materializer --check/--write.

UI/Site/QRC/Download/Platform, authored Source, goals/grades/UUID/transport остаются за границей. Native установленный SP→Java проход и последующее описание observed metrics назначает root отдельно после ready installation. Прежний отказ published Core authority документирован; guard/capsule не обходятся.

## Передача после подготовки (история)

Подготовка была завершена без Source записи и без polling/sleep; дальнейшее исполнение началось только после сообщения root с final independently clean Core commit.


## Итог разрешённой парной установки

Root сообщил окончательный Core332ea585d7b09f07c3f3886943d58a97a528078a и clean delta review; обаP2 закрыты (observed dependencies в complete/seal и structured stderr). Только после этого выполнена одна команда --write с данным полным SHA и PL3a6c1c14254f1ca547c3bc2577d80007c83bdcf0. Собственный полный интервал допуска включает пятый turn01a1a23b-5dba-7903-b424-640418924c8e, строка521; все пять top+nested Sol6.1/high согласованы, baseline не сдвигался.

Guard14scopes PASS до первой записи:13Core paths (11modified+2deleted) в каждом из6scopes обоих курсов плюс1PLpath вtasks обоих курсов. Итого158target operations:134атомарных замен и24удаления,79операций на курс. Неожиданных targets/чужих vendor правок не найдено. Postwrite проверены bytes/mode только этих targets. Ни одного неизменённого vendor file не обходили/не читали/не переписывали. Root local docs/release-cycle-20261110.md сохранён.

Точные Core paths: capture.ts; document/{assignment-contracts,assignment-producer,contracts,normalize}.ts; native-ts/early-filter.ts; runtime/{assignment-admission,assignment-declarations,assignment-execution,contracts,operation}.ts; удалены runtime/assignment-authority.ts и runtime/assignment-producer-profiles.ts. PL path: application/native-entry.ts. Полный machine-readable scope/path/status список в core-pl-candidate-installation-result.json.

Core package hash1e5c1e9ddce4398718e3b01ae2416aa4197f7678cf69e2307068d30ad8236e7f; cumulative patch SHA179538fc0aa2a53f2b463bbd6b24e8241ef37ad70c6f833b5f7c09952e79c723. PL package hashbaaaa83165a56cf081e46a0184de4a199882dd5654722a089a6d4837071f953b; cumulative patch SHA85e73500673b1ab622bdb4335bdd899beee59ba294542e1c0f857100e318bc6e. Published tag/version/commit/sourceZip не менялись; обе candidates explicit local-unpublished.

Java Core candidate содержит packageHashes всех declared packages; unchanged UI hashes вычислены из прежних rows (Presentation5006a728, Navigation3e6e8d5e). Их file rows/pins/scopes не изменены, добавлена только необходимая общая candidate metadata. SP core-ui9.1.0 полностью сохранён. Остальные provider manifests/locks равны прежним. Изменение file rows обоих Core/PL lock точно равно адресной Git delta; concrete manifest diff прочитан; git diff --check PASS обоих курсов.

Результат ограничен адресной установкой/provenance. Render/inspect/native smoke/CI/full inventory не запускались. Реальный installed SP→Java native проход, оба transport и observed metrics назначаются root отдельно; его результат здесь не заявляется.

Перед commit учтена особенность exact cumulative patch: полный cached diff-check сообщил trailing-whitespace на двух строках Core patch1778/2047. Обе являются обязательной blank context строкой unified diff, содержащей единственный пробел. Это синтаксис patch, не whitespace в установленном TS. Patch bytes не менялись/не нормализовались: оба cumulative patch каждого курса побайтово сопоставлены с exact Git published-base→target diff, PASS. Installed/provenance diff-check (исключая *.patch) PASS и cached counterpart PASS.

Installation commits (83paths каждый, root state исключён):

- java: `11b0a31c70de26eed964aa0279611097f20f8571`; оставшиеся изменения: M docs/release-cycle-20261110.md.
- system-practice: `66c4c5110b03e7ac9e9132eff6b9f0bfff0b6190`; оставшиеся изменения: clean.

Staged paths точно совпали с разрешённым списком; push/PR/CI не выполнялись.
