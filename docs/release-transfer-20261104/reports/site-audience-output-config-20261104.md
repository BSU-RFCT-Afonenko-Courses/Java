# Site: active audience и разделение Native outputs конфигурацией

**Candidate готов к root review: Site fast-forward выполнен, course-config diff — 0.** Последнее прямое решение пользователя разрешает удалить alternate-audience inspect и держать student/full outputs раздельно конфигурацией. При чтении текущих Java/SP оказалось, что нужное разделение **уже задано** во всех root+5 child owners. Поэтому существующую схему не переименовываем и не меняем public/deploy layout ради формального нового diff.

## Принятый Source Site

`site-java` был clean на `d16f2322c1da3c96c2e93d32225f5428ae347756`. Отдельно проверены наличие exact commit `85080c730ad54c8fd81fa4a3c7c806a8771c4f68`, ancestor relation и clean working tree; выполнен разрешённый `git merge --ff-only 85080…`. Итоговый HEAD85080c7 clean. Это перенос уже принятого candidate:2 production files, contract и existing focused test; нового runtime implementation в этом проходе нет. [FF evidence](site-audience-output-config-20261104-ff.json).

Исходный isolated `site-native-audience` и старый `site-sp` не изменены. Core/PL/Download/Navigation/QRC Source, course installed extensions/providers/pins, mainstate и existing outputs не менялись. Никакого нового commit/push/install; FF не был push. Последнее user decision заменяет прежний pending status из [implementation](site-active-audience-implementation-20261104.md)/[review](site-active-audience-review-20261104.md), но production остаётся до финального review.

Site850 убирает только alternate-аудит root+5 child. Active root/child audience, selected Native planning, source/physical/output geometry, cleanup safeguards, Core/QRC/currentness/role/lifetime checks и mount/ZIP paths остаются. Сохранённая прежняя цена удаляемой ветви SP —6 inclusive inspect envelopes суммарно9.914178s; это исторический measurement, **не** новый measured wall delta/ускорение этого candidate. Никаких новых inspect/API/gates не добавлено.

## Minimal Native layout: оставить существующее

Прочитаны оба root `_quarto.yml`, все24 root/children `_quarto-student.yml`/`_quarto-full.yml` и относящиеся к output/profile/mount/resource строки base child configs. Обе схемы совпадают:

| Native owner | student project.output-dir | full project.output-dir | course.view / projection |
| --- | --- | --- | --- |
| Java root |`_site-student`|`_site-full`|student/student; full/author|
| SP root |`_site-student`|`_site-full`|student/student; full/author|
| theory, tasks, lectures, practice, handbook каждого курса |`_output/student`|`_output/full`|student/student; full/author|

Root defaults/groups остаются `default:student`, mutually exclusive `[student,full]`; children имеют тот же Native arrangement. Existing source privacy options (`keep-source:false`/code-tools settings), resources и role/projection поля не трогаем. Base root `_site` и child `_output` сохраняются как base config; для рассматриваемых native student/full профилей output-dir переопределяется профилем.

Candidate diff именно **нулевой** для2 course owners: root/base profiles и child profiles побайтно не редактировались; targeted git diff по ним пуст. Java HEAD при чтении `6aa1cedd4b98b6dbd41cd6318627dc5d3a89990e`; у root уже были dirty mainstate и untracked generated HTML — они сохранены как найдены, не очищались и не включались в пакет. SP HEAD `9a2f8120a37d1b42384e2f008b3e0a3a34247008`, без новых tracked изменений от этого прохода.

Альтернатива `_site/student`/`_site/full` технически вписывается в ignored parent, но сейчас не даёт требуемого нового свойства: непересечение уже есть. Она потребовала бы companion изменения существующего Java artifact path/прочих downstream assumptions и создала бы лишнюю миграцию layout. По прямому правилу «если уже раздельная — не переименовывать без причины» выбран существующий вариант. Новых output dirs не создавали, старые `_site*`/`_output*` не перемещали, не удаляли и не сканировали.

## `_site` consumers и public path semantics

- `site-java/.../infrastructure/config.ts:58–61` получает effective `config.project["output-dir"]`, `_site` — лишь fallback. `workspace` использует selected Native inspect/profiles и existing `QUARTO_PROJECT_OUTPUT_DIR` override; candidate не хардкодит физический audience path. Child output берётся из selected native plan, а не вычисляется по имени профиля.
- Mount вычисляется из relative source path (`config.ts:119–122`). `compose.ts:560–585` копирует actual child output в `join(ws.output,project.mount)` и пересчитывает адреса current files относительно child output. При выбранной схеме student `theory/_output/student` попадает в `_site-student/theory`, full — в `_site-full/theory`; аналогично остальным частям. Mount names/`subprojects:{path,to:default}` остаются прежними.
- Final public URL строится из declared `website.site-url` и `project.mount` (`compose.ts:660–678`), отдельно от physical output basename. Relative navigation/search/QRC/download paths зависят от прежнего mount, поэтому `_site-student` не добавляется в public URL. Public Java/System-Practice site URLs, imports base-url и deploy destinations не переключались.
- Java `.github/workflows/check.yml:44` действительно сохраняет artifact из `course-render/_site-student`. Он уже соответствует выбранной config, companion workflow diff не нужен. CI render script использует существующую course pipeline; ни CI, ни render не запускались.
- SP `Taskfile.yml` render/render:full задают Native student/full profiles, publish использует student render и `quarto publish gh-pages --profile student --no-render`. Имя directory не подставляется собственным новым deploy wrapper; profile-aware selected output остаётся прежним. Publish **не** выполнялся.
- Source gitignore Java содержит `**/_site/`, `**/_site-student/`, `**/_site-full/`, `**/_output/`; SP — root `_site/`, `_site-student/`, `_site-full/` и `**/_output/`. Root resources обеих конфигураций исключают `_site*/**`, child resources исключают `_output` corridors. Это Source patterns, не новый whole-output/hash inventory и не утверждение, что Git ignore сам задаёт весь Core source admission.

Разделение directories не меняет selected active guards и не является privacy proof для произвольного ресурса. Если authored CLI/env override намеренно сведёт outputs в один/вложенные paths, Site850 больше не проверяет неактивную аудиторию: старую чужую output выдачу можно перезаписать/очистить. Такой tradeoff принят пользователем, новый автоматический overlap gate не возвращён. Отдельные student/full output dirs также **не** разделяют shared `_freeze`/knitr/Jupyter caches; cross-profile unmarked computed reuse/data freshness остаются прежним отдельным вопросом, без нового cache framework.

## Действия и пределы

Выполнены только Source/report чтение, обязательные local Git identity/ancestor/clean проверки для FF и сам разрешённый FF. Tests/compiler/Quarto/Native/render/inspect/hash/build/install/network/SSH/config/DB/liveness queries не запускались. Существующая timing capture не повторялась и не прерывалась. Pending production structural export consent не обходился; actual production mount/config остаётся неизвестным в прежнем объёме.

До final production review готовый пакет: accepted Site850 в `site-java`, уже имеющиеся12 непересекающихся profile pairs двух курсов, zero course/config/deploy diff и этот Source rationale. Actual full student/full composition/publication/installed composition не квалифицированы новым проходом. Root владеет дальнейшей приёмкой/state/pins/publication; никаких новых широких runs этот отчёт не назначает.

Own primary full-origin admission: 33 turn_context, строки8–4243, все top/nested gpt-6.1-sol/high; origin 01a1a25f-b0fb-7fd2-b244-83933af77834, latest 01a1a5ae-bbf9-7c52-be4f-07bda7cbcd48. Globals не менялись; hidden serving tier не утверждается. Brainstorming применён к выбору layout с уже заданным прямым user decision и правилом сохранить существующее разделение; дополнительных approval gates/вопросов не вводилось.
