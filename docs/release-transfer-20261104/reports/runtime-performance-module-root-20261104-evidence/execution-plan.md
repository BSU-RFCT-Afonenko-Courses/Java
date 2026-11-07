# Проверка frozen module-root candidate

До исполнения required: один Sourcewriter frozen, независимый Source review APPROVE. Base9ba1fd87; изменён только src/launchers/setup.ts. Никаких новых content hash pins/registry. Config/lock прежние, штатные node_modules уже установлены; setup0.716354009/20с не повторять.

После APPROVE ровноодин targeted affected graph:

```
/opt/quarto/bin/tools/x86_64/deno check --config deno.json --lock deno.lock --frozen --node-modules-dir=manual --cached-only src/launchers/setup.ts src/launchers/tool.ts src/launchers/pandoc-data.ts src/runner/diagnostic.ts
```

Полный внешний cap8с, результат и fullwall сохраняются. Это изменённый setup и его прямые producer/readers, не fullrepo suite. Первыйновыйfailure стоп, без retry.

Только при graphPASS — один real synthetic summary прежнего QMD. Старый fullwall1-го<=15с +2-йfull1.607с округлён вверх1.608 => из прежнегоsummarycap23с доступно6.392с. Выбран внешний wholePython timeout6.3с (оставляет0.092с дополнительного запаса), childwatchdog4.8с+cleanup1с+allowance0.5с. Full invocation Bash time timed, включая fixtures/setup/cleanup/overhead. Не считать толькоCLImeasure. Никаких новых setups/network/VM/TERM/directoff или широкихtests. Firstnewfailure стоп.

Из фактов читатьactualcoverage/observedagents/counters/spans, dataValid и Native receipt отдельно от wrapper, стоимость observer и HTML comparison с уже существующим NativeHTML. Не объявлять всефазы/fullcourseoverhead/speedup по1tinyfixture. При отсутствиифаз так и сохранить.
