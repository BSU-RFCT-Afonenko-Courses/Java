import java.lang.Throwable;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HiddenContractTest {
    @Test
    @DisplayName("Обе границы включаются, диагноз сохраняет повторные вхождения")
    void sameBoundaryAndNoDedup() throws Throwable {
        Object a = TaskBinding.patient(97, 707, "X"), b = TaskBinding.patient(98, 708, "x");
        Object[] in = TaskBinding.array(a, b, a), before = in.clone();
        List<?> state = PublicChecks.values(in),
                expectedRange = PublicChecks.values(new Object[] {a, a});
        Object[] range = TaskBinding.range(in, 707, 707);
        List<?> actualRange = PublicChecks.values(range);
        assertEquals(expectedRange, actualRange);
        for (int k = 0; k < in.length; k++)
            assertSame(before[k], in[k]);
        assertEquals(state, PublicChecks.values(in));
        Object[] beforeDiagnosis = in.clone();
        List<?> diagnosisState = PublicChecks.values(in),
                expectedDiagnosis = PublicChecks.values(new Object[] {a, a});
        Object[] diagnosed = TaskBinding.diagnosis(in, "X");
        List<?> actualDiagnosis = PublicChecks.values(diagnosed);
        assertEquals(expectedDiagnosis, actualDiagnosis);
        for (int k = 0; k < in.length; k++)
            assertSame(beforeDiagnosis[k], in[k]);
        assertEquals(diagnosisState, PublicChecks.values(in));
    }
}
