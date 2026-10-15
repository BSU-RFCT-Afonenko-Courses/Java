from collections.abc import Sequence

Number = float | complex


def dot(left: Sequence[Number], right: Sequence[Number]) -> Number:
    if len(left) != len(right):
        raise ValueError("Different vector lengths")
    result: Number = 0
    for a, b in zip(left, right):
        result = result + a * b
    return result


if __name__ == "__main__":
    print(dot([1.0, 2.0], [3.0, 4.0]))
    print(dot([1 + 1j, 2 + 0j], [3 + 0j, 4 - 1j]))
    print(dot([], []))
