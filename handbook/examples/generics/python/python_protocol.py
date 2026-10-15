from collections.abc import Sequence
from typing import Protocol


class Arithmetic[T](Protocol):
    def zero(self) -> T: ...
    def add(self, a: T, b: T) -> T: ...
    def multiply(self, a: T, b: T) -> T: ...


def dot[T](left: Sequence[T], right: Sequence[T], arithmetic: Arithmetic[T]) -> T:
    if len(left) != len(right):
        raise ValueError("Different vector lengths")
    result = arithmetic.zero()
    for a, b in zip(left, right):
        result = arithmetic.add(result, arithmetic.multiply(a, b))
    return result


class FloatArithmetic:
    def zero(self) -> float: return 0.0
    def add(self, a: float, b: float) -> float: return a + b
    def multiply(self, a: float, b: float) -> float: return a * b


class ComplexArithmetic:
    def zero(self) -> complex: return 0j
    def add(self, a: complex, b: complex) -> complex: return a + b
    def multiply(self, a: complex, b: complex) -> complex: return a * b


if __name__ == "__main__":
    print(dot([1.0, 2.0], [3.0, 4.0], FloatArithmetic()))
    print(dot([1 + 1j, 2 + 0j], [3 + 0j, 4 - 1j], ComplexArithmetic()))
