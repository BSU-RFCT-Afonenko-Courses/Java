"""Восстановить закреплённую локальную адаптацию Core без изменения источника."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys


def git(repository, *args, **kwargs):
    return subprocess.check_output(["git", "-C", str(repository), *args], **kwargs)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source-repository", type=Path, required=True)
    parser.add_argument("--work-dir", type=Path, required=True)
    parser.add_argument("--install", action="store_true")
    args = parser.parse_args()
    package = Path(__file__).resolve().parent
    course = package.parents[2]
    manifest = json.loads((package / "manifest.json").read_text())
    patch = package / "optional-time.patch"
    if hashlib.sha256(patch.read_bytes()).hexdigest() != manifest["patchSha256"]:
        raise SystemExit("Хеш патча не совпадает с manifest.json")
    source = args.source_repository.resolve()
    work = args.work_dir.resolve()
    if work == source or source in work.parents:
        raise SystemExit("Рабочий каталог должен находиться вне исходного репозитория")
    if work.exists() and (not work.is_dir() or any(work.iterdir())):
        raise SystemExit("Рабочий каталог должен отсутствовать или быть пустым")
    git(source, "cat-file", "-e", manifest["base"] + "^{commit}")
    if args.install:
        provider = json.loads((course / "providers.json").read_text())["providers"]["core"]
        if provider["commit"] != manifest["commit"]:
            raise SystemExit("providers.json не закрепляет ожидаемый локальный коммит")
    work.mkdir(parents=True, exist_ok=True)
    checkout = work / "quarto-course"
    subprocess.run(["git", "clone", "--no-hardlinks", "--no-checkout", str(source), str(checkout)], check=True)
    git(checkout, "checkout", "--detach", manifest["base"])
    git(checkout, "apply", "--index", str(patch))
    tree = git(checkout, "write-tree", text=True).strip()
    if tree != manifest["tree"]:
        raise SystemExit("Дерево адаптации не совпадает с manifest.json")
    identity = manifest["identity"]
    env = os.environ.copy()
    for role in ("AUTHOR", "COMMITTER"):
        for key, field in (("NAME", "name"), ("EMAIL", "email"), ("DATE", "date")):
            env[f"GIT_{role}_{key}"] = identity[field]
    commit = git(checkout, "-c", "commit.gpgsign=false", "commit-tree", tree,
                 "-p", manifest["base"], input=manifest["message"], text=True, env=env).strip()
    if commit != manifest["commit"]:
        raise SystemExit("Коммит адаптации не совпадает с manifest.json")
    git(checkout, "update-ref", "refs/heads/codex/core-optional-time", commit)
    git(checkout, "checkout", "codex/core-optional-time")
    print(f"Восстановлено: {checkout}\nКоммит: {commit}\nДерево: {tree}", flush=True)
    if args.install:
        subprocess.run([sys.executable, str(course / "tools/sync-providers.py"),
                        "--providers-root", str(work), "--provider", "core"], check=True)


if __name__ == "__main__":
    main()
