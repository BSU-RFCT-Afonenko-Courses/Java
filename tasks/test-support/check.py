#!/usr/bin/env python3
"""Запустить весь набор тестов общей поддержки без скачивания зависимостей."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess
import tempfile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--junit-jar', required=True, type=Path)
    parser.add_argument('--report', type=Path)
    args = parser.parse_args()
    root = Path(__file__).resolve().parent
    java_home = os.environ.get('JAVA_HOME')
    java = Path(java_home) / 'bin/java' if java_home else Path('java')
    javac = Path(java_home) / 'bin/javac' if java_home else Path('javac')
    sources = sorted((root / 'src').rglob('*.java'))
    results = []
    with tempfile.TemporaryDirectory(prefix='course-test-support-') as temporary:
        commands = [
            [str(javac), '--release', '25', '-encoding', 'UTF-8', '-proc:none',
             '-cp', str(args.junit_jar.resolve()), '-d', temporary, *map(str, sources)],
            [str(java), '-jar', str(args.junit_jar.resolve()), 'execute', '--class-path',
             temporary, '--scan-class-path', '--disable-banner', '--disable-ansi-colors',
             '--details', 'summary'],
        ]
        for command in commands:
            completed = subprocess.run(command, capture_output=True, text=True, timeout=45)
            results.append({'argv': command, 'exitCode': completed.returncode,
                            'stdout': completed.stdout, 'stderr': completed.stderr})
            print(completed.stdout, end='')
            print(completed.stderr, end='')
            if completed.returncode:
                break
    report = {
        'status': 'PASS' if len(results) == 2 and all(r['exitCode'] == 0 for r in results) else 'FAIL',
        'sources': {str(p.relative_to(root)): hashlib.sha256(p.read_bytes()).hexdigest() for p in sources},
        'junitSha256': hashlib.sha256(args.junit_jar.read_bytes()).hexdigest(),
        'commands': results,
        'scope': 'Весь host JUnit-набор библиотеки; целевая поставка проектов проверяется отдельно.',
    }
    if args.report:
        args.report.write_text(json.dumps(report, ensure_ascii=False, indent=2) + '\n')
    return 0 if report['status'] == 'PASS' else 1


if __name__ == '__main__':
    raise SystemExit(main())
