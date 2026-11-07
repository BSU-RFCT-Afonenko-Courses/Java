# Одна предложенная compiler-проверка Native publication

**Не выполнена.** Созданы только temporary map/список roots и данный план.
Root запускает ровно один compiler process после Source freezes и своего решения;
cap20s, retry0. Это `deno check`, не `deno run/test`, не Native/config/render.
Top-level fixture code при check не исполняется.

## Source correction до первого compiler

По отдельному разрешению root изменены ровно три type usages
`NativeOperation → NativePublication` в `download/tests/runtime-types.ts`:
import, resource inventory ReturnType и condition ReturnType. Это actual export,
без compat alias/casts и без других production mutations. Source patch/report
обновлены, consumer повторно frozen. Эта fixture и существующая contract-types
**включены** в единственный graph. Compiler для обнаружения этого mismatch не
запускался; известный direct export gap исправлен Source.

## Actual map и зависимости

Готовая map: `/tmp/native-publication-combined-imports-20261104.json`.
Машиночитаемый список тех же roots:
`/tmp/native-publication-combined-roots-20261104.json`.
Map повторяет existing `/tmp/download-ownership-snapshot-imports-20261104.json`
Core/PL/QRC relative sibling prefixes и `/tmp/core-native-bridge-imports.json`
bare Core/pl-native/std bindings; добавлены actual bare PL/Download/QRC public
prefixes для Site. Ни copied source/stubs, ни ambient interfaces нет.
Core — именно `core-native-bridge`, не отдельный dirty `core`.

```json
{
  "imports": {
    "course-core/": "file:///home/tolya/JavaCodex/java-release-20261110/core-native-bridge/_extensions/course-core/",
    "course-prairielearn/": "file:///home/tolya/JavaCodex/java-release-20261110/prairielearn/_extensions/course-prairielearn/",
    "project-download/": "file:///home/tolya/JavaCodex/java-release-20261110/download/_extensions/project-download/",
    "pl-native/": "file:///home/tolya/JavaCodex/java-release-20261110/prairielearn/_extensions/course-prairielearn/application/",
    "reference-catalog/": "file:///home/tolya/JavaCodex/java-release-20261110/qrc/_extensions/reference-catalog/",
    "stdlib/path": "jsr:@std/path@0.224.0",
    "stdlib/fs": "jsr:@std/fs@0.224.0",
    "file:///home/tolya/JavaCodex/java-release-20261110/download/_extensions/course-core/": "file:///home/tolya/JavaCodex/java-release-20261110/core-native-bridge/_extensions/course-core/",
    "file:///home/tolya/JavaCodex/java-release-20261110/download/_extensions/course-prairielearn/": "file:///home/tolya/JavaCodex/java-release-20261110/prairielearn/_extensions/course-prairielearn/",
    "file:///home/tolya/JavaCodex/java-release-20261110/download/_extensions/reference-catalog/": "file:///home/tolya/JavaCodex/java-release-20261110/qrc/_extensions/reference-catalog/"
  }
}
```

Bundled compiler существует: `/opt/quarto/bin/tools/x86_64/deno`.
Используется существующий Quarto cache
`/home/tolya/.cache/quarto/deno_std/cache`, без нового DENO_DIR/install/download.
Source reading подтвердил cached URL metadata для
`https://jsr.io/@std/path/meta.json`, `@std/path/0.224.0/mod.ts`,
`@std/fs/meta.json`, `@std/fs/0.224.0/mod.ts` в existing
`remote/https/jsr.io`; prior successful compiler maps использовали эти versions.
Полная транзитивная cache completeness сейчас compiler/resolver не проверялась.
`--deny-import` сохраняется; отсутствующая cached dependency должна остановить
единственный запуск с точным URL, без network/fake alias/retry.
`--cached-only` не добавлен: bundled check ранее документированно не принимает его.
Vendored Core/PL Zod declarations существуют. Core relative sibling
`course-navigation/exercise-index.ts` — реальный файл того же bridge tree;
не перенаправляется на другой ref или stub. QRC public — actual qrc tree.

## Один command, без предварительного compiler/helper invocation

Команда содержит все 34 roots одного graph. Она ещё не исполнялась.
`timeout --signal=KILL 20s` задаёт общий hard cap20 без дополнительной grace/retry.
Output files создаст только root при запуске; exit status нужно сохранить отдельно.
Не заменять отказ запуском половин graph.

```bash
timeout --signal=KILL 20s env \
  DENO_DIR=/home/tolya/.cache/quarto/deno_std/cache \
  DENO_NO_PACKAGE_JSON=1 \
  /opt/quarto/bin/tools/x86_64/deno check \
  --deny-import --no-config --no-lock \
  --import-map=/tmp/native-publication-combined-imports-20261104.json \
  /home/tolya/JavaCodex/java-release-20261110/core-native-bridge/_extensions/course-core/public.ts \
  /home/tolya/JavaCodex/java-release-20261110/core-native-bridge/_extensions/course-core/release/publication.ts \
  /home/tolya/JavaCodex/java-release-20261110/core-native-bridge/_extensions/course-core/entrypoints/native-pre.ts \
  /home/tolya/JavaCodex/java-release-20261110/core-native-bridge/_extensions/course-core/entrypoints/native-post.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/_extensions/course-prairielearn/public.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/_extensions/course-prairielearn/entrypoints/inspect-publication.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/owner-contract.test.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/native-adapter.test.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/grading-policy.test.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/native-seed-policy.test.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/native-sp-carrier.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/native-course-export-types.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/native-publication-sources.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/native-scope-integration.ts \
  /home/tolya/JavaCodex/java-release-20261110/prairielearn/tests/performance-native-boundary.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/_extensions/project-download/public.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/_extensions/project-download/entrypoints/pre.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/_extensions/project-download/entrypoints/post.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/tests/contract-types.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/tests/runtime-types.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/tests/public-runtime.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/tests/model-receipts.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/tests/model-installed.ts \
  /home/tolya/JavaCodex/java-release-20261110/download/tests/project-contract-consumer.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/_extensions/course-site/application/compose.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/_extensions/course-site/entrypoints/pre.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/_extensions/course-site/entrypoints/post.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-public-operation.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-owner-release.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-native-pipeline.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-child-profiles.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-domains.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-project-native.ts \
  /home/tolya/JavaCodex/java-release-20261110/site-native-audience/tests/site-siblings.ts \
  > /home/tolya/JavaCodex/java-release-20261110/reports/native-publication-combined-compiler-20261104.stdout.log \
  2> /home/tolya/JavaCodex/java-release-20261110/reports/native-publication-combined-compiler-20261104.stderr.log
```

Core public/new module включают semantic extraction и shared acquisition/closing
graph; native pre/post проверяются как entrypoints без execution. Producer
подтвердил отсутствие дополнительных changed Core fixtures. PL roots включают
actual public/new CLI, все прямо назначенные общие decoder/declaration/full-course
type consumers и три изменённых integration callers. Download public/pre/post
плюс runtime/contract type consumers и четыре изменённых direct fixtures закрывают
breaking API. Site compose/pre/post и шесть catalog/receipt/proof consumers плюс
sibling-error fixture используют ту же actual map. Helpers импортируются
транзитивно. Historical model-installed включён как изменённый Source reader,
не как обещание runnable Native acceptance.

Existing `runtime-types` inventory misuse `@ts-expect-error` и PL policy/input
Exact assertions входят в graph; новых test schemas/DTO/checker/framework нет.
Нового capability negative в Source этот план не добавляет. Полный repo,
CI/matrix, курсы и Native runtime этой командой не предлагаются.
