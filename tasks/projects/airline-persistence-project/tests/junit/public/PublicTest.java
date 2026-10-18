import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
static java.util.Map<String,TaskBinding.Plane> byId(java.util.List<TaskBinding.Plane> planes){return planes.stream().collect(java.util.stream.Collectors.toMap(TaskBinding.Plane::id,x->x));}
@Test void publishedFleetTotalsAndSortedRange(){Object a=TaskBinding.fixture();assertEquals(232,TaskBinding.capacity(a));assertEquals(38.0,TaskBinding.payload(a));var original=TaskBinding.planes(a);assertEquals(3,original.size());var sorted=TaskBinding.sorted(a);assertEquals(byId(original),byId(sorted));boolean ascending=true,descending=true;for(int i=1;i<sorted.size();i++){ascending&=sorted.get(i-1).range()<=sorted.get(i).range();descending&=sorted.get(i-1).range()>=sorted.get(i).range();}assertTrue(ascending||descending,"Сортировка по дальности в выбранном направлении");}
@Test void inclusiveRangeAndNoInventedAllMatchRequirement(){Object a=TaskBinding.fixture();for(int[] range:new int[][]{{1500,2400},{2400,2400},{3000,3000},{5000,6000}}){var result=TaskBinding.find(a,range[0],range[1]);if(range[0]==5000)assertTrue(result.isEmpty());else{assertFalse(result.isEmpty());for(var plane:result){assertTrue(plane.fuel()>=range[0]&&plane.fuel()<=range[1]);assertEquals(byId(TaskBinding.planes(a)).get(plane.id()),plane);}}}}
}
