# Adaptive effort controls — 2026-11-03

Admission: delegated service task; selected role Sol 6.1 / low / Standard.
Scope: inspect documented local app-tool controls; do not change runtime settings.
Read: openai-docs/SKILL.md and available app-tool metadata.
No global config, automation, chat setting, or other chat was changed.

The available tools expose no dedicated setter for this active turn's reasoning effort.
`send_message_to_thread` accepts `threadId`, `prompt`, optional `model`, and
`thinking: "medium"`, but sends a new message and requires explicit messaging
authorization. It is not an authorized settings-only workaround here.

`automation_update` documents `reasoningEffort` for cron creation only.
The documented heartbeat creation fields omit `model` and `reasoningEffort`;
the exposed update alternatives are opaque (`unknown`). Thus this schema does
not establish a supported heartbeat effort setter or inheritance guarantee.

Minimal user action: select Medium in the current chat's model/reasoning control
before its next turn. Exact UI labels/location and heartbeat inheritance were
not verified locally; no claim that this changes an already running turn.

Sources: available `mcp__codex_app__automation_update`,
`mcp__codex_app__send_message_to_thread`, and `create_thread` metadata.
No network search: explicit task boundary requested local evidence only.
