"""One exact disposable SP stage and one bounded native Site6 capture."""
import datetime
import json
import os
import pathlib
import shutil
import subprocess
import tarfile
import time

release = pathlib.Path('/home/tolya/JavaCodex/java-release-20261110')
reports = release / 'reports'
stage = reports / 'sp-shared-site6-stage-20261104-actual'
sp_sha = '9a2f8120a37d1b42384e2f008b3e0a3a34247008'
performance_sha = '356a76b52d4fa0b20b965a82c1997e34632173fc'
scopes = ['.', 'theory', 'lectures', 'tasks', 'practice', 'handbook']
providers = [
    ('core-native-types', 'Afonenko-Course-Tools/quarto-course', '0cf20ccc0a4853b704544c7252fcaf3ccb84a518', 'course-core', scopes),
    ('prairielearn', 'Afonenko-Course-Tools/quarto-course-prairielearn', 'f3a09cd060609b57e516779588d635abedb1ca21', 'course-prairielearn', ['tasks']),
    ('qrc', 'Afonenko-Course-Tools/quarto-reference-catalog', '8732dbbe0d8502f8b6a77049e27907ae8a66e46a', 'reference-catalog', scopes),
    ('download', 'Afonenko-Course-Tools/quarto-project-download', '944ca76dbdef5bb4842116950621f82449b631b3', 'project-download', ['tasks']),
    ('site-java', 'Afonenko-Course-Tools/quarto-project-publish', 'd16f2322c1da3c96c2e93d32225f5428ae347756', 'course-site', ['.']),
]
catalogs = [f'{book}/_output/student/reference-catalog-local.json' for book in ['theory', 'practice', 'handbook']]

def git(repo, *args):
    return subprocess.check_output(['git', '-C', str(repo), *args], text=True).strip()

def archive(repo, sha, destination, paths=()):
    process = subprocess.Popen(['git', '-C', str(repo), 'archive', sha, *paths], stdout=subprocess.PIPE)
    with tarfile.open(fileobj=process.stdout, mode='r|') as archive_file:
        for member in archive_file:
            archive_file.extract(member, destination, filter='data')
    process.stdout.close()
    if process.wait():
        raise RuntimeError('exact local Git archive failed')

started = time.monotonic()
stage.mkdir()  # Never overwrite an earlier attempt or its evidence.
try:
    preflight = []
    for repo, sha in [('system-practice', sp_sha), *[(p[0], p[2]) for p in providers]]:
        actual = git(release / repo, 'rev-parse', sha + '^{commit}')
        if actual != sha:
            raise RuntimeError('exact Source ref unavailable: ' + repo)
        preflight.append({'repositoryDirectory': repo, 'archiveRef': actual})
    if git(release / 'performance', 'rev-parse', 'HEAD') != performance_sha:
        raise RuntimeError('Performance Source moved from approved356')
    if git(release / 'performance', 'status', '--porcelain'):
        raise RuntimeError('Performance Source is dirty')
    for relative in catalogs:
        if not (release / 'system-practice' / relative).is_file():
            raise RuntimeError('missing existing catalog input: ' + relative)
    for cache in ['/home/tolya/JavaCodex/.cache']:
        if not pathlib.Path(cache).is_dir() or not os.access(cache, os.W_OK):
            raise RuntimeError('existing writable cache unavailable: ' + cache)
    for path in ['/opt/quarto/bin/quarto', '/opt/quarto/bin/tools/x86_64/deno', '/opt/quarto/bin/tools/x86_64/pandoc',
                 '/home/tolya/.cache/quarto/deno_std/run_import_map.json']:
        if not pathlib.Path(path).is_file():
            raise RuntimeError('existing runtime file unavailable: ' + path)
    (stage / 'preflight.json').write_text(json.dumps({'sources': preflight, 'performanceHead': performance_sha,
        'runtimeSelection': 'existing pinned Quarto1.11.5 and bundled Deno/Pandoc; no version probe',
        'cachePreparation': 'existing paths only; no dependency install, cleanup or inventory'}, indent=2) + '\n')
    course = stage / 'course'
    course.mkdir()
    archive(release / 'system-practice', sp_sha, course)
    replacements = []
    for repo, repository, sha, package, destinations in providers:
        source = stage / 'providers' / repo
        source.mkdir(parents=True)
        archive(release / repo, sha, source, ('_extensions/' + package,))
        relative_destinations = []
        for scope in destinations:
            relative = pathlib.Path(scope) / '_extensions' / 'Afonenko-Course-Tools' / package
            target = course / relative
            shutil.rmtree(target)
            shutil.copytree(source / '_extensions' / package, target)
            relative_destinations.append(relative.as_posix())
        replacements.append({'repository': repository, 'extensionId': package, 'sha': sha,
            'archiveRoot': str(source), 'destinations': relative_destinations})
    for relative in catalogs:
        target = course / relative
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(release / 'system-practice' / relative, target)
    metadata = {'createdUTC': datetime.datetime.now(datetime.timezone.utc).isoformat(),
        'spBaseHead': sp_sha, 'performanceHead': performance_sha, 'course': str(course),
        'replacements': replacements, 'copiedCatalogs': catalogs,
        'unchangedUIRef': '04c189fccfaa56badfc079f7be46fea4969ccf37',
        'installedPackagesInterpretation': 'BASE SP9a provenance, not regenerated; actual15 archive overrides listed here',
        'warmState': 'persistent existing XDG/Deno caches; no copied ignored native or engine scratch; three borrowed student catalogs',
        'stagePreparationSeconds': time.monotonic() - started}
    (stage / 'stage-metadata.json').write_text(json.dumps(metadata, indent=2) + '\n')
except Exception as error:
    (stage / 'preparation-failure.json').write_text(json.dumps({'error': str(error),
        'stagePreparationSeconds': time.monotonic() - started, 'nativeAttempted': False}, indent=2) + '\n')
    raise

command = ['/usr/bin/timeout', '--signal=TERM', '--kill-after=5s', '115s',
    '/opt/quarto/bin/tools/x86_64/deno', 'run', '--config', str(release / 'performance/deno.json'),
    '--lock', str(release / 'performance/deno.lock'), '--frozen', '--node-modules-dir=manual', '--cached-only', '-A',
    str(reports / 'sp-shared-site6-stage-20261104.ts'), str(stage)]
env = dict(os.environ, XDG_CACHE_HOME='/home/tolya/JavaCodex/.cache',
    DENO_DIR='/home/tolya/.cache/quarto/deno_std/cache', DENO_NO_UPDATE_CHECK='1',
    DENO_NO_PACKAGE_JSON='1', QUARTO_RUN_NO_NETWORK='true', QUARTO='/opt/quarto/bin/quarto')
(stage / 'outer-command.json').write_text(json.dumps({'command': command,
    'cwd': str(release / 'performance'), 'selectedEnv': {k: env[k] for k in [
        'XDG_CACHE_HOME', 'DENO_DIR', 'DENO_NO_UPDATE_CHECK', 'DENO_NO_PACKAGE_JSON', 'QUARTO_RUN_NO_NETWORK', 'QUARTO']},
    'capSecondsIncludingKillGrace': 120, 'retry': 0}, indent=2) + '\n')
native_outer_started = time.monotonic()
with (stage / 'native-driver.stdout.log').open('wb') as stdout, (stage / 'native-driver.stderr.log').open('wb') as stderr:
    result = subprocess.run(command, cwd=release / 'performance', env=env, stdout=stdout, stderr=stderr)
timing = {'stagePreparationSeconds': metadata['stagePreparationSeconds'],
    'nativeOuterIncludingLauncherPreparationSeconds': time.monotonic() - native_outer_started,
    'totalStageAndNativeOuterSeconds': time.monotonic() - started, 'outerExitCode': result.returncode,
    'capSecondsIncludingKillGrace': 120, 'retry': 0}
(stage / 'outer-result.json').write_text(json.dumps(timing, indent=2) + '\n')
print(json.dumps(timing))
raise SystemExit(result.returncode)
