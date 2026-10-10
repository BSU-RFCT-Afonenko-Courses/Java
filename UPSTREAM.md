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

Platform v1.0.0 опубликован из source commit
`d85e16ef11ff2cc22b9bc1afdfbc76e5cda609be`. Runtime profiles закрепляют
`ghcr.io/afonenko-course-tools/java25-grader@sha256:c464389a45e5e073b3d11844e23a5c295b4c1c345ca7bc9937618d9abde36bf3`.
Community runtime опубликован как
`ghcr.io/afonenko-course-tools/prairielearn-community-gateway@sha256:124e1e90f755184f868ed45bfa0d2c48552f154e33cdae8bc757df48abcd92a7`.
Publication run 38057307347 собирал image source fc5a4d4e9d0795940b8cdf68e30e753ebfd27cec,
runner source hash d0c94460f1bd23200cf05aa93bc9c9090126fe624ec38e000be8da96e02d6ce1.
Tag и все Java profile digests независимо проверены по опубликованному Git source.
Анонимный pull GHCR ещё ожидает изменения видимости пакетов владельцем; publication
receipt сам по себе не подтверждает публичную переносимость.

CI закрепляет Quarto 1.11.5, CUE 0.17.1 и Java 25 в CI/toolchain.json и workflow.
Source/OCI pins разделены: source release содержит окончательные published digests,
а image provenance сохраняет фактический исходный commit сборки образа.
