using System;
using System.Collections.Generic;
using System.Numerics;
using System.Globalization;
class Program {
    static T Dot<T>(IReadOnlyList<T> left, IReadOnlyList<T> right)
        where T : IAdditionOperators<T, T, T>,
                  IMultiplyOperators<T, T, T>,
                  IAdditiveIdentity<T, T> {
        if (left.Count != right.Count)
            throw new ArgumentException("Different vector lengths");

        T result = T.AdditiveIdentity;
        for (int i = 0; i < left.Count; i++) result += left[i] * right[i];
        return result;
    }
    static void Main() {
        CultureInfo.CurrentCulture = CultureInfo.InvariantCulture;
        var real = Dot(new double[] { 1, 2 }, new double[] { 3, 4 });
        var complex = Dot(new Complex[] { new(1, 1), new(2, 0) },
                          new Complex[] { new(3, 0), new(4, -1) });
        Console.WriteLine(real);
        Console.WriteLine(complex);
        Console.WriteLine($"{complex.Real} {complex.Imaginary}");
    }
}