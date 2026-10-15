#include <array>
#include <complex>
#include <concepts>
#include <iostream>
#include <stdexcept>
#include <vector>
template <class T>
concept DotElement = std::semiregular<T> && requires(const T a, const T b) {
    T{};
    { a + b } -> std::same_as<T>;
    { a * b } -> std::same_as<T>;
};
template <DotElement T>
T dot(const std::vector<T>& left, const std::vector<T>& right) {
    if (left.size() != right.size()) {
        throw std::invalid_argument("Different vector lengths");
    }
    T result{};
    for (std::size_t i = 0; i < left.size(); ++i) {
        result = result + left[i] * right[i];
    }
    return result;
}
template <DotElement T, std::size_t N>
T dot(const std::array<T, N>& left, const std::array<T, N>& right) {
    T result{};
    for (std::size_t i = 0; i < N; ++i) {
        result = result + left[i] * right[i];
    }
    return result;
}
int main() { dot(std::array<double, 2>{1, 2}, std::array<double, 3>{3, 4, 5}); }