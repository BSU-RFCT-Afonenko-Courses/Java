import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void chosenRoundingAndIndependentResult(){var input=new java.util.ArrayList<>(java.util.List.of(PublicTest.d("1.005"),PublicTest.d("1.015"),PublicTest.d("1000000"),PublicTest.d("1.005")));var before=new java.util.ArrayList<>(input);var result=TaskBinding.run(input,PublicTest.d("0"));assertEquals(before,input);for(int i=0;i<input.size();i++)assertEquals(0,input.get(i).setScale(2,TaskBinding.rounding()).compareTo(result.get(i)));input.clear();assertEquals(4,result.size());PublicTest.values(TaskBinding.run(java.util.List.of(PublicTest.d("1000000"),PublicTest.d("0")),PublicTest.d("1")),"2000000.00","0.00");}
}
