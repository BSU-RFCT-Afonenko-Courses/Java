import datetime
import json
import pathlib
import shutil
import subprocess
import sys
import tarfile
import time

started = time.monotonic()
release = pathlib.Path('/home/tolya/JavaCodex/java-release-20261110')
reports = release / 'reports'
root = reports / 'performance-sp-pl9c-delta'
root.mkdir()
course = root / 'course'
course.mkdir()
sp = release / 'system-practice'
sp_sha = '3bb0e0789545420b9947bdd85fac3d71715d0e36'
pl_sha = '9c62960ff345552dad492d8cc560dc6760787ec5'
extension_dirs = ['_extensions', 'handbook/_extensions', 'lectures/_extensions',
                  'practice/_extensions', 'tasks/_extensions', 'theory/_extensions']

def archive(repo, sha, destination, paths=(), omit=()):
    process = subprocess.Popen(['git', '-C', str(repo), 'archive', sha, *paths], stdout=subprocess.PIPE)
    with tarfile.open(fileobj=process.stdout, mode='r|') as archive_file:
        for member in archive_file:
            if any(member.name == name or member.name.startswith(name + '/') for name in omit):
                continue
            archive_file.extract(member, destination, filter='data')
    process.stdout.close()
    if process.wait():
        raise RuntimeError('exact local Git archive failed')

archive(sp, sp_sha, course, omit=extension_dirs)
for relative in extension_dirs:
    shutil.copytree(sp / relative, course / relative)
pl_source = root / 'pl-source'
pl_source.mkdir()
archive(release / 'prairielearn', pl_sha, pl_source, ('_extensions/course-prairielearn',))
destination = course / 'tasks/_extensions/Afonenko-Course-Tools/course-prairielearn'
shutil.rmtree(destination)
shutil.copytree(pl_source / '_extensions/course-prairielearn', destination)
catalogs = [f'{book}/_output/student/reference-catalog-local.json'
            for book in ['theory', 'practice', 'handbook']]
for relative in catalogs:
    target = course / relative
    target.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(sp / relative, target)
metadata = {
    'createdUTC': datetime.datetime.now(datetime.timezone.utc).isoformat(),
    'spBaseHead': sp_sha,
    'plArchiveHead': pl_sha,
    'performanceHead': '924b306cfa34c7ce0f79ca4050d045b6d4cb3e29',
    'installedCore': 'd7cc10eaa21fa35bbe32d73cc4afa216002393da',
    'course': str(course),
    'plArchiveRoot': str(pl_source),
    'copiedInstalledDirectories': extension_dirs,
    'packageOverride': str(destination.relative_to(course)),
    'copiedCatalogs': catalogs,
    'installedPackagesInterpretation': 'BASE published/local-patch provenance; not regenerated; actual PL override is exact 9c local archive',
    'warmState': 'persistent XDG cache; staged archive has no copied ignored native scratch; three existing catalogs reused byte-for-byte',
    'stagePreparationSeconds': time.monotonic() - started,
}
(root / 'stage-metadata.json').write_text(json.dumps(metadata, indent=2) + '\n')
command = ['/opt/quarto/bin/tools/x86_64/deno', 'run', '--config',
           str(release / 'performance/deno.json'), '--lock', str(release / 'performance/deno.lock'),
           '--frozen', '--node-modules-dir=manual', '--cached-only', '-A',
           str(reports / 'performance-sp-pl9c-delta.ts'), str(root)]
result = subprocess.run(command, cwd=release / 'performance')
(root / 'driver-timing.json').write_text(json.dumps({
    'totalStageAndDenoSeconds': time.monotonic() - started,
    'denoExitCode': result.returncode,
    'command': command,
}, indent=2) + '\n')
sys.exit(result.returncode)
