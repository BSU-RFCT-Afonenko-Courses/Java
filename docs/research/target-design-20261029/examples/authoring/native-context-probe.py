"""Воспроизведение малого исследовательского inspect; не parser/producer курса.

Запуск только явно: python3 native-context-probe.py /absolute/path/to/quarto.
Создаёт собственное /tmp-дерево без hooks/filters/execute; рабочий курс не читает.
"""
from pathlib import Path
import hashlib
import json
import os
import subprocess
import sys
import tempfile

executable = str(Path(sys.argv[1]).resolve())
root = Path(tempfile.mkdtemp(prefix="authoring-native-context-20261029-"))
files = {
    "_quarto.yml": "project:\n  type: book\nbook:\n  chapters: [index.qmd]\nformat: html\nexecute:\n  enabled: false\nresearch:\n  root: true\n",
    "index.qmd": "# Контроль\n",
    "projects/_metadata.yml": "research:\n  selection:\n    demo: true\n  inherited: parent\n",
    "projects/arrays/reverse-array/_metadata.yml": "research:\n  selection:\n    demo: false\n    extra: true\n  local: task\n",
    "projects/arrays/reverse-array/_course-input.qmd": "---\n---\n",
    "_quarto-source-context.yml": "project:\n  type: default\n  render:\n    - projects/arrays/reverse-array/_course-input.qmd\n",
}
for name, body in files.items():
    path = root / name
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(body, encoding="utf-8")
environment = dict(os.environ)
environment["XDG_CACHE_HOME"] = str(root / "_cache")
runs = []
for label, extra in (("book", []), ("selected-default", ["--profile", "source-context"])):
    command = [executable, "inspect", str(root / "projects/arrays/reverse-array/_course-input.qmd"), *extra]
    result = subprocess.run(command, cwd=root, env=environment, text=True, capture_output=True, timeout=35)
    (root / (label + ".stdout")).write_text(result.stdout, encoding="utf-8")
    (root / (label + ".stderr")).write_text(result.stderr, encoding="utf-8")
    value = json.loads(result.stdout) if result.returncode == 0 else {}
    project = value.get("project", {})
    runs.append({
        "case": label, "argv": command, "exit": result.returncode,
        "project_dir": project.get("dir"),
        "project_type": project.get("config", {}).get("project", {}).get("type"),
        "research": value.get("formats", {}).get("html", {}).get("metadata", {}).get("research"),
        "version": value.get("quarto", {}).get("version"),
        "input": project.get("files", {}).get("input"), "stderr": result.stderr[:500],
    })
summary = {
    "root": str(root), "runs": runs,
    "fixture_sha256": {name: hashlib.sha256((root / name).read_bytes()).hexdigest() for name in files},
}
(root / "summary.json").write_text(json.dumps(summary, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
print(json.dumps(summary, ensure_ascii=False, indent=2))
