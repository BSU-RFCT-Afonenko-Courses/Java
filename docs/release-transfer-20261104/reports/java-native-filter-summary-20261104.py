"""Одна disposable Java128 попытка; общий cap включает stage и grace."""
import datetime
import json
import os
import pathlib
import shutil
import subprocess
import sys
import tarfile
import time

release = pathlib.Path('/home/tolya/JavaCodex/java-release-20261110')
reports = release / 'reports'
stage = reports / 'java-native-filter-summary-20261104-actual'
java_ref = 'a6d28a42d3f0d4f4bbebcd64b5664ebef8341932'
perf_ref = 'c2dcf002a7396aa3b507e49b99e0ecf00ed9f438'
providers = [
    ('core-native-bridge', '4a2903e6d03d1dd489b6922d113100826913cf67', 'course-core',
     ['.', 'theory', 'tasks', 'lectures', 'practice', 'handbook']),
    ('prairielearn', 'd8efb030ee6435f3c1f7d242e7388803b55b3299', 'course-prairielearn', ['tasks']),
]

def write(name, value):
    (stage / name).write_text(json.dumps(value, ensure_ascii=False, indent=2) + '\n')

def git(repo, *args):
    return subprocess.check_output(['git', '-C', str(repo), *args], text=True).strip()

def archive(repo, ref, destination, paths=()):
    proc = subprocess.Popen(['git', '-C', str(repo), 'archive', ref, *paths], stdout=subprocess.PIPE)
    with tarfile.open(fileobj=proc.stdout, mode='r|') as source:
        for member in source:
            source.extract(member, destination, filter='data')
    proc.stdout.close()
    if proc.wait():
        raise RuntimeError('Exact local Git archive failed')

if '--inside' in sys.argv:
    started = time.monotonic()
    phase = 'exact-source-refs'
    try:
        write('phase.json', {'phase': phase, 'nativeAttempted': False})
        refs = []
        for repo, ref in [('java', java_ref), *[(p[0], p[1]) for p in providers]]:
            actual = git(release / repo, 'rev-parse', ref + '^{commit}')
            if actual != ref:
                raise RuntimeError('Exact Source ref unavailable: ' + repo)
            refs.append({'repositoryDirectory': repo, 'usedArchivedRef': actual, 'actualCheckoutHead': git(release / repo, 'rev-parse', 'HEAD')})
        if git(release / 'performance', 'rev-parse', 'HEAD') != perf_ref:
            raise RuntimeError('Performance moved from exact c2dc')
        write('source-refs.json', {'sources': refs, 'performanceRef': perf_ref,
            'performanceActualHead': git(release / 'performance', 'rev-parse', 'HEAD'),
            'coreArchivePolicy': '4a intentionally archived for comparable provider composition; checkout HEAD recorded separately',
            'selection': 'exact-local-git-archive; no network/version/hash/inventory probes'})
        phase = 'archive-and-whole-package-overlays'
        write('phase.json', {'phase': phase, 'nativeAttempted': False})
        course = stage / 'course'
        course.mkdir()
        archive(release / 'java', java_ref, course)
        performance_source = stage / 'providers' / 'performance'
        performance_source.mkdir(parents=True)
        archive(release / 'performance', perf_ref, performance_source)
        (performance_source / 'node_modules').symlink_to(release / 'performance' / 'node_modules', target_is_directory=True)
        replacements = []
        for repo, ref, package, scopes in providers:
            source = stage / 'providers' / repo
            source.mkdir(parents=True)
            archive(release / repo, ref, source, ('_extensions/' + package,))
            destinations = []
            for owner in scopes:
                relative = pathlib.Path(owner) / '_extensions' / 'Afonenko-Course-Tools' / package
                target = course / relative
                shutil.rmtree(target)
                shutil.copytree(source / '_extensions' / package, target)
                destinations.append(relative.as_posix())
            replacements.append({'repositoryDirectory': repo, 'ref': ref,
                'extensionId': package, 'destinations': destinations})
        metadata = {'createdUTC': datetime.datetime.now(datetime.timezone.utc).isoformat(),
            'javaRef': java_ref, 'performanceRef': perf_ref, 'course': str(course),
            'replacements': replacements, 'stagePreparationSeconds': time.monotonic() - started,
            'installedProvenance': 'Javaa6 installed pins remain unchanged; actual whole-package overlays listed here',
            'warmState': 'existing persistent caches; no ignored native generations/outputs copied',
            'inputPolicy': 'full Git archive; no QMD/package-corridor exclusions'}
        write('stage-metadata.json', metadata)
        write('phase.json', {'phase': 'performance-driver-launch', 'nativeAttempted': False,
            'stagePreparationSeconds': metadata['stagePreparationSeconds']})
        command = ['/opt/quarto/bin/tools/x86_64/deno', 'run', '--config', str(performance_source / 'deno.json'),
            '--lock', str(performance_source / 'deno.lock'), '--frozen', '--node-modules-dir=manual',
            '--cached-only', '-A', str(reports / 'java-native-filter-summary-20261104.ts'), str(stage)]
        print(json.dumps({'phase': 'stage-ready', 'stagePreparationSeconds': metadata['stagePreparationSeconds']}, ensure_ascii=False), flush=True)
        result = subprocess.run(command, cwd=release / 'performance')
        raise SystemExit(result.returncode)
    except Exception as error:
        write('preparation-failure.json', {'phase': phase, 'error': str(error),
            'stagePreparationSeconds': time.monotonic() - started, 'nativeAttempted': False})
        raise
else:
    started = time.monotonic()
    stage.mkdir()  # Never overwrite an earlier attempt.
    env = dict(os.environ, XDG_CACHE_HOME='/home/tolya/JavaCodex/.cache',
        DENO_DIR='/home/tolya/.cache/quarto/deno_std/cache', DENO_NO_UPDATE_CHECK='1',
        DENO_NO_PACKAGE_JSON='1', QUARTO_RUN_NO_NETWORK='true', QUARTO='/opt/quarto/bin/quarto')
    for diagnostic_variable in ['QUARTO_TRACE_FILTERS', 'QUARTO_FLUSH_TRACE', 'QUARTO_COMBINED_LUA_PROFILE', 'QUARTO_LOG_LEVEL']:
        env.pop(diagnostic_variable, None)
    remaining = 114 - (time.monotonic() - started)  # 1 s reserved for outer bookkeeping within total120.
    command = ['/usr/bin/timeout', '--signal=TERM', '--kill-after=5s', f'{remaining:.6f}s',
        sys.executable, str(pathlib.Path(__file__).resolve()), '--inside']
    write('outer-command.json', {'command': command, 'cwd': str(release),
        'selectedEnv': {k: env[k] for k in ['XDG_CACHE_HOME', 'DENO_DIR', 'DENO_NO_UPDATE_CHECK',
            'DENO_NO_PACKAGE_JSON', 'QUARTO_RUN_NO_NETWORK', 'QUARTO']},
        'capIncludingStagePreparationAndGraceSeconds': 120, 'nativeMaximumAttempts': 1, 'retry': 0})
    with (stage / 'driver.stdout.log').open('wb') as stdout, (stage / 'driver.stderr.log').open('wb') as stderr:
        result = subprocess.run(command, cwd=release, env=env, stdout=stdout, stderr=stderr)
    observation = {'outerExitCode': result.returncode, 'totalStagePreparationNativeOuterSeconds': time.monotonic() - started,
        'capIncludingStagePreparationAndGraceSeconds': 120, 'retry': 0,
        'executeScenarioEntered': (stage / 'native-start.json').exists(),
        'actualNativeSpawnStatus': 'determine only from saved command receipts/logs; marker is not spawn proof',
        'productFinalRecovery': 'UNKNOWN unless actual complete RunResult exists'}
    write('outer-result.json', observation)
    print(json.dumps(observation, ensure_ascii=False))
    raise SystemExit(result.returncode)
