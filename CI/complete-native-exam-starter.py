#!/usr/bin/env python3
"""Готовит отдельную Native-поставку экзаменационной заготовки с Gradle Wrapper."""

import argparse
import hashlib
import json
from pathlib import Path, PurePosixPath
import re
import shutil
import stat
import subprocess
import tempfile
from zipfile import ZIP_DEFLATED, ZipFile, ZipInfo


ROOT = Path(__file__).resolve().parents[1]
QUESTION = "java-semester-one/exr-numbers-console-greeting"
CLIENT = Path("questions", QUESTION, "clientFilesQuestion")
PROJECT = "tasks/projects/numbers/console-greeting"
SOURCE = f"{PROJECT}/starter"
PROCESS = "src/test/java/coursesupport/JavaProcess.java"
CANONICAL_PROCESS = "tasks/test-support/src/main/java/coursesupport/JavaProcess.java"
WRAPPER_JAR = "gradle/wrapper/gradle-wrapper.jar"
WRAPPER_FILES = ("gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.properties")
WRAPPER_JAR_SHA256 = "3b8a25775a69158b5ad2b1d17a80a88dc7b40a352a73eb27d06c612a7ce68e98"


def git_blob(revision: str, name: str) -> bytes:
    return subprocess.check_output(["git", "-C", str(ROOT), "show", f"{revision}:{name}"])


def validate_archive(export: Path, revision: str) -> None:
    with ZipFile(export / CLIENT / "starter.zip") as archive:
        names = archive.namelist()
        normalized_names = {name.casefold() for name in names}
        if (
            WRAPPER_JAR in normalized_names
            or len(normalized_names) != len(names)
            or archive.testzip() is not None
        ):
            raise ValueError("Ожидался штатный Native ZIP перед добавлением Wrapper JAR")
        for name in names:
            path = PurePosixPath(name)
            if (
                not path.parts or path.as_posix() != name or path.is_absolute()
                or ".." in path.parts or re.search(r"[\\:\x00-\x1f]", name)
            ):
                raise ValueError("Некорректный путь в Native ZIP")
            member = export / CLIENT / "starter" / path
            if archive.read(name) != member.read_bytes():
                raise ValueError(f"Состав Native ZIP отличается от отдельных файлов: {name}")
        for name in (*WRAPPER_FILES, "src/main/java/Main.java"):
            if archive.read(name) != git_blob(revision, f"{SOURCE}/{name}"):
                raise ValueError(f"Файл Native не совпадает с исходным коммитом: {name}")
        for name, source in {
            PROCESS: CANONICAL_PROCESS,
            "src/test/java/DemoTest.java": f"{PROJECT}/demo/src/test/java/DemoTest.java",
        }.items():
            if archive.read(name) != git_blob(revision, source):
                raise ValueError(f"Файл Native не совпадает с исходным коммитом: {name}")


def complete_starter(export: Path, destination: Path, jar: bytes) -> list[str]:
    client = destination / CLIENT
    starter = client / "starter"
    complete = client / "starter.zip"
    with ZipFile(export / CLIENT / "starter.zip") as existing, ZipFile(complete, "w") as archive:
        names = [*existing.namelist(), WRAPPER_JAR]
        previous = {path.relative_to(starter).as_posix() for path in starter.rglob("*") if path.is_file()}
        if previous - set(names):
            raise ValueError("Во входной заготовке есть файлы вне согласованного Native ZIP")
        for member in existing.infolist():
            content = existing.read(member)
            target = starter / member.filename
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes(content)
            if member.filename == "gradlew":
                member.create_system = 3
                member.external_attr = (stat.S_IFREG | 0o755) << 16
                target.chmod(0o755)
            archive.writestr(member, content)
        wrapper = ZipInfo(WRAPPER_JAR)
        wrapper.create_system = 3
        wrapper.external_attr = (stat.S_IFREG | 0o644) << 16
        (starter / WRAPPER_JAR).write_bytes(jar)
        archive.writestr(wrapper, jar, compress_type=ZIP_DEFLATED)
    return [(CLIENT / "starter" / name).as_posix() for name in names]


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--export-native", type=Path, required=True)
    parser.add_argument("--base-native", type=Path, required=True)
    parser.add_argument("--course-revision", required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    revision = args.course_revision
    export = args.export_native.resolve(strict=True)
    base = args.base_native.resolve(strict=True)
    output = args.output.resolve()
    if not re.fullmatch(r"[0-9a-f]{40}", revision):
        parser.error("--course-revision должен содержать полный SHA коммита курса")
    if output.exists() or any(output.is_relative_to(path) for path in (export, base)):
        parser.error("--output должен быть новым каталогом вне обоих входных комплектов")
    if (base / ".git").exists():
        parser.error("Подготовьте входной снимок Native через git archive")

    validate_archive(export, revision)
    jar = git_blob(revision, f"{SOURCE}/{WRAPPER_JAR}")
    if hashlib.sha256(jar).hexdigest() != WRAPPER_JAR_SHA256:
        raise ValueError("Wrapper JAR не совпадает с официальным SHA-256 Gradle 9.8.2")
    delivery = json.loads((base / "delivery.json").read_text(encoding="utf-8"))
    if delivery["courseId"] != "java-semester-one" or QUESTION not in delivery["questions"]:
        raise ValueError("Во входном комплекте отсутствует назначенное задание Java")
    question_root = CLIENT.parent
    for name in ("question.html", "info.json", "serverFilesQuestion/starter/Main.java"):
        if (base / question_root / name).read_bytes() != (export / question_root / name).read_bytes():
            raise ValueError(f"Изменилась серверная часть задания: {name}")

    with tempfile.TemporaryDirectory(dir=output.parent) as temp:
        candidate = Path(temp, "native")
        shutil.copytree(base, candidate)
        files = complete_starter(export, candidate, jar)
        delivery["files"].extend(sorted(set(files) - set(delivery["files"])))
        manifest = json.dumps(delivery, ensure_ascii=False, indent=2) + "\n"
        (candidate / "delivery.json").write_text(manifest, encoding="utf-8")
        candidate.rename(output)
    starter = output / CLIENT / "starter.zip"
    print(f"{hashlib.sha256(starter.read_bytes()).hexdigest()}  {starter}")


if __name__ == "__main__":
    main()
