# Удалённые PR для продолжения

Главный пакет: https://github.com/BSU-RFCT-Afonenko-Courses/Java/pull/33 — codex/release-transfer-20261104. Начать с START-HERE.md.

| Компонент | PR | Точный Source commit |
|---|---|---|
|Core актуальный|https://github.com/Afonenko-Course-Tools/quarto-course/pull/71|010ba917909fa7d44179d4a1a05291f88f061775|
|Site актуальный|https://github.com/Afonenko-Course-Tools/quarto-project-publish/pull/21|2b397815cfdbf0e860405559de68067fe20204b4|
|PrairieLearn|https://github.com/Afonenko-Course-Tools/quarto-course-prairielearn/pull/36|1ad5516e543d07804aa09e426302413c21542e3e|
|Download|https://github.com/Afonenko-Course-Tools/quarto-project-download/pull/19|09e797a1f56bdeb31a66c3e2afa7b8aba7f90321|
|QRC|https://github.com/Afonenko-Course-Tools/quarto-reference-catalog/pull/22|541a44836cf5ec934a101059cbee77c9ff47d134|
|SystemPractice|https://github.com/BSU-RFCT-Afonenko-Courses/System-Practice/pull/10|d30345098dcfb1da1530eca5021319bf91fe0f75|
|Platform WIP|https://github.com/Afonenko-Course-Tools/prairielearn-platform/pull/35|6616222206d3e0843ebc560af38c2dc3519557f0|
|Performance lifecycle|https://github.com/Afonenko-Course-Tools/quarto-course-performance/pull/6|7a1645936924ba0dd710c9121a8871a13d67a84b|
|Performance TERM, НЕПРИНЯТЫЙ архив|https://github.com/Afonenko-Course-Tools/quarto-course-performance/pull/7|111f09dfa54cdf76a97c9294aeb0fdae88bb8e78|

Gateway901624527dce2d37a62fedaa9b768cbbbfd1e366 уже main; сохранена remote веткаcodex/java-release-20261110. Отдельного diff для PR нет.

Старые PR32/70 сохраняются. Core PR70 — прежнее направление, актуальный Source находится вPR71. Superseded Core d8cf30e и SiteSP dcb59dd сохранены отдельно как bundles/archives; не применять их поверх актуальных. Root также сохранил oldCore remote веткуcodex/checkpoint-core-frozen-20261104 без force-push существующего PR.

Поздние успешные Core/Site pushes заменяют статус transport blocked из первоначального publish-source-branches report. Никаких merge/deploy/VM/database изменений; checkpoint не делает непроверенные кандидаты принятыми.
