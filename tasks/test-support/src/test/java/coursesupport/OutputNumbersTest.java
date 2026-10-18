package coursesupport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OutputNumbersTest {
    @Test void readsSignedDecimalAndExponentWithoutRoundingToIntegers() {
        assertArrayEquals(new double[]{-1.25, 2e3, 0.5}, OutputNumbers.finiteNumbers(" -1.25\t2e3 0.5\n"));
        assertArrayEquals(new double[0], OutputNumbers.finiteNumbers(" \n"));
    }

    @Test void rejectsNonNumbersAndNonfiniteAnswers() {
        for (String value : new String[]{"NaN", "Infinity", "-Infinity", "1e999", "1 nope 2"}) {
            assertThrows(AssertionError.class, () -> OutputNumbers.finiteNumbers(value), value);
        }
    }

    @Test void preservesMatrixRowsAndFractionalCells() {
        double[][] actual = OutputNumbers.finiteMatrix("0.5 -1\r\n2 3.25\r\n", 2, 2);
        assertArrayEquals(new double[]{0.5, -1}, actual[0]);
        assertArrayEquals(new double[]{2, 3.25}, actual[1]);
    }

    @Test void rejectsFlattenedRaggedMissingAndExtraRows() {
        for (String value : new String[]{"1 2 3 4", "1 2\n3", "1 2\n3 4\n5 6", "", "1 2\n\n3 4"}) {
            assertThrows(AssertionError.class, () -> OutputNumbers.finiteMatrix(value, 2, 2), value);
        }
    }
}
