#!/usr/bin/env bash
set -euo pipefail
cd /PrairieLearn
make -s start-support
node apps/prairielearn/dist/server.js --config /feedback-config/pl.json --migrate-and-exit
node apps/prairielearn/dist/server.js --config /feedback-config/pl.json &
feedback_pl_pid=$!
feedback_worker_pid=''
trap 'kill "$feedback_pl_pid" ${feedback_worker_pid:-} 2>/dev/null || true' EXIT INT TERM
for feedback_attempt in {1..60}; do
  if curl -fsS http://127.0.0.1:3000/pl/webhooks/ping >/dev/null; then break; fi
  kill -0 "$feedback_pl_pid"
  sleep 1
done
curl -fsS http://127.0.0.1:3000/pl/webhooks/ping >/dev/null
node apps/workspace-host/dist/interface.js --config /feedback-config/worker.json &
feedback_worker_pid=$!
wait -n "$feedback_pl_pid" "$feedback_worker_pid"
