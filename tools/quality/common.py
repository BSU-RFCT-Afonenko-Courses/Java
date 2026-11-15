"""Общая область авторских исходников курса для штатных инструментов качества."""

from __future__ import annotations

import subprocess
from pathlib import Path

# Исторические исследования, планы и свидетельства сохраняют исходные снимки.
# Действующие инструкции в корне docs остаются в области проверки.
ARCHIVE_DIRECTORIES = (
    "docs/research/",
    "docs/superpowers/",
    "docs/releases/",
    "docs/release-transfer-",
    "docs/night-session-",
    "docs/release-evening-",
)
ARCHIVE_FILES = {
    # Зафиксированный прежний договор с исходными свидетельствами.
    "tasks/projects/numbers/quadratic-roots/historical-contract.md",
    "docs/release-execution-regulation-20261103.md",
    "docs/release-cycle-20261110.md",
    "docs/refactoring-start-20261030.md",
    "docs/night-session-20261107.md",
    "docs/extensions-work-report-20261104.md",
    "docs/execution-status-20261030.md",
    "docs/agent-execution-proposal-20261029.md",
    "docs/incidents/2026-11-01-extension-performance.md",
}
GENERATED_COMPONENTS = {
    "_extensions",
    "node_modules",
    ".platform",
    ".ci",
    "vendor",
    ".git",
    ".quarto",
    ".qrc",
    "_book",
    "_site",
    "_site-student",
    "_site-full",
    "_output",
    "_generated",
    "_downloads",
    "_freeze",
    "site_libs",
    "__pycache__",
}


def source_files(root: Path) -> list[Path]:
    """Вернуть собственные tracked и новые неигнорируемые файлы, без gitlinks."""
    result = subprocess.run(
        ["git", "ls-files", "--cached", "--others", "--exclude-standard", "-z"],
        cwd=root,
        check=True,
        stdout=subprocess.PIPE,
    )
    selected: set[Path] = set()
    for name in result.stdout.decode("utf-8").split("\0"):
        if not name:
            continue
        path = Path(name)
        if any(part in GENERATED_COMPONENTS for part in path.parts):
            continue
        if name.startswith(ARCHIVE_DIRECTORIES):
            continue
        # Исключаются конкретные исторические отчёты: дата нового файла
        # сама по себе не должна исключать действующее руководство.
        if name in ARCHIVE_FILES:
            continue
        if any(part in {"projects", "examples"} for part in path.parts) and any(
            part in {"build", "target", "out"} for part in path.parts
        ):
            continue
        physical = root / path
        if physical.is_symlink():
            # Git symlink не является отдельным каноническим текстовым исходником.
            continue
        if physical.is_file():
            selected.add(path)
    return sorted(selected)
