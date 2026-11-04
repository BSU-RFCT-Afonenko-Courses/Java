import datetime, json, os, pathlib, shutil, subprocess, sys, time, zipfile

started = time.monotonic()
release = pathlib.Path('/home/tolya/JavaCodex/java-release-20261110')
reports = release / 'reports'
stage = reports / 'core-native-cache-integration-20261104'
stage.mkdir()
owner = stage / 'owner'
(owner / '_extensions').mkdir(parents=True)
expected = {
    'core-native-bridge': '4a2903e6d03d1dd489b6922d113100826913cf67',
    'download': '944ca76dbdef5bb4842116950621f82449b631b3',
    'performance': 'e7267f1dd8b186d5b95a213f7a03a9bed71559a8',
}
for repo, sha in expected.items():
    actual = subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=release / repo, text=True).strip()
    if actual != sha:
        raise RuntimeError(f'{repo} changed: {actual}')
for repo, extension in [('core-native-bridge', 'course-core'), ('download', 'project-download')]:
    archive = subprocess.check_output(['git', 'archive', expected[repo], '_extensions/' + extension], cwd=release / repo)
    subprocess.run(['tar', '-x', '-C', str(owner)], input=archive, check=True)

def put(path, text):
    target = owner / path
    target.parent.mkdir(parents=True, exist_ok=True)
    target.write_text(text)

put('_quarto.yml', '''project:
  type: website
  render: [index.qmd]
  pre-render: [quarto run _extensions/course-core/entrypoints/native-pre.ts]
  post-render: [quarto run _extensions/course-core/entrypoints/native-post.ts]
format: {html: {theme: none}}
execute: {freeze: false, cache: true}
profile: {default: student, group: [[student, full]]}
course: {id: native-computed-probe, view: student}
course-core-runtime: native-ts
course-core-source-policy: static
course-core-projection: student
exercise-bank: true
default-exercise-statement-visibility: open
filters: [course-core]
''')
put('_quarto-full.yml', 'course: {view: full}\ncourse-core-projection: author\n')
put('projects/one/_metadata.yml', 'course-project:\n  starter: {path: starter}\n  solutions:\n    reference: {path: reference}\n')
put('projects/one/starter/Main.txt', 'Public starter\n')
put('projects/one/reference/Secret.txt', 'PRIVATE-REFERENCE-SENTINEL\n')
put('index.qmd', '''---
assessment: {id: work, kind: seminar}
---
# Вычисляемая работа

::: {#exr-computed project="/projects/one" difficulty=introductory}

```{r}
#| label: computed
#| echo: false
#| results: asis
#| fig-format: png
ledger_path <- "/home/tolya/JavaCodex/java-release-20261110/reports/core-native-cache-integration-20261104/execution-ledger.txt"
previous <- if (file.exists(ledger_path)) as.integer(readLines(ledger_path, warn=FALSE)) else 0L
writeLines(as.character(previous + 1L), ledger_path)
value <- 21L * 2L
cat("| computed | value |\\n|:---|---:|\\n| result |", value, "|\\n\\n")
plot(1:3, c(1, 4, 9), type="b", main="computed")
```

Вычисленное значение: `r value`.

::: {.solution}
PRIVATE-SOLUTION-SENTINEL
:::
:::

::: {.task-items}
1. @exr-computed
:::
''')
(stage / 'stage-metadata.json').write_text(json.dumps({
    'createdUTC': datetime.datetime.now(datetime.timezone.utc).isoformat(),
    'coreHead': expected['core-native-bridge'], 'downloadHead': expected['download'],
    'performanceHead': expected['performance'], 'owner': str(owner),
    'fixture': 'one authored synthetic QMD/assessment/exercise, one project; adjacent exact extension copies',
    'stagePreparationOuterSeconds': time.monotonic() - started,
    'cachePolicy': 'execute freeze:false/cache:true; same QMD/cache path/profile, two sequential captures; external ledger; no refresh/cleanup',
    'status': 'stage-completed-before-native', 'maxCaptures': 2,
}, indent=2) + '\n')
command = ['/opt/quarto/bin/tools/x86_64/deno', 'run', '--config', str(release / 'performance/deno.json'),
           '--lock', str(release / 'performance/deno.lock'), '--frozen', '--node-modules-dir=manual',
           '--cached-only', '-A', str(reports / 'core-native-cache-performance.ts'), str(stage)]
env = dict(os.environ, DENO_DIR='/home/tolya/.cache/quarto/deno_std/cache', DENO_NO_PACKAGE_JSON='1')
result = subprocess.run(command, cwd=release / 'performance', env=env)
(stage / 'outer-driver.json').write_text(json.dumps({'command': command, 'exitCode': result.returncode,
    'outerSeconds': time.monotonic() - started, 'retryCount': 0}, indent=2) + '\n')
if result.returncode:
    sys.exit(result.returncode)
verifications = []
for capture in [1, 2]:
    artifact = stage / f'artifact-{capture}'
    manifest = json.loads((artifact / 'result.json').read_text())
    with zipfile.ZipFile(artifact / 'artifact.zip') as archive:
        assert archive.testzip() is None, 'ZIP CRC failure'
        assert sorted(archive.namelist()) == sorted(f['target'] for f in manifest['files']), 'ZIP entries differ'
        for file in manifest['files']:
            assert archive.read(file['target']) == (artifact / 'inventory' / file['target']).read_bytes(), 'ZIP byte mismatch'
    item = {'capture': capture, 'status': 'PASS', 'entries': len(manifest['files']),
            'crc': 'all entries checked', 'bytes': 'all entries equal actual Core inventory files'}
    verifications.append(item)
    (stage / f'capture-{capture}-zip-verification.json').write_text(json.dumps(item, indent=2) + '\n')
assert (stage / 'execution-ledger.txt').read_text().strip() == '1', 'Expected one actual R-cell execution'
(stage / 'zip-verification.json').write_text(json.dumps({'status': 'PASS', 'captures': verifications,
    'executionLedger': 1, 'totalOuterSeconds': time.monotonic() - started}, indent=2) + '\n')
print('PASS two captures, ledger1, independent ZIP CRC and all exact inventory bytes')
