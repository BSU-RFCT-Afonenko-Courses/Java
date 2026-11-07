# Единственная коррекция CLI затронутого graph

Прежний unsupported --cached-only failure0.006с сохранён и не является Sourcecheck. По прямому разрешению меняются только flags того же rootgraph, Source/roots/lock/setupне меняются. Help именно /opt/quarto/bin/tools/x86_64/deno check подтверждает --deny-import: deny importing from remote hosts; default cacheddependencies не re-download без reload. Используются --deny-import, frozenlock, existing manualnode_modules, никаких новыхdependencies/installs/downloads.

Один исправленный compiler invocation: внешний timeout7.9s, fullwall timed. С прежними0.006с это впределах исходногоgraphcap8с с allowance. Любойfailure/timeout — stop, никакойещёcorrection. После фактическогоPASS толькоодинtiny summary, full externalcap<=6s в остатке summary6.392s, всего23sне расширять. Setup0.716354009/20 прежний.
