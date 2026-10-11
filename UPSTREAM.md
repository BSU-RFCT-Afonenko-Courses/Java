# Источники установленных расширений

Пакеты устанавливаются целиком штатным `quarto add`; `providers.json` закрепляет
immutable source commits и scopes, а `installed-packages.json` — полный состав,
SHA256, размеры и Git modes. Реализация vendored `_extensions` вручную не меняется.

| Пакеты | Фактический опубликованный выпуск | Exact source commit |
| --- | --- | --- |
| Core / Presentation / Navigation | Core v5.0.1 | d9c764823beee6ea45af1d7093933382aafce663 |
| Download | v3.0.0 | 6fb3945020cd74fda40cc4d333389864e06609b5 |
| PrairieLearn exporter | v5.0.0 | 4a83958ed6ad3902dc23cfd6be01944ff2f61b65 |
| Course Site | v5.0.0 | 215309b5c41669e56a857a1bc3e4f7f2ce782c5f |
| Reference Catalog | v3.0.0 | 559583805a514ae8a244b6ea4cb5124867064024 |

Core, Presentation и QRC установлены в корне и пяти учебных частях. Course Site
находится в корне; Download и exporter — в tasks; Navigation — в lectures,
practice и tasks для native index. Whole packages обновляются существующим
provider interface:

```sh
python3 tools/sync-providers.py --providers-root /absolute/provider/repositories
```

Java использует release25, официальный JUnit console1.14.1/Jupiter5.14.1, общий
Platform runner и versioned java25-junit-v1/java25-mutation-v1 registry. Check
schemas имеют version1; compiler/student failures и infrastructure outcomes
разделены. Host diagnostic, container authoritative. Runtime/source/version
pins должны подтверждаться owner receipt и actual registry digest.

Текущая Platform metadata version1.0.1 закреплена на normal merged main source
`0083e3e102f47860cabc1d1e62e6434353f5c4c6`. Образы фактически опубликованы: Java
`ghcr.io/afonenko-course-tools/java25-grader@sha256:1084c3e254105383d476303a4983d70abef58cb046a4899f05339f1d13c9a9a6` и Community
`ghcr.io/afonenko-course-tools/prairielearn-community-gateway@sha256:a1fd72637587782766d8f2f1fd37fad23b8a56ade7f5754b7f2f3bcfda50bc34`. Publication run38061254263 собирал
image source `756d9dfb96fcb43cb02d499594a7408d174be16c`; runner source hash
`3e6d45c783e3972aa02c294312244f6f81c284897418954a7653cf64bfe4a051`.
Source runtime registry и durable publication assets независимо совпадают.
Source tag v1.0.1 ещё не опубликован: CI/toolchain.json содержит tag:null и
workflow не проходит release gate до фактического тега. Анонимный pull GHCR
ожидает изменения видимости пакетов владельцем; publication receipt не
подтверждает публичную переносимость.

Исторический Platform v1.0.0 source tag остаётся
`d85e16ef11ff2cc22b9bc1afdfbc76e5cda609be`; его прежние receipts не являются
приёмкой текущего feedback runtime.

CI закрепляет Quarto 1.11.5, CUE 0.17.1 и Java 25 в CI/toolchain.json и workflow.
Source/OCI pins разделены: source release содержит окончательные published digests,
а image provenance сохраняет фактический исходный commit сборки образа.
