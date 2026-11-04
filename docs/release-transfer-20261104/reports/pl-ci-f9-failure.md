# CI PL f9: причины отказа

Run41680971465, headf9c00aed6de610dce7d9b7cb6e50c28ebfb9573e, 3ноября2026. Повторный workflow не запускался. Оба jobs failed;106existingtests и cross-filesystem test прошли.

Первый job: envless public Core gate regression: functionalProfiles читает QUARTO_PROFILE до refusal Core. Второй: existing deno test --no-config не получает новый type-only import map. Ни --no-check, ни ослабление gate не являются решением.

## Gate excerpt

```text
2026-11-03T17:07:02.4329132Z env:
2026-11-03T17:07:02.4329330Z   BUNDLE_EXT: linux-amd64.deb
2026-11-03T17:07:02.4329571Z ##[endgroup]
2026-11-03T17:07:02.9678326Z error: Uncaught (in promise) AssertionError: The input did not match the regular expression /PUBLIC_CORE_GATE_REFUSAL/. Input:
2026-11-03T17:07:02.9679286Z 
2026-11-03T17:07:02.9680077Z '\x1b[0m\x1b[1m\x1b[31merror\x1b[0m: Uncaught (in promise) NotCapable: Requires env access to "QUARTO_PROFILE", run again with the --allow-env flag\n' +
2026-11-03T17:07:02.9681372Z   '    (Deno.env.get("QUARTO_PROFILE") || "").split(",")).filter((p) =>\n' +
2026-11-03T17:07:02.9682038Z   '\x1b[0m\x1b[31m              ^\x1b[0m\n' +
2026-11-03T17:07:02.9684224Z   '    \x1b[0m\x1b[2m\x1b[38;5;245mat \x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245mObject.getEnv [as get]\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245m (\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245mext:deno_os/30_os.js\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245m:\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245m124\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245m:\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245m10\x1b[0m\x1b[0m\x1b[2m\x1b[38;5;245m)\x1b[0m\n' +
```

## Compiler diagnostics

```text
2026-11-03T17:08:24.7940725Z Check tests/native-auxiliary.test.ts
2026-11-03T17:08:26.5251599Z TS2307 [ERROR]: Import "course-core/public.ts" not a dependency
2026-11-03T17:08:26.5252569Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-adapter.ts:1:34
2026-11-03T17:08:26.5253155Z 
2026-11-03T17:08:26.5253379Z TS2307 [ERROR]: Import "course-core/public.ts" not a dependency
2026-11-03T17:08:26.5254301Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:1:34
2026-11-03T17:08:26.5254881Z 
2026-11-03T17:08:26.5255102Z TS2307 [ERROR]: Import "course-core/capture.ts" not a dependency
2026-11-03T17:08:26.5255931Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:6:65
2026-11-03T17:08:26.5256497Z 
2026-11-03T17:08:26.5256719Z TS7006 [ERROR]: Parameter 'input' implicitly has an 'any' type.
2026-11-03T17:08:26.5257066Z     !inputs.length || inputs.some((input) =>
2026-11-03T17:08:26.5257386Z                                    ~~~~~
2026-11-03T17:08:26.5258495Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-adapter.ts:77:36
2026-11-03T17:08:26.5259074Z 
2026-11-03T17:08:26.5259290Z TS7006 [ERROR]: Parameter 'input' implicitly has an 'any' type.
2026-11-03T17:08:26.5259857Z     ) || new Set(inputs.map((input) => input.source)).size !== inputs.length
2026-11-03T17:08:26.5260233Z                              ~~~~~
2026-11-03T17:08:26.5261002Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-adapter.ts:80:30
2026-11-03T17:08:26.5261599Z 
2026-11-03T17:08:26.5261831Z TS7006 [ERROR]: Parameter 'pending' implicitly has an 'any' type.
2026-11-03T17:08:26.5262181Z   }, async (pending) => {
2026-11-03T17:08:26.5262422Z             ~~~~~~~
2026-11-03T17:08:26.5263219Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-adapter.ts:167:13
2026-11-03T17:08:26.5263820Z 
2026-11-03T17:08:26.5264049Z TS7006 [ERROR]: Parameter 'observed' implicitly has an 'any' type.
2026-11-03T17:08:26.5264576Z       prepare: async (observed) => {
2026-11-03T17:08:26.5264904Z                       ~~~~~~~~
2026-11-03T17:08:26.5265717Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:233:23
2026-11-03T17:08:26.5266284Z 
2026-11-03T17:08:26.5266508Z TS7006 [ERROR]: Parameter 'scope' implicitly has an 'any' type.
2026-11-03T17:08:26.5266784Z   }, async (scope) => {
2026-11-03T17:08:26.5266949Z             ~~~~~
2026-11-03T17:08:26.5267424Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:248:13
2026-11-03T17:08:26.5267787Z 
2026-11-03T17:08:26.5267927Z TS7006 [ERROR]: Parameter 'lease' implicitly has an 'any' type.
2026-11-03T17:08:26.5268229Z     await scope.withPreparedOperation(async (lease) => {
2026-11-03T17:08:26.5268606Z                                              ~~~~~
2026-11-03T17:08:26.5269176Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:249:46
2026-11-03T17:08:26.5269732Z 
2026-11-03T17:08:26.5269957Z TS7006 [ERROR]: Parameter 'i' implicitly has an 'any' type.
2026-11-03T17:08:26.5270311Z         JSON.stringify(prepared.inputs.map((i) => i.source)) !==
2026-11-03T17:08:26.5270664Z                                             ^
2026-11-03T17:08:26.5271411Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:261:45
2026-11-03T17:08:26.5271976Z 
2026-11-03T17:08:26.5272185Z TS7006 [ERROR]: Parameter 'p' implicitly has an 'any' type.
2026-11-03T17:08:26.5272535Z             prepared.identity.profiles.filter((p) =>
2026-11-03T17:08:26.5272884Z                                                ^
2026-11-03T17:08:26.5273660Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-entry.ts:272:48
2026-11-03T17:08:26.5274225Z 
2026-11-03T17:08:26.5274420Z TS7006 [ERROR]: Parameter 'd' implicitly has an 'any' type.
2026-11-03T17:08:26.5274762Z     declarations: input.declarations.map((d) => ({
2026-11-03T17:08:26.5275094Z                                           ^
2026-11-03T17:08:26.5276076Z     at file:///home/runner/work/quarto-course-prairielearn/quarto-course-prairielearn/_extensions/course-prairielearn/application/native-input.ts:37:43
2026-11-03T17:08:26.5276642Z 
2026-11-03T17:08:26.5276724Z Found 12 errors.
2026-11-03T17:08:26.5276835Z 
```

Scope следующей партии: восстановить gate-before-env и portable mapping в реальных existing compiler entries; Source writer PL владеет следующей типизацией и не смешивает runtime fix в неё. Полные suite/render/image локально не повторять.

