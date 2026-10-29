"""Малая исследовательская проверка служебного origin-tree; не producer курса.

Явный запуск: python3 native-origin-shadow-probe.py /absolute/path/to/quarto.
Только /tmp, штатный inspect, без render/execute/hooks. JSON здесь — подмножество
YAML для независимости fixture от Python YAML-библиотеки; CST extraction не проверен.
"""
from pathlib import Path
import copy
import hashlib
import json
import os
import subprocess
import sys
import tempfile

executable = str(Path(sys.argv[1]).resolve())
root = Path(tempfile.mkdtemp(prefix="authoring-native-origin-20261029-"))
trace_key = "course-origin-trace-research"
input_name = "projects/task/_course-input.qmd"
cases = ["scalar-maps-rebasing", "active-profile", "array-dedup-counterexample"]
all_results = []

for case in cases:
    directory = root / case
    directory.mkdir()
    configs = {
        "_quarto.yml": {
            "project": {"type": "default", "render": [input_name]},
            "format": "html", "execute": {"enabled": False},
            "course-project": {
                "components": {
                    "shared": {"sources": "helper", "label": "сохранено из root"},
                    "glob": {"sources": "missing/**/*.java"},
                    "profiled": {"sources": "helper"},
                },
                "starter": "src",
                "delivery": {"student": {"testsets": {"demo": True}}},
            },
        },
        "projects/_metadata.yml": {
            "course-project": {
                "components": {"shared": {"sources": "helper"}},
                "starter": "src",
            },
        },
        "projects/task/_metadata.yml": {
            "course-project": {"delivery": {"student": {"testsets": {"demo": False}}}},
        },
    }
    if case == "active-profile":
        configs["_quarto-originprobe.yml"] = {
            "course-project": {"components": {"profiled": {"sources": "profile-helper"}}},
        }
    if case == "array-dedup-counterexample":
        for name in ("_quarto.yml", "projects/_metadata.yml"):
            configs[name]["course-project"]["components"]["array-example"] = {
                "sources": ["missing/**/*.java"]
            }
    # Сохраняем существенное наличие путей на каждом уровне, включая decoy.
    fixtures = {
        input_name: "---\n---\n",
        "helper/A.java": "// root\n",
        "projects/helper/A.java": "// parent\n",
        "projects/task/helper/A.java": "// decoy\n",
        "profile-helper/A.java": "// profile\n",
        "src/Main.java": "// root convention\n",
        "projects/src/Main.java": "// parent convention\n",
        "projects/task/src/Main.java": "// project-local convention\n",
    }
    for name, content in fixtures.items():
        file = directory / name
        file.parent.mkdir(parents=True, exist_ok=True)
        file.write_text(content, encoding="utf-8")
    origins = {}
    next_id = [100]

    def shadow(value, source, path):
        if isinstance(value, dict):
            return {key: shadow(child, source, path + [key]) for key, child in value.items()}
        if isinstance(value, list):
            return [shadow(child, source, path + [index]) for index, child in enumerate(value)]
        next_id[0] += 1
        marker = next_id[0]
        origins[str(marker)] = {"file": source, "path": path, "raw": value}
        return marker

    runs = []
    snapshots = {}
    for instrumented in (False, True):
        label = "shadow" if instrumented else "baseline"
        hashes = {}
        for name, config in configs.items():
            output = copy.deepcopy(config)
            if instrumented:
                output[trace_key] = shadow(config["course-project"], name, ["course-project"])
            content = json.dumps(output, ensure_ascii=False, indent=2) + "\n"
            file = directory / name
            file.parent.mkdir(parents=True, exist_ok=True)
            file.write_text(content, encoding="utf-8")
            hashes[name] = hashlib.sha256(content.encode()).hexdigest()
            (directory / (name.replace("/", "__") + "." + label + ".input.json")).write_text(content, encoding="utf-8")
        environment = dict(os.environ)
        environment["XDG_CACHE_HOME"] = str(directory / "_cache")
        command = [executable, "inspect", str(directory / input_name)]
        if case == "active-profile":
            command += ["--profile", "originprobe"]
        result = subprocess.run(command, cwd=directory, env=environment, capture_output=True, text=True, timeout=35)
        (directory / (label + ".stdout.json")).write_text(result.stdout, encoding="utf-8")
        (directory / (label + ".stderr")).write_text(result.stderr, encoding="utf-8")
        value = json.loads(result.stdout) if result.returncode == 0 else {}
        metadata = value.get("formats", {}).get("html", {}).get("metadata", {})
        snapshots[label] = metadata
        runs.append({"label": label, "argv": command, "exit": result.returncode,
                     "version": value.get("quarto", {}).get("version"),
                     "stderr": result.stderr[:1000], "config_sha256": hashes})
    clean_shadow = {key: value for key, value in snapshots["shadow"].items() if key != trace_key}
    trace = snapshots["shadow"].get(trace_key, {})
    all_results.append({
        "case": case, "runs": runs,
        "effective_metadata_equal_without_trace": snapshots["baseline"] == clean_shadow,
        "effective_course_project": snapshots["shadow"].get("course-project"),
        "effective_trace": trace, "origin_index": origins,
        "fixture_sha256": {name: hashlib.sha256(content.encode()).hexdigest() for name, content in fixtures.items()},
    })

summary = {"root": str(root), "cases": all_results}
(root / "summary.json").write_text(json.dumps(summary, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
print(json.dumps(summary, ensure_ascii=False, indent=2))
