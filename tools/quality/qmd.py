#!/usr/bin/env python3
"""Format only authored Java fences, using the standard Java formatter.

Syntactic fragments receive temporary wrappers; wrappers never enter course files.
Incomplete/invalid teaching examples require a content-addressed reviewed reason.
"""

from __future__ import annotations

import argparse
import base64
import hashlib
import json
import re
import subprocess
import sys
import tempfile
from collections import Counter
from dataclasses import dataclass
from pathlib import Path

HERE = Path(__file__).resolve().parent
BEGIN = "// __QMD_FORMAT_BEGIN__"
END = "// __QMD_FORMAT_END__"


@dataclass
class Fence:
    path: Path
    number: int
    line: int
    start: int
    end: int
    source: str
    prefix: str
    newline: str

    @property
    def digest(self) -> str:
        return hashlib.sha256(self.source.encode()).hexdigest()


def is_java_info(info: str) -> bool:
    info = info.strip()
    return bool(
        re.match(r"^java(?:\s|$)", info, re.I)
        or re.match(r"^\{\s*java(?:[\s,}]|$)", info, re.I)
        or (
            info.startswith("{")
            and re.search(r"(?:[\s{])\.java(?:[\s}]|$)", info, re.I)
        )
    )


def fences(path: Path, text: str) -> list[Fence]:
    """Read CommonMark fences, including list and blockquote containers.

    A non-Java outer fence is opaque, so Java-looking examples inside it are not
    accidentally interpreted as executable blocks. Fence delimiters and cell
    options are retained byte for byte.
    """
    lines = text.splitlines(keepends=True)
    result: list[Fence] = []
    offset = 0
    index = 0
    number = 0
    while index < len(lines):
        opening = re.match(r"^(.*?)(`{3,}|~{3,})([^\r\n]*)", lines[index])
        if not opening:
            offset += len(lines[index])
            index += 1
            continue
        raw_prefix, delimiter, info = opening.groups()
        if not re.fullmatch(r"(?:[ \t]+|>[ \t]?|(?:[-+*]|\d+[.)])[ \t]+)*", raw_prefix):
            offset += len(lines[index])
            index += 1
            continue
        # List markers occupy continuation indentation, while quote markers recur.
        prefix = re.sub(
            r"[-+*]|\d+[.)]", lambda match: " " * len(match.group()), raw_prefix
        )
        if delimiter[0] == "`" and "`" in info:
            offset += len(lines[index])
            index += 1
            continue
        java = is_java_info(info)
        end = index + 1
        close = re.compile(
            r"^[ \t]*(?:>[ \t]*)*"
            + re.escape(delimiter[0])
            + "{"
            + str(len(delimiter))
            + r",}[ \t]*(?:\r?\n)?$"
        )
        while end < len(lines) and not close.match(lines[end]):
            end += 1
        if end == len(lines):
            if java:
                raise ValueError(f"{path}:{index + 1}: unclosed Java fence")
            break
        number += int(java)
        if java:
            bodies: list[str] = []
            # CommonMark removes at most the opening indent inside a fenced block.
            for line in lines[index + 1 : end]:
                if ">" in prefix:
                    match = re.match(r"^[ \t]*(?:>[ \t]?)+", line)
                    removed = match.group() if match else ""
                    trailing_indent = max(0, len(prefix.rsplit(">", 1)[1]) - 1)
                    rest = line[len(removed) :]
                    removed += rest[
                        : min(trailing_indent, len(rest) - len(rest.lstrip(" \t")))
                    ]
                else:
                    removed = line[
                        : min(len(prefix), len(line) - len(line.lstrip(" \t")))
                    ]
                bodies.append(line[len(removed) :])
            # Quarto cell options are metadata rather than Java comments to reflow.
            options = 0
            while options < len(bodies) and re.match(r"^\s*//\|", bodies[options]):
                options += 1
            code_start = (
                offset
                + len(lines[index])
                + sum(map(len, lines[index + 1 : index + 1 + options]))
            )
            newline = "\r\n" if lines[index].endswith("\r\n") else "\n"
            result.append(
                Fence(
                    path,
                    number,
                    index + 1 + options + 1,
                    code_start,
                    offset + sum(map(len, lines[index:end])),
                    "".join(bodies[options:]).replace("\r\n", "\n"),
                    prefix,
                    newline,
                )
            )
        consumed = sum(map(len, lines[index : end + 1]))
        offset += consumed
        index = end + 1
    return result


def wrap(source: str, kind: str) -> str:
    if kind == "unit":
        return source
    body = f"{BEGIN}\n{source.rstrip()}\n{END}\n"
    if kind == "members" or kind.startswith(("members:", "record-members:")):
        name = kind.split(":", 1)[1] if ":" in kind else "__QmdSnippet"
        declaration = (
            f"record {name}()"
            if kind.startswith("record-members:")
            else f"class {name}"
        )
        return declaration + " {\n" + body + "}\n"
    if kind == "statements":
        return "class __QmdSnippet { void __qmd() {\n" + body + "} }\n"
    if kind == "expression":
        return "class __QmdSnippet { Object __qmd = (\n" + body + "); }\n"
    raise ValueError(kind)


def syntax_records(sources: list[str]) -> list[tuple[str, str]]:
    payload = "".join(
        f"{i}\t{base64.b64encode(s.encode()).decode()}\n" for i, s in enumerate(sources)
    )
    proc = subprocess.run(
        ["java", str(HERE / "qmd" / "SnippetSyntax.java")],
        input=payload,
        text=True,
        capture_output=True,
    )
    if proc.returncode:
        raise RuntimeError(proc.stderr.strip())
    result = [("missing syntax result", "")] * len(sources)
    for line in proc.stdout.splitlines():
        key, encoded, fingerprint = line.split("\t")
        result[int(key)] = (base64.b64decode(encoded).decode(), fingerprint)
    return result


def syntax(sources: list[str]) -> list[str]:
    return [error for error, _ in syntax_records(sources)]


def classify(blocks: list[Fence]) -> tuple[list[str | None], list[str]]:
    kinds = ["unit", "members", "statements", "expression"]
    errors = syntax([wrap(b.source, kind) for b in blocks for kind in kinds])
    selected: list[str | None] = []
    reasons: list[str] = []
    extra: list[tuple[int, str, list[str]]] = []
    for i, block in enumerate(blocks):
        candidates = errors[i * len(kinds) : (i + 1) * len(kinds)]
        chosen = next(
            (kind for kind, error in zip(kinds, candidates, strict=True) if not error),
            None,
        )
        # Java 25 compact source files accept members directly. Report them as
        # members rather than pretending that every pedagogical excerpt is a unit.
        if chosen == "unit" and not re.search(
            r"(?m)^\s*(?:(?:public|final|abstract|sealed|non-sealed)\s+)*(?:class|interface|enum|record|module|package|import)\b",
            block.source,
        ):
            chosen = "members"
        selected.append(chosen)
        reasons.append(
            "; ".join(
                f"{kind}: {error}"
                for kind, error in zip(kinds, candidates, strict=True)
            )
        )
        if chosen is not None:
            continue
        # A constructor excerpt needs its actual enclosing name, not a fabricated
        # method return type. Only parser-accepted candidates may be formatted.
        names = set(
            re.findall(
                r"(?m)^\s*(?:(?:public|protected|private)\s+)?([A-Z]\w*)\s*(?:\([^;]*?\))?\s*\{",
                block.source,
            )
        )
        for name in sorted(names):
            kind = "members:" + name
            extra.append((i, kind, [wrap(block.source, kind)]))
            record_kind = "record-members:" + name
            extra.append((i, record_kind, [wrap(block.source, record_kind)]))
        # Some slides deliberately put declarations and their calls in one fence.
        # Split only at existing line boundaries; javac validates both halves.
        for match in re.finditer(r"(?:}[^\S\n]*|;[^\S\n]*)\n", block.source):
            cut = match.end()
            if block.source[cut:].strip():
                extra.append(
                    (
                        i,
                        f"mixed:{cut}",
                        [block.source[:cut], wrap(block.source[cut:], "statements")],
                    )
                )
    trial = (
        syntax([source for _, _, sources in extra for source in sources])
        if extra
        else []
    )
    cursor = 0
    for index, kind, sources in extra:
        passed = not any(trial[cursor : cursor + len(sources)])
        cursor += len(sources)
        if selected[index] is None and passed:
            selected[index] = kind
    return selected, reasons


def unwrap(source: str, kind: str) -> str:
    if kind == "unit":
        return source
    lines = source.splitlines()
    start = next(i for i, line in enumerate(lines) if line.strip() == BEGIN) + 1
    end = next(i for i, line in enumerate(lines) if line.strip() == END)
    body = lines[start:end]
    indent = min(
        (len(line) - len(line.lstrip()) for line in body if line.strip()), default=0
    )
    return "\n".join(line[indent:] if line.strip() else "" for line in body) + "\n"


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("mode", choices=["format", "check-format", "check"])
    parser.add_argument("--root", type=Path, default=Path.cwd())
    args = parser.parse_args()
    from common import source_files
    from java import formatter_command

    root = args.root.resolve()
    paths = [root / p for p in source_files(root) if Path(p).suffix == ".qmd"]
    texts = {p: p.read_bytes().decode("utf-8") for p in paths}
    blocks = [block for path, text in texts.items() for block in fences(path, text)]
    kinds, errors = classify(blocks)
    exclusions_path = root / "tools" / "quality" / "qmd" / "exclusions.json"
    exclusions: dict[str, dict[str, str]] = (
        json.loads(exclusions_path.read_text()) if exclusions_path.exists() else {}
    )
    failures: list[str] = []
    coverage: Counter[str] = Counter()
    accepted: list[tuple[Fence, str, str]] = []
    for block, kind, error in zip(blocks, kinds, errors, strict=True):
        key = f"{block.path.relative_to(root).as_posix()}#{block.number}"
        if kind is None:
            exclusion = exclusions.get(key)
            if (
                exclusion
                and exclusion.get("sha256") == block.digest
                and exclusion.get("reason")
            ):
                coverage["reviewed " + exclusion.get("category", "invalid")] += 1
                print(f"{key}: сохранён ({exclusion['reason']})")
            else:
                failures.append(
                    f"{key}:{block.line}: unclassified Java fragment: {error}"
                )
        else:
            coverage[kind.split(":")[0]] += 1
            if kind.startswith("mixed:"):
                cut = int(kind.split(":")[1])
                accepted.append((block, "unit", block.source[:cut]))
                accepted.append((block, "statements", block.source[cut:]))
            else:
                accepted.append((block, kind, block.source))
    actual = {
        f"{b.path.relative_to(root).as_posix()}#{b.number}": (b, kind)
        for b, kind in zip(blocks, kinds, strict=True)
    }
    for key, exclusion in exclusions.items():
        current = actual.get(key)
        if (
            current is None
            or current[0].digest != exclusion.get("sha256")
            or current[1] is not None
        ):
            failures.append(
                f"{key}: stale reviewed Java exclusion; review or remove its exact entry"
            )
    changed: list[str] = []
    with tempfile.TemporaryDirectory(prefix="qmd-quality-") as tmp:
        files: list[Path] = []
        for i, (_block, kind, source) in enumerate(accepted):
            path = Path(tmp) / f"Snippet{i}.java"
            path.write_text(wrap(source, kind))
            files.append(path)
        # GJF may expose a second whitespace simplification on a later pass.
        # Iterate its own formatting to a bounded fixed point, never custom edits.
        for _iteration in range(4):
            before_pass = [path.read_bytes() for path in files]
            for start in range(0, len(files), 100):
                proc = subprocess.run(
                    [
                        *formatter_command(),
                        "--replace",
                        *map(str, files[start : start + 100]),
                    ],
                    text=True,
                    capture_output=True,
                )
                if proc.returncode:
                    failures.append(proc.stderr.strip() or proc.stdout.strip())
            if before_pass == [path.read_bytes() for path in files]:
                break
        else:
            failures.append(
                "google-java-format did not reach a fixed point in four passes"
            )
        replacements: dict[Path, list[tuple[int, int, str]]] = {}
        verify_sources = [wrap(source, kind) for _, kind, source in accepted]
        # Validate the emitted body after removing temporary wrappers too.
        verify_sources += [
            wrap(unwrap(path.read_text(), kind), kind)
            for (_, kind, _), path in zip(accepted, files, strict=True)
        ]
        verified = syntax_records(verify_sources)
        before, after = verified[: len(files)], verified[len(files) :]
        for (block, _, _), old, new in zip(accepted, before, after, strict=True):
            if old[0] or new[0] or old[1] != new[1]:
                failures.append(
                    f"{block.path}:{block.line}: formatter changed Java AST/literal values"
                )
        outputs: dict[tuple[Path, int], list[str]] = {}
        for (block, kind, _source), path in zip(accepted, files, strict=True):
            formatted = unwrap(path.read_text(), kind)
            outputs.setdefault((block.path, block.number), []).append(formatted)
        for block, kind in zip(blocks, kinds, strict=True):
            if kind is None:
                continue
            formatted = "".join(outputs[(block.path, block.number)])
            # Formatter is permitted to change whitespace, never Java tokens.
            if re.sub(r"\s+", "", formatted) != re.sub(r"\s+", "", block.source):
                failures.append(
                    f"{block.path}:{block.line}: formatter changed non-whitespace content"
                )
                continue
            if formatted != block.source:
                changed.append(f"{block.path.relative_to(root)}:{block.line}")
                prefix = block.prefix
                replacement = "".join(
                    prefix + line + block.newline for line in formatted.splitlines()
                )
                replacements.setdefault(block.path, []).append(
                    (block.start, block.end, replacement)
                )
        if args.mode == "format" and not failures:
            for path, edits in replacements.items():
                text = texts[path]
                for start, end, replacement in sorted(edits, reverse=True):
                    text = text[:start] + replacement + text[end:]
                path.write_bytes(text.encode("utf-8"))
    print(
        "Java fences: "
        + ", ".join(f"{key}={value}" for key, value in sorted(coverage.items()))
    )
    if changed:
        print(
            f"{len(changed)} Java fences {'formatted' if args.mode == 'format' and not failures else 'need formatting'}"
        )
        if args.mode != "format":
            print("\n".join(changed))
    for failure in failures:
        print(failure, file=sys.stderr)
    return int(bool(failures or (changed and args.mode != "format")))


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, RuntimeError, ValueError) as exc:
        print(f"qmd: {exc}", file=sys.stderr)
        raise SystemExit(1) from exc
