import json
import os
from pathlib import Path
import resource
import signal
import subprocess
import time

root = Path(__file__).resolve().parent
platform = root.parent.parent / "platform"
perf = root.parent.parent / "performance-lifecycle"


def snapshot():
    def git(*args):
        return subprocess.run(["git", *args], cwd=platform, capture_output=True, text=True, check=True).stdout
    return {"head": git("rev-parse", "HEAD").strip(), "status": git("status", "--porcelain=v1"), "candidate": "dirty candidate; другой writer активен"}


before = snapshot()
(root / "platform-source-before.json").write_text(json.dumps(before, indent=2, ensure_ascii=False) + "\n")
argv = ["/opt/quarto/bin/tools/x86_64/deno", "run", "--config", str(perf / "deno.json"), "--lock", str(perf / "deno.lock"), "--frozen", "--node-modules-dir=manual", "--cached-only", "-A", str(root / "run-platform.ts")]
observer_before = resource.getrusage(resource.RUSAGE_SELF)
start = time.monotonic()
timed_out = False
usage = None
with (root / "platform.stdout").open("wb") as out, (root / "platform.stderr").open("wb") as err:
    child = subprocess.Popen(argv, cwd=platform, stdin=subprocess.DEVNULL, stdout=out, stderr=err, start_new_session=True)
    while True:
        pid, status, current = os.wait4(child.pid, os.WNOHANG)
        if pid:
            child.returncode = os.waitstatus_to_exitcode(status)
            usage = current
            break
        if time.monotonic() - start > 20:
            timed_out = True
            os.killpg(child.pid, signal.SIGKILL)
            deadline = time.monotonic() + 2
            while time.monotonic() < deadline:
                pid, status, current = os.wait4(child.pid, os.WNOHANG)
                if pid:
                    child.returncode = os.waitstatus_to_exitcode(status)
                    usage = current
                    break
                time.sleep(0.01)
            break
        time.sleep(0.01)
wall = time.monotonic() - start
observer_after = resource.getrusage(resource.RUSAGE_SELF)
result = {"argv": argv, "cwd": str(platform), "outerExit": child.returncode, "timeout": timed_out, "wallSeconds": wall, "capSeconds": 22, "cpuUserSeconds": usage.ru_utime if usage else None, "cpuSystemSeconds": usage.ru_stime if usage else None, "peakRssKiB": usage.ru_maxrss if usage else None, "resourceScope": "Linux wait4: весь вызов Performance с дочерними процессами", "observerCpuSeconds": observer_after.ru_utime + observer_after.ru_stime - observer_before.ru_utime - observer_before.ru_stime, "observerPeakRssKiB": observer_after.ru_maxrss}
(root / "platform-outer.json").write_text(json.dumps(result, indent=2, ensure_ascii=False) + "\n")
(root / "platform-source-after.json").write_text(json.dumps(snapshot(), indent=2, ensure_ascii=False) + "\n")
print(json.dumps(result, indent=2, ensure_ascii=False))
