# Штатная offline подготовка Performance summary — 4 ноября

Срез19:17 Europe/Minsk. Сначала поручена одна offline package-manager подготовка, затем прямо разрешена одна сетевая подготовка тем же manager **в остатке прежних20с**, с прежним offline failure в учёте. После успеха подготовки — один исправленный summary invocation8с. Суммарный предел summary15→23с, старый failure сохраняется с полным wall≤15с (его точное полное время неизвестно); максимальный предел setup+обеих summary попыток43с. При первом новом failure — стоп.

**Offline установка не прошла; отдельно разрешённая сетевая установка успешна. Единственный исправленный summary остановился до Native render: runtime copier отвергает штатный directory symlink, созданный Deno. Дальнейших попыток нет.**

## Подготовка и фактическая команда

Прочитаны `deno.json`, `deno.lock`, `docs/development.md`, `docs/arch-package.md` и workflow setup. В lock объявлены ровно `npm:typescript@5.9.3` и `npm:luaparse@0.3.1`. Source требует локальные `node_modules/typescript` и `node_modules/luaparse`. Существующие directories этих версий найдены в `/home/tolya/.cache/deno/npm/registry.npmjs.org/`; наличие каталогов не доказывает готовность offline dependency resolution.

План сохранён **до установки**: [plan.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/plan.json). Проверены поддерживаемые параметры существующего Deno install; использована обычная команда package manager, без собственного installer, ручной распаковки/подмены пакетов или самодельных symlinks:

```sh
DENO_DIR=/home/tolya/.cache/deno deno install \
  --no-save --cached-only --frozen --node-modules-dir=auto \
  --config deno.json --lock deno.lock \
  npm:typescript@5.9.3 npm:luaparse@0.3.1
```

К process применён watchdog19с, общий setup cap20с включая allowance оболочки. Установка закончилась **exit1 за0.117151116с** измеренного subprocess интервала; watchdog не сработал. stderr:

```text
error: npm:typescript was not found.
```

Это конкретный первый неразрешённый package `typescript@5.9.3`. Команда использовала `--cached-only`; network/download не предпринимался. По имеющемуся исходу нельзя утверждать отсутствие всех bytes пакета: на диске есть version directory, но offline resolver не смог разрешить объявленный specifier. `luaparse` далее не устанавливался и отдельной попыткой не проверялся. Обход другим manager/режимом, повтор установки и ручные ссылки не выполнялись.

Сохранены [setup-result.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/setup-result.json), stdout/stderr и исходные manifest/lock. После попытки `deno.json` и `deno.lock` побайтно прежние, `node_modules` отсутствует, `git status --short` пуст. [Postcheck](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/postcheck.json). Product Source/globals не менялись; failed исторический Performance checkout не использовался.

## Первоначальный исход offline correction, 19:11

Старый summary failure остаётся: measured CLI1.714872273с, полный предыдущий invocation≤15с. Штатная setup-попытка заняла0.117151116с измеренного subprocess интервала при отдельном cap20с; полное wall Python wrapper отдельно не измерено. Разрешённые8с corrected summary **не использованы**, поскольку setup не прошёл. Максимальные15+20+8=43с — предельные разрешённые бюджеты, не выданное за измеренный wall значение. Старые расходы не обнулены и не заменены новым baseline.

Первый новый failure сохранён, retries0. Native render/phases/coverage/HTML в этом продолжении не запускались/не получены. Статистический overhead, speedup и внутренние фазы полного курса неизвестны. Задача следующей подготовки конкретна: обычная полная поставка/доступный offline resolver cache `typescript@5.9.3` и `luaparse@0.3.1`; новая Source infrastructure для этого не нужна. Дальнейшая установка/загрузка/summary не выполняется в исчерпанном однократном поручении.

VM/Docker/network/TERM/recovery/direct-off pair не повторялись. Core/PL/другие Source и основной release state не менялись. Вечерний отчёт и session state обновлены; сообщений другим чатам нет.

## Разрешённая сетевая подготовка и corrected summary, 19:17

Следующее прямое поручение разрешило снять `--cached-only` ровно у того же обычного manager, сохранив `--no-save --frozen --node-modules-dir=auto`, exact declared packages и отсутствие Source/lock/global changes. До запуска сохранён [network-plan.json](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/network-plan.json): setup остаток19.882848884с, командный watchdog18с плюс allowance в том же общем20с. Один обычный sandbox escalation для сети/cache write допущен; auto-review отказа не было, обходов нет.

Установка завершилась **exit0,0.599202893с**. Deno переиспользовал оба package payload из cache; загрузил registry metadata `https://registry.npmjs.org/luaparse`, новых npm/JSR package archives0. Фактические package.json показывают TypeScript5.9.3 и luaparse0.3.1. Manifest/lock побайтно прежние, Git status пуст; `node_modules` — штатный локальный результат package manager. Сумма двух измеренных install subprocess intervals **0.716354009с из setup20с**; overhead Python setup отдельно не timed. [Установка](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/network-setup-result.json), [postcheck](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/network-postcheck.json).

Только после успешной подготовки запущен один corrected summary на точно том же QMD: child watchdog6с + cleanup до1с + allowance1с, внешний `timeout --signal=KILL 8s` ограничивал полный Python invocation. Полный wall измерен Bash `time`: **1.607с** (округление до milliseconds); CLI interval1.572933970с. Внешний supervisor exit0 не означает успех CLI: CLI вернул **2**, watchdog не сработал. stderr:

```text
Ссылка runtime не указывает на файл: /home/tolya/JavaCodex/java-release-20261110/performance-lifecycle/node_modules/typescript
```

Причина установлена read-only: обычный Deno install создал `node_modules/typescript → .deno/typescript@5.9.3/node_modules/typescript` и аналогичную ссылку luaparse. Это ссылки package manager на каталоги внутри собственного `node_modules`, не ручные заглушки. `src/launchers/setup.ts:30–33` копирует все `node_modules` через `copyTree(...,{materializeFileLinks:true})`; `src/sources/snapshot.ts:24–31` допускает при этом только ссылки на **файлы**, directory link отклоняет. Готовые dependencies теперь существуют, но runtime preparation несовместима с этой штатной раскладкой Deno. Это конкретная граница данного source install route, не доказательство отказа каждой поставки: существующий Arch recipe распаковывает обычные directories и имеет другой layout.

После этого первого нового реального failure **стоп**: без другого installer/linker, ручной переделки symlinks, Source fix или ещё одного запуска. Native render не начался, capability probes были, `run.json`/summary/coverage/HTML отсутствуют; phase coverage **не измерено**, не0 и неPASS. [Corrected result](https://github.com/BSU-RFCT-Afonenko-Courses/Java/blob/4254ee1f961b0d0ff6c13e1ebf46d5399bd493f2/docs/release-transfer-20261104/reports/runtime-performance-summary-correction-20261104-evidence/corrected-summary-result.json), stdout/stderr/fullwall сохранены.

Ресурсы corrected CLI: wait4 CPU user1.650302с + system0.163697с = **1.813999с**; peak RSS650968KiB (около635.7MiB). Python observer CPU0.016729с, process high-water RSS14036KiB. Пик child не сумма одновременной памяти дерева; observer RSS не прирост, его wall overhead отдельно не выделен. Это стоимость неуспешной подготовки, не успешного render/статистический overhead. Старый measured1.7149с и новый1.5729с нельзя сравнивать как speedup.

Бюджет без обнуления: старое fullwall≤15с, corrected fullwall1.607с rounded; безопасная суммарная верхняя граница **≤16.608с из summary cap23с**. Setup две попытки имеют measured subprocess total0.716354009с при прежнем cap20с. Общий разрешённый максимум43с сохраняется, не выдаётся за измеренное время. Отдельные direct/off2.947с/cap24 и grader3.216с/cap22 и исторические TERM failures не повторялись и не переименованы.

Следующий конкретный Source вопрос — совместимость существующего runtime copier со штатным package-manager layout, с сохранением реальной containment границы. Новая registry/inventory/validator framework для этого не нужна. Это только зафиксированная причина; в текущем поручении Source не меняется и retry не назначается. Реальные фазы Core/PL/QRC и полного курса по-прежнему неизвестны. VM/Docker/TERM/старые пары/другие tests не запускались. Performance tracked Source остаётся clean.

Независимый `/root/ordinary_gradle_publication_review` прочитал evidence и точную ветку copier: подтвердил корректность причины для данного layout, ресурсов и бюджетов20/8/23/43. Новых запусков/правок reviewer не выполнял. Отдельно подтвердил предел: этот failure не означает отказ всех installations или нулевое фазовое покрытие; стоимость успешного render не измерена.
