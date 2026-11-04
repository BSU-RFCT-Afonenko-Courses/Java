import json
import os
from pathlib import Path
import resource
import signal
import subprocess
import time

ROOT = Path(__file__).resolve().parent
PERF = ROOT.parent.parent / "performance-lifecycle"
env = os.environ.copy()
env["XDG_CACHE_HOME"] = "/home/tolya/JavaCodex/.cache"
env["TMPDIR"] = str(ROOT / "tmp")
(ROOT / "tmp").mkdir(exist_ok=True)
source = "---\ntitle: Small public lifecycle render\nformat:\n  html:\n    theme: none\n    toc: false\n---\n\n# Result\n\nNative Quarto completed this one-page document.\n"
for name in ("native", "wrapper"):
    directory = ROOT / name
    directory.mkdir(exist_ok=True)
    (directory / "index.qmd").write_text(source)


def measure(name, argv, limit):
    cwd = ROOT / name
    before = resource.getrusage(resource.RUSAGE_SELF)
    command = argv
    start = time.monotonic()
    snapshots = []
    seen = set()
    timeout = False
    with (ROOT / (name + ".stdout")).open("wb") as out, (ROOT / (name + ".stderr")).open("wb") as err:
        process = subprocess.Popen(command, cwd=cwd, env=env, stdin=subprocess.DEVNULL, stdout=out, stderr=err, start_new_session=True)
        child_usage = None
        while True:
            pid, status, usage = os.wait4(process.pid, os.WNOHANG)
            if pid:
                process.returncode = os.waitstatus_to_exitcode(status)
                child_usage = usage
                break
            if name == "wrapper":
                for path in (ROOT / "tmp").glob("quarto-course-perf-*/run.json"):
                    try:
                        raw = path.read_text()
                        record = json.loads(raw)
                    except (FileNotFoundError, json.JSONDecodeError):
                        continue
                    key = (record["runId"], record["completion"]["kind"])
                    if key not in seen:
                        seen.add(key)
                        snapshots.append({"seconds": time.monotonic() - start, "path": str(path), "record": record})
            if time.monotonic() - start > limit:
                timeout = True
                os.killpg(process.pid, signal.SIGKILL)
                deadline = time.monotonic() + 2
                while time.monotonic() < deadline:
                    pid, status, usage = os.wait4(process.pid, os.WNOHANG)
                    if pid:
                        process.returncode = os.waitstatus_to_exitcode(status)
                        child_usage = usage
                        break
                    time.sleep(0.01)
                break
            time.sleep(0.01)
        code = process.returncode
    wall = time.monotonic() - start
    after = resource.getrusage(resource.RUSAGE_SELF)
    result = {"argv": argv, "cwd": str(cwd), "limitSeconds": limit, "wallSeconds": wall, "wrapperExit": code, "timeout": timeout, "outerResourcesSource": "Linux wait4 child rusage", "cpuUserSeconds": child_usage.ru_utime if child_usage else None, "cpuSystemSeconds": child_usage.ru_stime if child_usage else None, "peakRssKiB": child_usage.ru_maxrss if child_usage else None, "observerCpuUserSeconds": after.ru_utime - before.ru_utime, "observerCpuSystemSeconds": after.ru_stime - before.ru_stime, "observerPeakRssKiB": after.ru_maxrss, "snapshots": snapshots}
    (ROOT / (name + "-outcome.json")).write_text(json.dumps(result, indent=2) + "\n")
    return result


native = measure("native", ["/usr/bin/quarto", "render", "index.qmd", "--no-execute", "--quiet"], 8)
if native["timeout"] or native["wrapperExit"]:
    raise SystemExit("Native baseline incomplete/failed; no wrapper retry")
wrapper = measure("wrapper", ["/opt/quarto/bin/tools/x86_64/deno", "run", "--config", str(PERF / "deno.json"), "--lock", str(PERF / "deno.lock"), "--frozen", "--node-modules-dir=manual", "--cached-only", "-A", str(PERF / "src/cli.ts"), "render", "--perf-mode", "off", "--perf-output", str(ROOT / "runs"), "index.qmd", "--no-execute", "--quiet"], 12)
files = list((ROOT / "runs").glob("*/run.json"))
record = json.loads(files[0].read_text()) if len(files) == 1 else None
comparison = {"native": native, "wrapper": wrapper, "finalRunPath": str(files[0]) if len(files) == 1 else None, "finalRecord": record, "htmlBytesEqual": (ROOT / "native/index.html").read_bytes() == (ROOT / "wrapper/index.html").read_bytes() if (ROOT / "wrapper/index.html").exists() else False, "pairWallSeconds": native["wallSeconds"] + wrapper["wallSeconds"], "capSeconds": 24}
(ROOT / "public-pair.json").write_text(json.dumps(comparison, indent=2) + "\n")
print(json.dumps({k: comparison[k] for k in ("finalRunPath", "htmlBytesEqual", "pairWallSeconds", "capSeconds")}))
