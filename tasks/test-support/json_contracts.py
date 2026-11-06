"""Проверяемые JSON-значения на границе локальных служебных команд."""
from collections.abc import Mapping, Sequence
import json
from pathlib import Path
from typing import TypeGuard


def _mapping(value: object) -> TypeGuard[Mapping[object, object]]:
    return isinstance(value, Mapping)


def _list(value: object) -> TypeGuard[Sequence[object]]:
    return isinstance(value, list)


def record(value: object, label: str) -> dict[str, object]:
    if not _mapping(value):
        raise ValueError(label + ': нужен JSON-объект')
    result: dict[str, object] = {}
    for key, item in value.items():
        if not isinstance(key, str):
            raise ValueError(label + ': нужен строковый ключ')
        result[key] = item
    return result


def read_record(path: Path) -> dict[str, object]:
    value: object = json.loads(path.read_text())
    return record(value, str(path))


def items(value: object, label: str) -> list[object]:
    if not _list(value):
        raise ValueError(label + ': нужен JSON-массив')
    return list(value)


def text(value: object, label: str) -> str:
    if not isinstance(value, str):
        raise ValueError(label + ': нужна строка')
    return value


def texts(value: object, label: str) -> list[str]:
    return [text(item, label) for item in items(value, label)]
