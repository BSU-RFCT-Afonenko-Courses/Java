# SP: ограниченная integration проверка общего Site 6.0.1

Текущий результат: **маленькая existing mixed fixture PASS** после разрешённого root исправления `XDG_CACHE_HOME`. Первая actual Native attempt и её Sass KV отказ сохранены ниже. Суммарная actual wall двух Site6 attempts — **17.151281199s** при общем cap60s; Source и inputs не менялись. Полная SP выдача не квалифицирована.

## Выбранный owner и граница

Root выбрал общий Site `6.0.1` из existing `site-java` exact `d16f2322c1da3c96c2e93d32225f5428ae347756`. Отдельный legacy `site-sp` backport остаётся невыбранным и сохранён без commit/push. Этот запуск отвечает только на вопрос о маленькой mixed HTML/Reveal + QRC composition выбранного общего owner; полный SP курс, профили student/full и installed release не квалифицируются.

Исполнитель `/root/site_sp_native`, собственный chat `01a1a527-b7f3-7aa0-b1d7-735711254e02`, исходный ход полного интервала `01a1a527-b819-7bf1-af20-d26fd3269eb5`. Primary `/home/tolya/.codex/sessions/2026/11/04/rollout-2026-11-04T09-03-26-01a1a527-b7f3-7aa0-b1d7-735711254e02.jsonl`: session_meta line1 подтверждает собственные chat/path; все три turn_context от origin (lines8,277,344) согласованы top-level и nested `gpt-6.1-sol/high`. Итог PASS, [свидетельство](sp-shared-site6-integration-evidence-20261104/model-admission.json). Standard назначен; скрытая server routing не удостоверяется. Globals не менялись.

Прочитан exact existing `site-java/tests/site-web-selection.ts` и его `command-probe.ts`; Source не редактировался. `site-java` и `qrc` были чистыми до и после attempt. QRC source `qrc` подтверждён на `8732dbbe0d8502f8b6a77049e27907ae8a66e46a`, descriptor `3.2.0`; Quarto `1.11.5`. В установленный SP или toolchain ничего не добавлялось; существующий fixture копировал расширения штатным local `quarto add` только в собственный temporary project.

## Фиксированный existing case

Existing fixture задаёт один child `a/slides` через `{path: a/slides, to: default}`, selected `index.qmd` и `talk.qmd`; у индекса HTML идёт первым перед PDF, у talk выбран Revealjs. Root и child имеют QRC namespaces. Existing assertions ожидают:

- один native child argv `render . --to default` и отсутствие document inspect fanout;
- один body каждого child документа;
- circular HTML → Reveal `talk.html#/sec-slide` и Reveal → HTML `index.html#sec-web`;
- resolved root reference, mounted QRC target `a/slides/talk.html` и отсутствие PDF.

Это fixed existing input/expectations. Первая Site6 attempt остановилась до assertions; разрешённый corrected run ниже завершил их с PASS.

Выполнен ровно один corrected integration run, cap60s/retry0:

```sh
timeout --signal=TERM --kill-after=5s 60s env \
DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache \
DENO_NO_UPDATE_CHECK=1 DENO_NO_PACKAGE_JSON=1 QUARTO_RUN_NO_NETWORK=true \
QRC_PROVIDER=/home/tolya/JavaCodex/java-release-20261110/qrc \
/opt/quarto/bin/tools/x86_64/deno run --cached-only --no-config --no-lock \
--import-map=/home/tolya/.cache/quarto/deno_std/run_import_map.json \
-A tests/site-web-selection.ts
```

Cwd — `/home/tolya/JavaCodex/java-release-20261110/site-java`. Имеющийся cache/import map сохранён, новые зависимости не устанавливались. Новый compiler не запускался: selected Source6 не менялся и общий owner был уже принят отдельно. Прежний compiler PASS backport не выдаётся за эту integration проверку.

## Первый actual Native результат

**Exit1**, timeout не исчерпан. Source-repository QRC preparation прошла: выполнены три local add (Site, QRC root, QRC child), после них fixture вошла в native root render. Полный [mixed-fixture.log](sp-shared-site6-integration-evidence-20261104/mixed-fixture.log) содержит:

```text
course-site: Планирование корня: … — готово (0.8 с)
course-site: Проверка аудиторий: … — готово (0.0 с)
course-site: Планирование компонента: …/a/slides — готово (0.9 с)
course-site: Проверка аудиторий: …/a/slides — готово (0.0 с)
course-site: Рендер: …/a/slides — начало
[1/2] index.qmd
ERROR: unable to open database file
```

Точный первый stack: `Deno.openKv` → `sassCache` (`/opt/quarto/bin/quarto.js:80229`) → `compileWithCache` → `compileSass` → `resolveQuartoSyntaxHighlighting` → `runPandoc`. Child render завершился кодом1, Site сохранил исходную Native ошибку и остановился; интервал render в Site progress — 2.3s, это не общая стоимость fixture и не benchmark.

Наблюдался **один вход в child render** `a/slides`, остановленный на первом из двух selected документов. Raw trace/семантические завершающие assertions не достигнуты. Нельзя объявить успешной одну completed child command, actual argv/default assertion, отсутствие runtime document inspect, HTML/Reveal bodies/assets, QRC circular links или отсутствие PDF. Их результат **UNKNOWN**. Путь самой Sass KV database из этого stderr неизвестен; причина не приписывается Source Site или Core и не заменяется догадкой о формате.

Temporary fixture `/tmp/course-site-web-selection-d7135a4112ba8fb0` удалена собственным existing `finally` runner. Полный первый stderr/progress/stack сохранён указанным внешним log. Cache repair, смена XDG, повторный add/render, другой fixture или дополнительная матрица не запускались.

## Связь с прежней prep attempt

Ранее legacy attempt передавала в `QRC_PROVIDER` напрямую installed extension directory и остановилась в `quarto add` с `Found 0 extensions / Invalid extension`, **до любого native render**. Её [исходный log](sp-site-native-render-evidence-20261104/mixed-fixture.log) и [отчёт](sp-site-native-render-implementation-20261104.md) сохранены. Corrected run использовал целый уже имеющийся repository root `qrc`; прежний setup отказ не является продуктовым Native Source failure и не скрывается за новым результатом.

Для выбранного shared Site6 первая actual Native attempt зафиксирована отдельным Native Sass KV отказом. На момент этой первой передачи integration PASS не был получен; последующее отдельное разрешение root и corrected результат приведены ниже. Source-only SP миграция пяти `{path,to:default}` records остаётся совместимой по договору, но ни её полная курсная выдача, ни публикация этой attempt не подтверждены.

Source6, legacy worktree, course providers/installed, production и mainstate не изменялись этой ролью; commit/push отсутствуют. Дальнейшее решение принадлежит root.


## Разрешённая коррекция cache environment и результат

После первого отказа root прочитал Source Quarto1.11.5: appdirs/xdg cache (`quarto.js:36511+`) и Sass cache (`:80229`, `:87869`). У первой attempt `XDG_CACHE_HOME` не был задан, поэтому default HOME cache находился вне writable roots этой роли. Это установленная cache preparation причина; прежний отказ не считается продуктовым Source failure. Root разрешил исправить только этот environment на существующий writable `XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache` и повторить тот же existing input в пределах общего остатка60s. Наличие каталога и `test -w` подтверждены до команды; cache cleanup/repair не выполнялись.

Actual wall первой Site6 attempt получена из собственного primary `CommandExecution`, process3926, line304: `6s + 999969166ns`. Остаток — `53.000030834s`. Corrected timeout выбран48s, прежний kill-after5s сохранён: даже при полном ожидании принудительного завершения граница составляет53s. Никакого дополнительного разрешения времени за пределами исходного60s не вводилось.

Вторая Site6 attempt отличается только environment cache path и timeout:

```sh
timeout --signal=TERM --kill-after=5s 48s env \
XDG_CACHE_HOME=/home/tolya/JavaCodex/.cache \
DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache \
DENO_NO_UPDATE_CHECK=1 DENO_NO_PACKAGE_JSON=1 QUARTO_RUN_NO_NETWORK=true \
QRC_PROVIDER=/home/tolya/JavaCodex/java-release-20261110/qrc \
/opt/quarto/bin/tools/x86_64/deno run --cached-only --no-config --no-lock \
--import-map=/home/tolya/.cache/quarto/deno_std/run_import_map.json \
-A tests/site-web-selection.ts
```

Те же cwd `site-java`, exact Site d16, QRC873, fixture и default inputs. Source edits, установка новых зависимостей, очистка cache, изменение Quarto flags/форматов/профилей отсутствуют. Local add внутри синтетического fixture использует прежний existing runner. Дополнительных compiler/Quarto probes и третьей Site6 attempt не было.

**Exit0, PASS.** [Полный corrected log](sp-shared-site6-integration-evidence-20261104/mixed-fixture-corrected-xdg.log):

```text
PASS native HTML-first default, nested QRC, circular HTML/Reveal links, одна child команда и один body на документ
```

Фактически завершились все existing assertions, читавшие текущие output files и command trace:

- `counts.txt` содержит ровно `Slides` и `Website`: по одному body каждого child документа.
- Trace содержит ровно **одну** child render команду, actual argv `render . --to default`.
- Все observed inspect commands обращаются к project paths; `.qmd` inspect fanout отсутствует.
- Mounted HTML index содержит `href="talk.html#/sec-slide"`, mounted Reveal output содержит `href="index.html#sec-web"`.
- Root HTML не содержит `qrc-unresolved`; actual mounted QRC target `talk:sec-slide` имеет page `a/slides/talk.html`.
- Mounted `a/slides/index.pdf` отсутствует: native HTML-first default не создал дополнительный PDF.

Raw trace/HTML существовали в собственном temporary fixture во время assertions; existing `finally` после PASS удалил fixture. Здесь они не выдаются за отдельно сохранённые artifacts. Сохранены exact неизменённый existing runner в site-java и внешний PASS log. Эта fixture не включает Core/Download, поэтому selected-source Core catalog guards, S6/noDownload, privacy/full role projection и freeze/cache hit ею не квалифицируются. Actual SP course metadata сама по себе не выдаётся за полный course PASS.

Собственное primary tool metadata corrected process36223, line375: actual wall `10s + 151312033ns`. Сумма обеих Site6 attempts — **17.151281199s**, меньше общего cap60s; [run-budget.json](sp-shared-site6-integration-evidence-20261104/run-budget.json) сохраняет точные duration/exit values и строки первичного источника. Это budget accounting конкретных attempts, не performance benchmark.

Перед передачей подтверждены чистые `site-java` и `qrc`. Legacy `site-sp`, SP Source/pins/installed, production и mainstate этой ролью не редактировались. Commit/push отсутствуют. Выбранный общий owner квалифицирован в этой маленькой mixed native/QRC composition; полный SP stage, независимое review и release остаются отдельными действиями root.
