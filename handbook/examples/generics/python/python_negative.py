from python_dot import Arithmetic, dot

class WrongArithmetic:
    def zero(self) -> str: return "0"
    def add(self, a: float, b: float) -> float: return a + b
    def multiply(self, a: float, b: float) -> float: return a * b

bad: Arithmetic[float] = WrongArithmetic()
print(dot([1.0, 2.0], [3.0, 4.0], bad))
