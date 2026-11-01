# Обязательная квалификация native delivery

## До operator L: только published source preflight

`bash CI/check-projects.sh` теперь выполняет только строгие существующие
`tools/materialize-providers.py --check` и `CI/check-production-pins.py`.
Он проверяет точные published bytes/provenance/executable class и pins, не
запускает Core/C/Quarto/Platform build и не выдаёт full135/grading PASS.
Public hosted job называется проверкой providers/source pins; его green
не заменяет обязательный qualified release gate. Самостоятельные existing
student/full/student site checks сохраняют собственные гарантии и ограничения.

Published preflight выполняется **до** единственного operator L. Strict
materializer не ослаблен: L отсутствует в published package и после его
установки дополнительный файл ожидаемо отвергается. Ровно fixed L затем
принимается прежней внешней Root qualification и входит в S/G/K/RME/prior P.
Повторного vendor index после L/P в qualified фазе нет. Новый skip/accepted
flag, bypass validator, private DTO bridge или второй resolver не введён.

## После independent P: обязательные прямые Platform команды

Существующий trusted Root launcher получает актуальный независимый prior P
для точных owner/source/runtime/argv/cwd/environment и непосредственно
запускает опубликованные `build-course.py`, `check-course.py inventory` и
`check-course.py verify`. На owner выполняется один genuine native capture
с reuse existing proof, а не render на field/task. Stage выбирается **до**
RME/P, создаётся самим Platform2.5.2 после допуска и не считается immutable K.
Пример ниже — техническая форма, не разрешение исполнения и не executable DSL:

```sh
# Только с реальными буквальными путями/полным окружением из принятого Root P.
# Java author capture: existing Download deferral входит в exact E до запуска.
export COURSE_SITE_DEFER_DOWNLOADS=1
python3 -B "$PL_PLATFORM_ROOT/tools/build-course.py" \
  --source "$COURSE_SOURCE" --book tasks --instance 2026-2027-s1 \
  --output "$NATIVE_OUTPUT" --export-stage "$EXPORT_STAGE" \
  --runtime-registry "$PL_RUNTIME_REGISTRY"
python3 -B "$PL_PLATFORM_ROOT/tools/check-course.py" inventory \
  --manifest "$PRIVATE_CHECKS" --scope delivery --delivery "$NATIVE_OUTPUT/delivery.json" \
  --runtime-registry "$PL_RUNTIME_REGISTRY" --output "$PRIVATE_INVENTORY"
python3 -B "$PL_PLATFORM_ROOT/tools/check-course.py" verify \
  --manifest "$PRIVATE_CHECKS" --snapshot "$COURSE_SOURCE" --scope delivery \
  --delivery "$NATIVE_OUTPUT/delivery.json" --backend container \
  --runtime-registry "$PL_RUNTIME_REGISTRY" --output "$VERIFICATION_CONTAINER"
```

`PRIVATE_CHECKS` — штатный sibling `<NATIVE_OUTPUT>-checks.json`.
`VERIFICATION_CONTAINER` — закрытый `verification-container.json`.
Optional private-source receipt передаётся существующим `--staging-receipt`,
если именно этот режим независимо квалифицирован. Source/checks/output/stage
и runtime assets должны соблюдать прежние disjoint/currentness/ownership
условия. Фактический C command — absolute installed entry с cwd owner tasks;
Quarto→Deno преобразования также входят в exact E/P. Это не обычный public-only
маршрут: strict Core assignment admission сохраняется до первого C import.

Нет `--id`, `--scenario`, `--ready-only`, host-backend release acceptance или
подмножества работ. Нужны полный declared owner scenario inventory и existing
container receipt: `receipt.kind=delivery-verification`, `scope=delivery`,
полная `packageIntegrity.coverage` и соответствующий automatic proof; для
полностью ручной поставки допустим только штатный `not-applicable` dispatch.
Root проверяет этот существующий receipt штатным consumer. Exit0 source
preflight/inventory либо текст stdout не заменяет успешный receipt.

## Полная сохранённая обязанность

| Гарантия | Реальный owner/consumer и обязательное свидетельство |
| --- | --- |
| Source/student inventory, все135/102works | genuine Core operation/capture → C nativeEntry + whole native inventory, без фильтров |
| Hidden/non-PL facts, origins/common/A1/materialization/currentness/privacy | Core public scopes/private-first и C existing decoder/check/hash scopes; final publication fences |
| Starters, API и Gradle, ожидаемые FAIL | Existing Platform scenario_inventory/prepare_job/verify_results на всех owner scenarios; API unscored, INFRA≠student0 |
| Lawful references/alternatives/negative contract cases | Полные source-bound checks и container evidence, никакой замены четырьмя примерами |
| Java118 mixed +10oldmanual, SP7oldmanual,80/20 | Прежние metadata/scoring и closed C/Platform dispatch; новых manual/Jobs для Internal/Workspace нет |
| Итоговый release PASS | Existing successful complete delivery-verification container receipt, independently accepted Root report; до этого OPEN |

Java environment обязан сохранять `COURSE_SITE_DEFER_DOWNLOADS=1` именно для
квалифицированного author capture; это не отменяет самостоятельной проверки
финальных author/full downloads у Publisher. SP hooks/environment проверяются
отдельно, Java wrapper в SP не создаётся. Старый `CI/check-projects-candidate.sh`
с exact historical local-image contract остаётся отдельной диагностикой и не
считается текущим required release gate либо заменой P. Исторические research
документы/старый private-model pipeline сохранены как история; удалённые Core
`check.ts/materialize-projects.ts/project-checks.ts/course.json/native-run.json`
не восстанавливаются.

Actual full135, все API/scenarios/container results, ordinary final site и
qualified launcher/CI execution остаются **OPEN** до отдельного Root запуска
после финальных published providers/Platform assets, source freeze и prior P.
Синтетические shell-routing tests доказывают только фазу аргументов/fail-fast.
